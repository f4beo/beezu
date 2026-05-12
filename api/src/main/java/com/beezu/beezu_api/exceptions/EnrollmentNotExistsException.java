package com.beezu.beezu_api.exceptions;

public class EnrollmentNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EnrollmentNotExistsException(String message) {
		super(message);
	}

}
