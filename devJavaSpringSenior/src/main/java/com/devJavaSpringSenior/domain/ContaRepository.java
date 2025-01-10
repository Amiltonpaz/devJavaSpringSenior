package com.devJavaSpringSenior.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {
	
	List<ContaEntity> findByDataPagamentoBetween(Date dataInicial, Date dataFinal);
	// Deixei implementado, embora no exemplo informe dataVencimento E descrição;
	List<ContaEntity> findByDataVencimentoOrDescricao(Date dataVencimento, String descricao);

	List<ContaEntity> findByDataVencimentoAndDescricaoContainingIgnoreCase(Date dataVencimentoDate, String descricaoString);

}
