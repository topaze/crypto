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
import net.topaze.kame.impl.jce.KameImpl;

public class KameJCETest {

    private static final Logger LOGGER = LoggerFactory.getLogger("Kame");

    private Kame kame; 

    @Before
    public void setUp() throws Exception {	
	String key = "01234567890123456";
	CharSequence salt = "012345678";
	key = KeyGenerators.string().generateKey();	
	kame = new KameImpl(key, salt);
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
