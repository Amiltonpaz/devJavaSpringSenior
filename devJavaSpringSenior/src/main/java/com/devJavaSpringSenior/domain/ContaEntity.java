package com.devJavaSpringSenior.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaEntity {
	
	public ContaEntity() {
		
	}

	private Long id;
	
	private Date dataVencimento;
	
	private Date dataPagamento;
	
	private Double valor;
	
	private String descricao;
	
	private String situacao;
}
