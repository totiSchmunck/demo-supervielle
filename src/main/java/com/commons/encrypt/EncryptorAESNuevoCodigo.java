package com.commons.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.exceptions.EncryptionException;

public class EncryptorAESNuevoCodigo {
	
	private static final Logger logger = LoggerFactory.getLogger(EncryptorAESNuevoCodigo.class);
	
	/**
	 * Encrypt.
	 *
	 * @param key the key
	 * @param initVector the init vector
	 * @param value the value
	 * @return the string
	 * @throws EncryptionException 
	 */
	public static String encrypt(String key, String initVector, String value) throws EncryptionException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
        	logger.error("EncriptionException: ", ex);
			throw new EncryptionException(ex);
        }
    }

    public static String decrypt(String key, String initVector, String encrypted) throws EncryptionException {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
        	logger.error("EncriptionException: ", ex);
			throw new EncryptionException(ex);
        }
    }

    public static void main(String[] args) throws EncryptionException {
    	if(args == null || args.length == 0)
        {
            System.out.println("Ingrese la clave a encriptar.");
            System.exit(0);
        }
    	String ketToBeCrypt = args[0];
    	
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        
        String encrypt = encrypt(key, initVector, ketToBeCrypt);
		System.out.println("Clave encriptada -> "+encrypt);
    }
}

