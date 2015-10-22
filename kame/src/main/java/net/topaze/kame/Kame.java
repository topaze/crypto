package net.topaze.kame;

public interface Kame {

    /**
     * @param plain
     * @return
     */
    String encrypt(final String plain);

    
    /**
     * @param cipher
     * @return
     */
    String decrypt(final String cipher);

    /**
     * @param plainBytes
     * @return
     */
    byte[] encrypt(final byte[] plainBytes);

    
    /**
     * @param cipherBytes
     * @return
     */
    byte[] decrypt(final byte[] cipherBytes);
    
    /**
     * @param key
     */
    void setKey(String key);
    
    /**
     * @return
     */
    String getKey();
    
    /**
     * @param salt
     */
    void setSalt(CharSequence salt);
    
    /**
     * @return
     */
    CharSequence getSalt();
    
}
