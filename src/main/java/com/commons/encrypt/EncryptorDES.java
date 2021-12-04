package com.commons.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.exceptions.EncryptionException;


public class EncryptorDES extends Encryptor{


	protected static final String ENCRYPTION_KEY = "rLbngaFX978oaGyrrjn5";

	private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";
	
	private static final Logger logger = LoggerFactory.getLogger(EncryptorDES.class);

	@Override
	public String encrypt(String passPhrase) throws EncryptionException {
		try {

			DESKeySpec desKeySpec = new DESKeySpec(ENCRYPTION_KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(desKeySpec);

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] inputBytes = passPhrase.getBytes(CHARSET_ISO);

			return new String(cipher.doFinal(inputBytes), CHARSET_ISO);

		} catch (Exception e) {
			throw new EncryptionException("failed to encrypt", e);		
		}
	}

	@Override
	public Boolean matches(String password, String encryptedText) throws EncryptionException {
		try {
			String textDecrypted = this.decryptPhrase(encryptedText);
			return password.equals(textDecrypted);
		} catch (Exception e) {
			logger.error("EncriptionException: ", e);
			throw new EncryptionException(e);
		}
	}

	@Override
	public String decryptPhrase(String encryptedPhrase) throws EncryptionException {
		try {
			DESKeySpec desKeySpec = new DESKeySpec(ENCRYPTION_KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(desKeySpec);
			

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] decryptedBytes = cipher.doFinal(encryptedPhrase.getBytes(CHARSET_ISO));

			return new String(decryptedBytes, CHARSET_ISO);

		} catch (Exception e) {
			throw new EncryptionException("failed to decrypt", e);		
		}
	}
}
