package com.beezu.beezu_api.exceptions;

public class UserActivityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserActivityNotFoundException(String message) {
		super(message);
	}

}
