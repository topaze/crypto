package net.topaze.kame.impl.jce;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.topaze.kame.Kame;

public class KameImpl implements Kame {

    private static final Logger LOGGER = LoggerFactory.getLogger("Kame");
    private static final int DEFAULT_KEY_SIZE = 128;

    private String key;
    private CharSequence salt;

    private Key encryptionKey;
    private Key decryptionKey;

    private Cipher encryptCipher;
    private Cipher decryptCipher;

    /**
     * @param key
     * @param salt
     */
    public KameImpl(final String key, final CharSequence salt) {
	this.key = key;
	this.salt = salt;
	setKey(key);

	try {
	    encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
	    synchronized(encryptCipher) {
		encryptCipher.init(Cipher.ENCRYPT_MODE, encryptionKey, new IvParameterSpec(key.getBytes()));
	    }
	    decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
	    synchronized (decryptCipher) {
		decryptCipher.init(Cipher.DECRYPT_MODE, decryptionKey, new IvParameterSpec(key.getBytes()));
	    }

	} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
	    LOGGER.error(e.getLocalizedMessage(), e);
	}	    

    }


    @Override
    public String encrypt(String plain) {
	String cipher = null; 
	synchronized (encryptCipher) {
	    try {
		cipher = new String(
			Base64.encodeBase64URLSafe(
				encryptCipher.doFinal(
					plain.getBytes(StandardCharsets.UTF_8)
					)),
			StandardCharsets.UTF_8
			);
	    } catch (IllegalBlockSizeException | BadPaddingException e) {
		LOGGER.error(e.getLocalizedMessage(), e);
	    }
	}

	return cipher;
    }

    @Override
    public String decrypt(String cipher) {
	String plain = null; 
	synchronized (decryptCipher) {
	    try {
		plain = new String(
			decryptCipher.doFinal(
				Base64.decodeBase64(cipher.getBytes(StandardCharsets.UTF_8))
				),
			StandardCharsets.UTF_8
			);
	    } catch (IllegalBlockSizeException | BadPaddingException e) {
		LOGGER.error(e.getLocalizedMessage(), e);
	    }
	}
	return plain;
    }

    @Override
    public byte[] encrypt(byte[] plainBytes) {
	byte[] cipher = null; 
	synchronized (encryptCipher) {
	    try {
		cipher = encryptCipher.doFinal(plainBytes);
	    } catch (IllegalBlockSizeException | BadPaddingException e) {
		LOGGER.error(e.getLocalizedMessage(), e);
	    }
	}
	return cipher;
    }

    @Override
    public byte[] decrypt(byte[] cipherBytes) {
	byte[] plainBytes = null; 
	synchronized (decryptCipher) {	 
	    try {
		plainBytes = decryptCipher.doFinal(cipherBytes);
	    } catch (IllegalBlockSizeException | BadPaddingException e) {
		LOGGER.error(e.getLocalizedMessage(), e);
	    }
	}
	return plainBytes;
    }

    @Override
    public synchronized void setKey(String key) {
	this.key = key;	
	try {
	    //KeyGenerator generator = KeyGenerator.getInstance("AES", "SunJCE");
	    KeyGenerator generator = KeyGenerator.getInstance("AES", "SunJCE");	    
	    generator.init(DEFAULT_KEY_SIZE);
	    encryptionKey = generator.generateKey();
	    decryptionKey = new SecretKeySpec(encryptionKey.getEncoded(), encryptionKey.getAlgorithm());	    
	} catch ( NoSuchAlgorithmException | NoSuchProviderException e) {	
	    LOGGER.error(e.getLocalizedMessage(), e);
	}	
    }

    @Override
    public synchronized void setSalt(CharSequence salt) {
	this.salt = salt;
    }

    @Override
    public synchronized String getKey() {
	return key;
    }

    @Override
    public synchronized CharSequence getSalt() {	
	return salt;
    }

}
