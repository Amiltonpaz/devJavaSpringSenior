package com.devJavaSpringSenior.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	
	

	public ContaEntity(Date dataVencimento, Date dataPagamento, Double valor, String descricao, String situacao) {
		super();
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.descricao = descricao;
		this.situacao = situacao;
	}

	public ContaEntity(String dataVencimento, String dataPagamento, String valor, String descricao, String situacao) {
		
		this.dataVencimento = this.criarDataFromString(dataVencimento);
		this.dataPagamento = this.criarDataFromString(dataPagamento);
		this.valor = Double.valueOf(valor);
		this.descricao = descricao;
		this.situacao = situacao;
	}

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
	
	private Date criarDataFromString(String dataString) {
		
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
			return formato.parse(dataString);
		} catch (ParseException e) {
			
			 try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				return dateFormat.parse(dataString);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
}
