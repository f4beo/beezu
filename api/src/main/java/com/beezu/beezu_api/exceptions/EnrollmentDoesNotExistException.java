package com.beezu.beezu_api.exceptions;

public class EnrollmentDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EnrollmentDoesNotExistException(String message) {
		super(message);
	}

}
