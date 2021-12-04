package com.commons.encrypt;

import com.commons.encrypt.enums.EncryptionType;

/**
 * Encryptor factory to get the required Encryptor to handle password encryption
 * @author nmedici
 */
public class EncryptorFactory {
	
	public static Encryptor getEncryptor(EncryptionType type) {
		if (type.equals(EncryptionType.AES256)) { return new EncryptorAES256(); }
		if (type.equals(EncryptionType.DES)) { return new EncryptorDES(); }
		if (type.equals(EncryptionType.TDES)) { return new EncryptorTDES(); }
		return null;
	}
	
}
