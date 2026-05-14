package com.beezu.beezu_api.exceptions;

public class ActivityNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ActivityNotFoundException(String message) {
		super(message);
	}

}
