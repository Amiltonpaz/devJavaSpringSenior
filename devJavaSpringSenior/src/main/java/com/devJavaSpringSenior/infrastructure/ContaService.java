package com.devJavaSpringSenior.infrastructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devJavaSpringSenior.domain.ContaEntity;
import com.devJavaSpringSenior.domain.ContaRepository;
import com.devJavaSpringSenior.infrastructure.dto.ContaDto;
import com.devJavaSpringSenior.infrastructure.dto.ValorPagoDto;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repository;
	
	public ContaEntity getById(Long id) {
		// Usar Optional
		return repository.findById(id).get();
	}
	
	public ContaEntity create(ContaDto contaDto) {
		ContaEntity conta = this.convertDtoToEntity(contaDto);		
		return repository.save(conta);
	}
	
	public ValorPagoDto findTotalPagoPorPeriodo(String dataInicialString, String dataFinalString) {
		Date dataInicialDate = this.criarDataFromStringHifen(dataInicialString);
		Date dataFinalDate = this.criarDataFromStringHifen(dataFinalString);
		List<ContaEntity> listaDeContas = this.repository.findByDataPagamentoBetween(dataInicialDate, dataFinalDate);
		
		//Preencher valorPAgo com outro método
		ValorPagoDto valorPago = this.calculaValorPago(listaDeContas);
		valorPago.setPeriodo("De " + dataInicialString + " até " + dataFinalString);
		return valorPago;
	}
	
	public List<ContaEntity> findContasPorVencimentoDescricao(String dataVencimentoString, String descricaoString) {
		Date dataVencimentoDate = this.criarDataFromStringHifen(dataVencimentoString);
		
		List<ContaEntity> listaDeContas = this.repository.findByDataVencimentoAndDescricaoContainingIgnoreCase(dataVencimentoDate, descricaoString);

		return listaDeContas;
	}
	
	public ContaEntity atualizaConta(ContaDto contaDto) {

		if(this.existeObjeto(contaDto.getId())) {
			return this.repository.save(this.convertDtoToEntity(contaDto));
		}
		
		return null;
	}
	
	public ContaEntity atualizaSituacaoConta(ContaDto contaDto) {
		if(this.existeObjeto(contaDto.getId())) {
			return this.repository.save(this.convertDtoToEntity(contaDto));
		}
		
		return null;
	}
	
	public boolean existeObjeto(Long id) {
		return repository.existsById(id);
	}
	
	private ValorPagoDto calculaValorPago(List<ContaEntity> listaDeContas) {
		ValorPagoDto valorPago = new ValorPagoDto();
		Double valorAcumulado = 0.0;
		
		for(ContaEntity conta : listaDeContas) {
			
			if(conta.getValor() != null && conta.getValor() > 0) {
				valorAcumulado = valorAcumulado+conta.getValor();
			}
		}
		valorPago.setValorPago(valorAcumulado);
		
		return valorPago;
	}
	
	private ContaEntity convertDtoToEntity(ContaDto contaDto) {
		ContaEntity conta = new ContaEntity();
		Date dataPagamento = this.criarDataFromString(contaDto.getDataPagamento());
		Date dataVencimento = this.criarDataFromString(contaDto.getDataVencimento());
		
		conta.setDataPagamento(dataPagamento);
		conta.setDataVencimento(dataVencimento);
		conta.setDescricao(contaDto.getDescricao());
		conta.setSituacao(contaDto.getSituacao());
		conta.setValor(contaDto.getValor());
		
		return conta;
		
	}
	
	private Date criarDataFromString(String dataString) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		try {
			return formato.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null; 
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
}
