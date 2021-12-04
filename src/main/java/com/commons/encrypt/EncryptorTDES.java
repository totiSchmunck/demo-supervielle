package com.commons.encrypt;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.exceptions.EncryptionException;

public class EncryptorTDES extends Encryptor {
	
	protected static final String ENCRYPTION_KEY = "rLbngaFX978oaGyrrjn5";
	
	private static final Logger logger = LoggerFactory.getLogger(EncryptorTDES.class);

	@Override
	public String encrypt(String passPhrase) throws EncryptionException {
		try {
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(ENCRYPTION_KEY.getBytes(CHARSET_ISO));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

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
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(ENCRYPTION_KEY.getBytes(CHARSET_ISO));

			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher decipher = Cipher.getInstance("DESede/CBC/NoPadding");
			decipher.init(Cipher.DECRYPT_MODE, key, iv);

			final byte[] plainText = decipher.doFinal(encryptedPhrase.getBytes(CHARSET_ISO));

			return new String(plainText, "UTF-8");
		} catch (Exception e) {
			throw new EncryptionException("failed to decrypt", e);		
		}
	}

}
