package kame;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.keygen.KeyGenerators;

import junit.framework.Assert;
import net.topaze.kame.Kame;
import net.topaze.kame.impl.springsecurity.KameImpl;

public class KameSSTest {

    private static final Logger LOGGER = LoggerFactory.getLogger("Kame");

    private Kame kame; 

    @Before
    public void setUp() throws Exception {	
	String password = "azerty123456,;:!";
	CharSequence salt = "012345678";
	password = KeyGenerators.string().generateKey();
	salt = KameImpl.getSalt(32);
	kame = new KameImpl(password, salt);
    }

    @Test
    public void testTextCrypt() {
	String plain = "Hello, world !";
	for(int i=0; i<10; i++) {
	    String txt = String.format("%s%d", plain, i);
	    LOGGER.debug("{}", kame.decrypt(kame.encrypt(txt)) );
	    Assert.assertEquals(
		    txt,
		    kame.decrypt(kame.encrypt(txt))
		    );
	}

    }
    
    @Test
    public void testTextCrypt2() {
	String plain = "Hello, world !";
	for(int i=0; i<10; i++) {
	    String txt = plain;
	    LOGGER.debug("{}", kame.encrypt(txt) );
	    Assert.assertEquals(
		    txt,
		    kame.decrypt(kame.encrypt(txt))
		    );
	}

    }

    @Test
    public void testBytesCrypt() {
	String plain = "Hello, world !";
	for(int i=0; i<10; i++) {
	    String txt = String.format("%s%d", plain, i);	    
	    LOGGER.debug("{}", kame.decrypt(kame.encrypt(txt.getBytes(StandardCharsets.UTF_8))) );
	    Assert.assertTrue(
		    Arrays.equals(
			    txt.getBytes(StandardCharsets.UTF_8) ,
			    kame.decrypt(kame.encrypt(txt.getBytes(StandardCharsets.UTF_8)))
			    )
		    );

	}
    }

}
