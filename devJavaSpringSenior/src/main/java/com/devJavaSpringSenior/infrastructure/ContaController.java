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

@RestController
@RequestMapping("/pagamentos")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ImportaContasCsv importaContasCsv;

	@PostMapping("/cadastrarConta")
	public ResponseEntity<?> cadastrarConta(@RequestBody ContaDto contaDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(contaService.create(contaDto));
	}
	
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
	
	@GetMapping("/listarContasPorVencimentoDescricao")
	public ResponseEntity<?> getListaContasFilterVencimentoDescricao(@RequestParam(value = "dataVencimento") String dataVencimento, 
			@RequestParam(value = "descricao") String descricao, Pageable pageable) {
		return ResponseEntity.ok(contaService.findContasPorVencimentoDescricao(dataVencimento, descricao, pageable));
	}
	
	@GetMapping("/conta/{id}")
	public ResponseEntity<?> getContaPorId(@PathVariable Long id) {		
		return ResponseEntity.ok(contaService.getById(id));
	}
	
	@GetMapping("/totalPagoPorPeriodo")
	public ResponseEntity<?> totalPagoPorPeriodo(@RequestParam(value = "dataInicial") String dataInicial, 
			@RequestParam(value = "dataFinal") String dataFinal, Pageable pageable) {
		return ResponseEntity.ok(contaService.findTotalPagoPorPeriodo(dataInicial, dataFinal, pageable));
	}
	
	@PostMapping("/importarContasCsv")
	public ResponseEntity<?> importarContasFromCsv(MultipartFile file, Pageable pageable) {
        
		if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum arquivo foi enviado.");
        }
		
		try {
			
			return ResponseEntity.ok(importaContasCsv.lerArquivo(file));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (CabecalhoException e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(Optional.empty());
	}
}
