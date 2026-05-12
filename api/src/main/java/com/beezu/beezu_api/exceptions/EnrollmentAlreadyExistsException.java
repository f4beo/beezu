package com.beezu.beezu_api.exceptions;

public class EnrollmentAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EnrollmentAlreadyExistsException(String message) {
		super(message);
	}

}
