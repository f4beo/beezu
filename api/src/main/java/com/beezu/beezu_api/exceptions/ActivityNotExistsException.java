package com.beezu.beezu_api.exceptions;

public class ActivityNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ActivityNotExistsException(String message) {
		super(message);
	}

}
