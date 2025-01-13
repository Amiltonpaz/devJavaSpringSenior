package com.devJavaSpringSenior.infrastructure.dto;

import org.springframework.format.annotation.DateTimeFormat;

public class ContaDto {
	
	public ContaDto() {
		
	}
	
	

	public ContaDto(Long id, String dataVencimento, String dataPagamento, Double valor, String descricao,
			String situacao) {
		super();
		this.id = id;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.descricao = descricao;
		this.situacao = situacao;
	}



	private Long id;
	
	@DateTimeFormat
	private String dataVencimento;
	
	@DateTimeFormat
	private String dataPagamento;
	
	private Double valor;
	
	private String descricao;
	
	private String situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
	
	
}
