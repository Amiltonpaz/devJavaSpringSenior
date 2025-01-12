package com.devJavaSpringSenior.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devJavaSpringSenior.domain.ContaEntity;
import com.devJavaSpringSenior.domain.ContaRepository;
import com.devJavaSpringSenior.infrastructure.exception.CabecalhoException;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ImportaContasCsv {
	
	 private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ImportaContasCsv.class);

			
	@Autowired
	private ContaRepository contaRepository;

	public ImportaContasCsv() {
	}



	private static final String CABECALHO = "dataVencimento;dataPagamento;valor;descricao;situacao";
	
	public List<ContaEntity> lerArquivo(final MultipartFile file) throws CabecalhoException, IOException {
		
		var contas = new ArrayList<ContaEntity>();
		 List<ContaEntity> contasExtraidas = new ArrayList<ContaEntity>();
		 
		InputStream inputStream = file.getInputStream();
		
		try( var scanner = new Scanner(inputStream)) {
			
			scanner.useDelimiter("\n");
			
			var cabecalho = scanner.next();
			validadorCabecalho(cabecalho);
			
			while(scanner.hasNext()) {
				
				contasExtraidas =	adicionarContas(scanner.next(), contas);
				
			}
			
			return contaRepository.saveAll(contasExtraidas);
			
		} catch (Exception  e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<ContaEntity> lerArquivo(final InputStream inputStream) throws CabecalhoException, IOException {
		
		var contas = new ArrayList<ContaEntity>();
		 List<ContaEntity> contasExtraidas = new ArrayList<ContaEntity>();
		
		try( var scanner = new Scanner(inputStream)) {
			
			scanner.useDelimiter("\n");
			
			var cabecalho = scanner.next();
			validadorCabecalho(cabecalho);
			
			while(scanner.hasNext()) {
				
				contasExtraidas = adicionarContas(scanner.next(), contas);
								
			}
			
			return contaRepository.saveAll(contasExtraidas);
		}catch (Exception  e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public List<ContaEntity> adicionarContas(String linha, List<ContaEntity> contas){
		var campos = linha.split(";");
		
		if(campos == null || campos.length < 5) {			
			log.warn("Linha inválida {} ", linha); // Usei o log.warn para não lançar uma exceção e interromper o processamento;			
		}else {
			contas.add(new ContaEntity(campos[0], campos[1], campos[2], campos[3], campos[4]));
			return contas;
		}
		return null;
		
	}

	public static void validadorCabecalho(String cabecalho) throws CabecalhoException {
		//TODO Verificar validação do cabeçalho
		if(StringUtils.isEmpty(cabecalho) || !CABECALHO.equalsIgnoreCase(cabecalho)) {
			throw new CabecalhoException(cabecalho);
		}
		
	}
}
