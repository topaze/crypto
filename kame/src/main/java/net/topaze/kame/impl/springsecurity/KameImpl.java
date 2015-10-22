package net.topaze.kame.impl.springsecurity;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import net.topaze.kame.Kame;

public class KameImpl implements Kame {

    //    private static final Logger LOGGER = LoggerFactory.getLogger(Kame.class);

    private String key;
    private CharSequence salt;

    private TextEncryptor textEncryptor;
    private BytesEncryptor bytesEncryptor;

    /**
     * @param key
     * @param salt
     */
    public KameImpl(final String key, final CharSequence salt) {
	this.key = key;
	this.salt = salt;

	textEncryptor = Encryptors.text(key, salt);
	bytesEncryptor = Encryptors.standard(key, salt);
    }


    @Override
    public String encrypt(final String plain) {
	return textEncryptor.encrypt(plain);	
    }


    @Override
    public String decrypt(final String cipher) {
	return textEncryptor.decrypt(cipher);	
    }


    @Override
    public byte[] encrypt(final byte[] plainBytes) {
	return bytesEncryptor.encrypt(plainBytes);
    }


    @Override
    public byte[] decrypt(final byte[] cipherBytes) {
	return bytesEncryptor.decrypt(cipherBytes);
    }

    /**
     * @param size
     * @return
     */
    public static CharSequence getSalt(int size) {
	byte[] key = KeyGenerators.secureRandom(size).generateKey();
	CharSequence salt = new String(Hex.encodeHex(key));
	return salt;
    }

    @Override
    public String getKey() {
	return key;
    }


    @Override
    public void setKey(String key) {
	this.key = key;
    }

    @Override
    public CharSequence getSalt() {
	return salt;
    }


    @Override
    public void setSalt(CharSequence salt) {
	this.salt = salt;
    }

}
