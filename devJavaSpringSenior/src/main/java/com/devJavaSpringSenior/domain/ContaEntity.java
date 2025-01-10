package com.devJavaSpringSenior.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "pagamento", schema = "public")
public class ContaEntity {
	
	public ContaEntity() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_seq")
	@SequenceGenerator(name = "pagamento_seq", sequenceName = "pagamento_seq", allocationSize = 1)
	private Long id;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "datavencimento")
	private Date dataVencimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "datapagamento")
	private Date dataPagamento;
	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "situacao")
	private String situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
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
