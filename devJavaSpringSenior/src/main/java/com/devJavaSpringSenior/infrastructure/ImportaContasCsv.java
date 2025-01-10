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
import com.devJavaSpringSenior.infrastructure.exception.LinhaInvalidaException;

import io.micrometer.common.util.StringUtils;

@Service
public class ImportaContasCsv {
	
	@Autowired
	private ContaRepository contaRepository;

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
			
		} catch (LinhaInvalidaException e) {
			
			e.printStackTrace();
		}catch (Exception  e) {
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
		} catch (LinhaInvalidaException e) {
			
			e.printStackTrace();
		}catch (Exception  e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public List<ContaEntity> adicionarContas(String linha, List<ContaEntity> contas) throws LinhaInvalidaException {
		var campos = linha.split(";");
		
		if(campos == null || campos.length < 5) {
			// lançar warn não pararia o processamento;
			throw new LinhaInvalidaException();
		}else {
			contas.add(new ContaEntity(campos[0], campos[1], campos[2], campos[3], campos[4]));
			return contas;
		}
		
	}

	private static void validadorCabecalho(String cabecalho) throws CabecalhoException {
		
		if(StringUtils.isEmpty(cabecalho) || CABECALHO.equalsIgnoreCase(cabecalho)) {
			throw new CabecalhoException(cabecalho);
		}
		
	}
}
