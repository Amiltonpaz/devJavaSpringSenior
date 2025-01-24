package com.devJavaSpringSenior.infrastructure;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devJavaSpringSenior.infrastructure.dto.ContaDto;
import com.devJavaSpringSenior.infrastructure.exception.CabecalhoException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class ContaController {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ImportaContasCsv.class);
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ImportaContasCsv importaContasCsv;
	
	@Operation(summary = "Cadastrar conta", description = "Este endpoint permite cadastrar uma nova conta.")
	@PostMapping("/cadastrarConta")
	public ResponseEntity<?> cadastrarConta(@RequestBody ContaDto contaDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(contaService.create(contaDto));
	}
	
	@Operation(summary = "Atualizar conta", description = "Este endpoint permite atualizar uma conta.")
	@PutMapping("/atualizarConta")
	public ResponseEntity<?> atualizarConta(@RequestBody ContaDto contaDto) {
		
		if(contaService.existeObjeto(contaDto.getId())) {
			return ResponseEntity.ok(this.contaService.atualizaConta(contaDto));
		}
		
		if(contaDto.getId() == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@Operation(summary = "Alterar situação da conta", description = "Este endpoint permite atualizar a situação da conta.")
	@PutMapping("/alterarSituacaoConta")
	public ResponseEntity<?> alterarSituacaoDaConta(@RequestBody ContaDto contaDto) {
		if(contaService.existeObjeto(contaDto.getId())) {
			
		return ResponseEntity.ok(contaService.atualizaSituacaoConta(contaDto));
		}
		
		if(contaDto.getId() == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Operation(summary = "Listar contas por vencimento e descrição.", description = "Este endpoint retorna uma lista de contas, considerando a data de vencimento e a descrição fornecida exata ou em parte.")
	@GetMapping("/listarContasPorVencimentoDescricao")
	public ResponseEntity<?> getListaContasFilterVencimentoDescricao(@RequestParam(value = "dataVencimento") String dataVencimento, 
			@RequestParam(value = "descricao") String descricao, Pageable pageable) {
		return ResponseEntity.ok(contaService.findContasPorVencimentoDescricao(dataVencimento, descricao, pageable));
	}
	
	@Operation(summary = "Buscar uma conta por ID", description = "Este endpoint retorna os detalhes de uma conta com base no ID fornecido.")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Conta encontrada"),
	        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
	    })
	@GetMapping("/conta/{id}")
	public ResponseEntity<?> getContaPorId(@PathVariable Long id) {		
		return ResponseEntity.ok(contaService.getById(id));
	}
	
	@Operation(summary = "Calcular o total pago por período", description = "Este endpoint retorna o valor total pago baseado no intervalo solicitado.")	   
	@GetMapping("/totalPagoPorPeriodo")
	public ResponseEntity<?> totalPagoPorPeriodo(@RequestParam(value = "dataInicial") String dataInicial, 
			@RequestParam(value = "dataFinal") String dataFinal, Pageable pageable) {
		return ResponseEntity.ok(contaService.findTotalPagoPorPeriodo(dataInicial, dataFinal, pageable));
	}
	
	@Operation(summary = "Importar contas a partir de arquivo .csv", description = "Este Endpoint recebe um arquivo .csv contendo as contas a serem importadas para o banco de dados.")	    
	@PostMapping("/importarContasCsv")
	public ResponseEntity<?> importarContasFromCsv(MultipartFile file, Pageable pageable) {
        
		if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo foi enviado.");
        }
		
		try {
			
			return ResponseEntity.ok(importaContasCsv.lerArquivo(file));
			
		} catch (IOException e) {
			log.error(e.getMessage());			
		} catch (CabecalhoException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(Optional.empty());
	}
}
