package net.topaze.kame.impl.blowfish;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.topaze.kame.Kame;

public class KameImpl implements Kame {

    private static final Logger LOGGER = LoggerFactory.getLogger("MyBlowFish");

    private final String ALGORITHM = "Blowfish";
    private final String DEFAULT_KEY = "0123456789012345";    
    private Key secretKey;    
    private Cipher cipher;    
    private static Kame instance = new KameImpl();
    
    @Override
    public String encrypt(String plain) {
	return new String(Base64.encodeBase64URLSafe(encrypt(plain.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8 );
    }

    @Override
    public String decrypt(String cipher) {
	return new String(
		decrypt(
			Base64.decodeBase64( cipher.getBytes(StandardCharsets.UTF_8) )
			),
		StandardCharsets.UTF_8
		);
    }

    @Override
    public byte[] encrypt(byte[] plainBytes) {
	try {
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    return cipher.doFinal(plainBytes);
	} catch (Exception e) {
	    LOGGER.error(e.getLocalizedMessage(), e);
	}
	return null;
    }

    @Override
    public byte[] decrypt(byte[] cipherBytes) {
	try {            
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    return cipher.doFinal(cipherBytes);
	} catch (Exception e) {
	    LOGGER.error(e.getLocalizedMessage(), e);
	}
	return null;
    }

    @Override
    public void setKey(String key) {
	// TODO Auto-generated method stub

    }

    @Override
    public String getKey() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setSalt(CharSequence salt) {
	// TODO Auto-generated method stub

    }

    @Override
    public CharSequence getSalt() {
	// TODO Auto-generated method stub
	return null;
    }

}
