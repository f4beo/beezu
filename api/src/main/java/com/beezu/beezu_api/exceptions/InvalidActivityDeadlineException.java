package com.beezu.beezu_api.exceptions;

public class InvalidActivityDeadlineException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidActivityDeadlineException(String message) {
		super(message);
	}

}
