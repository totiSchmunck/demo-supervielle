package com.commons.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.exceptions.EncryptionException;

public class EncryptorAES256 extends Encryptor {

	private static final String ENCRYPTION_KEY = "Bar12345Bar12345";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String INIT_VECTOR = "RandomInitVector";
	private static final Logger logger = LoggerFactory.getLogger(EncryptorAES256.class);

	@Override
	public String encrypt(String value) throws EncryptionException {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
			throw new EncryptionException("Falló al encriptar!", e);
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
	public String decryptPhrase(String value) throws EncryptionException {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(value));

            return new String(original);
        } catch (Exception e) {
			throw new EncryptionException("Falló al desencriptar!", e);
        }
	}
}
