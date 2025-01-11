package com.devJavaSpringSenior.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {
	
	Optional<UsuarioEntity> findByEmail(String email);

}
