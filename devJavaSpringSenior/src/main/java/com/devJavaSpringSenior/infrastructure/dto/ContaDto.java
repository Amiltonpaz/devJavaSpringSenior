package com.devJavaSpringSenior.infrastructure.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDto {
	
	public ContaDto() {
		
	}

	private Long id;
	
	@DateTimeFormat
	private String dataVencimento;
	
	@DateTimeFormat
	private String dataPagamento;
	
	private Double valor;
	
	private String descricao;
	
	private String situacao;
	
}
