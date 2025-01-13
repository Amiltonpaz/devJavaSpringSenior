package com.devJavaSpringSenior.infrastructure;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devJavaSpringSenior.domain.ContaEntity;
import com.devJavaSpringSenior.domain.ContaRepository;
import com.devJavaSpringSenior.infrastructure.dto.ContaDto;
import com.devJavaSpringSenior.infrastructure.dto.ValorPagoDto;
import com.devJavaSpringSenior.infrastructure.exception.DataParseException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repository;
	
	public ContaDto getById(Long id) {
		return this.convertToDto(repository.findById(id).orElseThrow());
	}
	
	public ContaDto create(ContaDto contaDto) {
		ContaEntity conta = this.convertDtoToEntity(contaDto);	
		ContaDto resultado = this.convertToDto(repository.save(conta));
		return resultado;
	}
	
	public ValorPagoDto findTotalPagoPorPeriodo(String dataInicialString, String dataFinalString, Pageable pageable) {
		
		Date dataInicialDate = this.ajustarDataInicio(this.criarDataFromStringHifen(dataInicialString));
		Date dataFinalDate = this.ajustarDataFim(this.criarDataFromStringHifen(dataFinalString));
		
		List<ContaEntity> listaDeContas = this.repository.findByDataPagamentoBetween(dataInicialDate, dataFinalDate, pageable);
		
		ValorPagoDto valorPago = this.calculaValorPago(listaDeContas);
		valorPago.setPeriodo("De " + dataInicialString + " até " + dataFinalString);
		return valorPago;
	}
	
	public List<ContaDto> findContasPorVencimentoDescricao(String dataVencimentoString, String descricaoString, Pageable pageable) {
		Date dataVencimentoDate = this.criarDataFromStringHifen(dataVencimentoString);
		
		List<ContaEntity> listaContaEntity = this.repository.findByDataVencimentoAndDescricaoContainingIgnoreCase(dataVencimentoDate, descricaoString, pageable);

		 return listaContaEntity.stream()
			        .map(this::convertToDto)
			        .collect(Collectors.toList());

	}
	
	public ContaDto atualizaConta(ContaDto contaDto) {

		if(this.existeObjeto(contaDto.getId())) {
			return this.convertToDto(this.repository.save(this.convertDtoToEntity(contaDto)));
		}
		
		return null;
	}
	
	public ContaDto atualizaSituacaoConta(ContaDto contaDto) {
		if(this.existeObjeto(contaDto.getId())) {			 			 
			 return this.convertToDto(this.repository.save(this.convertDtoToEntity(contaDto)));
		}
		
		return null;
	}
	
	public boolean existeObjeto(Long id) {
		return repository.existsById(id);
	}
	
	private ValorPagoDto calculaValorPago(List<ContaEntity> listaDeContas) {
	    double valorAcumulado = listaDeContas.stream()
	            .filter(conta -> conta.getValor() != null && conta.getValor() > 0)
	            .mapToDouble(ContaEntity::getValor)
	            .sum();

	    // Arredondar para duas casas decimais
	    BigDecimal valorFinal = BigDecimal.valueOf(valorAcumulado).setScale(2, RoundingMode.HALF_UP);

	    ValorPagoDto valorPago = new ValorPagoDto();
	    valorPago.setValorPago(valorFinal.doubleValue());
	    return valorPago;
	}

	
	private ContaEntity convertDtoToEntity(ContaDto contaDto) {
		ContaEntity conta = new ContaEntity();
		
		try {
			Date dataVencimento = this.criarDataFromString(contaDto.getDataVencimento());
			Date dataPagamento = this.criarDataFromString(contaDto.getDataPagamento());
			
			if(contaDto.getId() != null) {
				conta.setId(contaDto.getId());
			}
					
			conta.setDataPagamento(dataPagamento);
			conta.setDataVencimento(dataVencimento);
			conta.setDescricao(contaDto.getDescricao());
			conta.setSituacao(contaDto.getSituacao().trim().replaceAll("\\r", ""));
			conta.setValor(contaDto.getValor());
			
			return conta;
			
		} catch (DataParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private ContaDto convertToDto(ContaEntity contaEntity) {
		ContaDto contaDto = new ContaDto();
				
			Date dataPagamento = contaEntity.getDataPagamento();
			Date dataVencimento = contaEntity.getDataVencimento();
			
			if(contaEntity.getId() != null) {
				contaDto.setId(contaEntity.getId());
			}
					
			contaDto.setDataPagamento(this.formatarData(dataPagamento));
			contaDto.setDataVencimento(this.formatarData(dataVencimento));
			contaDto.setDescricao(contaEntity.getDescricao());
			contaDto.setSituacao(contaEntity.getSituacao().trim().replaceAll("\\r", ""));
			contaDto.setValor(contaEntity.getValor());
			
			return contaDto;		
	}
	
	private Date criarDataFromString(String dataString) throws DataParseException {
	    String[] formatos = {"dd/MM/yyyy", "dd-MM-yyyy"};
	    
	    for (String formato : formatos) {
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
	            return dateFormat.parse(dataString);
	        } catch (ParseException e) {
	            // Ignora a exceção e tenta o próximo formato
	        }
	    }
	    
	    // Lança uma exceção personalizada quando nenhum formato é válido
	    throw new DataParseException("Erro ao parsear a data: formato inválido");
	}
	
	private Date criarDataFromStringHifen(String dataString) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
		try {
			return formato.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String formatarData(Date data) {
	    if (data == null) {
	        throw new IllegalArgumentException("A data não pode ser nula.");
	    }
	    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	    return formato.format(data);
	}
	
	private Date ajustarDataInicio(Date data) {
	    LocalDate localDate = data.toInstant()
	                              .atZone(ZoneId.systemDefault())
	                              .toLocalDate();
	    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private Date ajustarDataFim(Date data) {
	    LocalDate localDate = data.toInstant()
	                              .atZone(ZoneId.systemDefault())
	                              .toLocalDate();
	    return Date.from(localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
	}


}
