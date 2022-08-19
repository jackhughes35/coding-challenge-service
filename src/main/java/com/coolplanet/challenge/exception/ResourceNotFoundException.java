package com.coolplanet.challenge.exception;

public class ResourceNotFoundException extends TaskServiceException {

	/**
	 * default Serial ID
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
