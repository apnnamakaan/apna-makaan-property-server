package com.nvc.property.exceptions;

public class UnauthorizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String message;
	public UnauthorizeException(String message){
		super(message);
		this.message = message;
	}
}
