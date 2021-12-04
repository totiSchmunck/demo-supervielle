package com.commons.encrypt;

import com.commons.exceptions.EncryptionException;

public abstract class Encryptor {

	public static final String CHARSET_ISO = "ISO-8859-1";
	
	protected static final String SALT = "mWdU6kFhaeSDbBuC";

	public abstract String encrypt(String value) throws EncryptionException;

	public abstract String decryptPhrase(String value) throws EncryptionException;

	public abstract Boolean matches(String password, String encryptedString) throws EncryptionException;
	
}
