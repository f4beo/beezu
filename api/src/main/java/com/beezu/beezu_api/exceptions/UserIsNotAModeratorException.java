package com.beezu.beezu_api.exceptions;

public class UserIsNotAModeratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserIsNotAModeratorException(String message) {
		super(message);
	}

}
