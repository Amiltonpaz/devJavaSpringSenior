package com.devJavaSpringSenior.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthenticated implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8610080219256564175L;
	
	@Autowired
	UsuarioEntity usuario;
	
	public UserAuthenticated() {
		
	}

	public UserAuthenticated(UsuarioEntity usuario) {
		super();
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(() -> "read"); // Coloquei assim por simplicidade;
	}

	@Override
	public String getPassword() {
		
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		
		return usuario.getEmail();
	}

}
