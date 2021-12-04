package com.commons.exceptions;

/**
 * @author gonzalojavierdiaz
 *
 */
public class EncryptionException extends Exception {

	private static final long serialVersionUID = -4634501221677300844L;

	public EncryptionException(Throwable t) {
		super(t);
	}

	public EncryptionException(String message){
		super(message);
	}

	public EncryptionException(String message, Throwable t){
		super(message, t);
	}
}
