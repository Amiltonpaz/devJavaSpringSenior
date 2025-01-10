package com.devJavaSpringSenior.infrastructure.exception;

public class LinhaInvalidaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1032683416471292581L;

	public LinhaInvalidaException () {
		super("linha inválida");
	}
}
