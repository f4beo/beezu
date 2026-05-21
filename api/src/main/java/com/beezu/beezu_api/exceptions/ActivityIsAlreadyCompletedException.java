package com.beezu.beezu_api.exceptions;

public class ActivityIsAlreadyCompletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ActivityIsAlreadyCompletedException(String message) {
		super(message);
	}

}
