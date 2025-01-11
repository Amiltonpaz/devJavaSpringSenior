package com.devJavaSpringSenior.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContaRepository extends PagingAndSortingRepository<ContaEntity, Long>, JpaRepository<ContaEntity, Long> {
	
		
	List<ContaEntity> findByDataPagamentoBetween(Date dataInicial, Date dataFinal, Pageable pageable);
	
	List<ContaEntity> findByDataVencimentoAndDescricaoContainingIgnoreCase(Date dataVencimentoDate, String descricaoString, Pageable pageable);

}
