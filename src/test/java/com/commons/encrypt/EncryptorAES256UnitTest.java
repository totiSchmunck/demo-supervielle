package com.commons.encrypt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.commons.encrypt.EncryptorFactory;
import com.commons.encrypt.enums.EncryptionType;
import com.commons.exceptions.EncryptionException;

public class EncryptorAES256UnitTest extends AbstractEncryptorUnitTest{
	
	private static final String DES_PASSWORD = "Uk©WÝ;";
	
	@Before
	public void setUp(){
		encryptor = EncryptorFactory.getEncryptor(EncryptionType.AES256);
	}
	
	@Test
	public void return_true_when_password_and_encrypted_matches() throws EncryptionException {
		String encryptedString = encryptor.encrypt(PASSWORD);
		assertTrue(encryptor.matches(PASSWORD, encryptedString));
		//assertTrue(encryptor.matches(PASSWORD, DES_PASSWORD));
	}
	
	@Test
	public void return_false_when_password_and_encrypted_not_matches() throws EncryptionException {
		String encryptedString = encryptor.encrypt(PASSWORD);
		assertFalse(encryptor.matches(OTHER_PASSWORD, encryptedString));
		assertFalse(encryptor.matches(OTHER_PASSWORD, DES_PASSWORD));
	}
	
	@Test
	public void decrypted_string_should_be_same_as_expected_on_database() throws EncryptionException{
		assertTrue(encryptor.matches(PASSWORD, DES_PASSWORD));
	}

}
