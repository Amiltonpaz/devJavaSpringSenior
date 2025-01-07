package com.devJavaSpringSenior.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pagamento", schema = "public")
public class ContaEntity {
	
	public ContaEntity() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "datavencimento")
	private Date dataVencimento;
	
	@Column(name = "datapagamento")
	private Date dataPagamento;
	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "situacao")
	private String situacao;
}
