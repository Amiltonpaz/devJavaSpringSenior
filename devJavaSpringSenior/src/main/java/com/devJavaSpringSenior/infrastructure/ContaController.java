package com.devJavaSpringSenior.infrastructure;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.devJavaSpringSenior.infrastructure.dto.ContaDto;
import com.devJavaSpringSenior.infrastructure.exception.CabecalhoException;

@Controller
@RequestMapping("/pagamentos")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ImportaContasCsv importaContasCsv;

	@PostMapping("/cadastrarConta")
	public ResponseEntity<?> criarConta(@RequestBody ContaDto contaDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(contaService.create(contaDto));
	}
	
	@PutMapping("/atualizarConta")
	public ResponseEntity<?> updateConta(@RequestBody ContaDto contaDto) {
		
		if(contaService.existeObjeto(contaDto.getId())) {
			return ResponseEntity.ok(this.contaService.atualizaConta(contaDto));
		}
		
		if(contaDto.getId() == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/alteraSituacaoConta")
	public ResponseEntity<?> updateSituacaoConta(@RequestBody ContaDto contaDto) {
		if(contaService.existeObjeto(contaDto.getId())) {
			
		return ResponseEntity.ok(contaService.atualizaSituacaoConta(contaDto));
		}
		
		if(contaDto.getId() == null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/listarContas/{dataVencimento}/{descricao}")
	public ResponseEntity<?> getAllContasFilter(@PathVariable("dataVencimento") String dataVencimento, 
			@PathVariable("descricao") String descricao) {
		return ResponseEntity.ok(contaService.findContasPorVencimentoDescricao(dataVencimento, descricao));
	}
	
	@GetMapping("/conta/{id}")
	public ResponseEntity<?> getContaFromId(@PathVariable("id") Long id) {		
		return ResponseEntity.ok(contaService.getById(id));
	}
	
	@GetMapping("/conta")
	public ResponseEntity<?> getConta() {
		return ResponseEntity.ok("conta retornada");
	}
	
	@GetMapping("/totalPagoPorPeriodo/{dataInicial}/{dataFinal}")
	public ResponseEntity<?> totalPagoPorPeriodo(@PathVariable("dataInicial") String dataInicial, 
			@PathVariable("dataFinal") String dataFinal) {
		return ResponseEntity.ok(contaService.findTotalPagoPorPeriodo(dataInicial, dataFinal));
	}
	
	@PostMapping("/loadCsvFile")
	public ResponseEntity<?> uploadCsv(MultipartFile file) {
        
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
