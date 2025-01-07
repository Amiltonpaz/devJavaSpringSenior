package com.devJavaSpringSenior.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devJavaSpringSenior.infrastructure.dto.ContaDto;

@Controller
@RequestMapping("/pagamentos")
public class ContaController {

	@PostMapping("/cadastrarConta")
	public ResponseEntity<?> criarConta(@RequestBody ContaDto contaDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body("conta criada com sucesso");
	}
	
	@PutMapping("/atualizarConta/{id}")
	public ResponseEntity<?> updateConta(@PathVariable("id") Long id) {
		return ResponseEntity.ok(id);
	}
	
	@PutMapping("/alteraSituacaoConta/{id}")
	public ResponseEntity<?> updateSituacaoConta(@PathVariable("id") Long id) {
		return ResponseEntity.ok(id);
	}
	
	@GetMapping("/listarContas/{dataVencimento}{descricao}")
	public ResponseEntity<?> getAllContasFilter(@PathVariable("dataVencimento") String dataVencimento, 
			@PathVariable("descricao") String descricao) {
		return ResponseEntity.ok("Lista filtrada de todas as contas.");
	}
	
	@GetMapping("/conta/{id}")
	public ResponseEntity<?> getMethodName(@PathVariable("id") Long id) {
		return ResponseEntity.ok(id);
	}
	
	@GetMapping("/conta")
	public ResponseEntity<?> getConta() {
		return ResponseEntity.ok("conta retornada");
	}
	
	@GetMapping("/totalPagoPorPeriodo/{dataInicial}{dataFinal}")
	public ResponseEntity<?> totalPagoPorPeriodo(@PathVariable("dataInicial") String dataInicial, 
			@PathVariable("dataFinal") String dataFinal) {
		return ResponseEntity.ok("Total pago por período.");
	}
}
