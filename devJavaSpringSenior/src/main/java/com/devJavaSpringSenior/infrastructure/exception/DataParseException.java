package com.devJavaSpringSenior.infrastructure.exception;

public class DataParseException extends Exception {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 6408682143642825826L;

	public DataParseException(String message) {
	        super(message);
	    }

	    // Construtor que recebe uma mensagem de erro e uma causa (outra exceção)
	    public DataParseException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
