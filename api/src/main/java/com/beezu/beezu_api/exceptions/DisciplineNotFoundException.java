package com.beezu.beezu_api.exceptions;

public class DisciplineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DisciplineNotFoundException(String message) {
		super(message);
	}

}
