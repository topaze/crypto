package net.topaze.kame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author topaze
 *
 */
public class KameCli {

    private static final Logger LOGGER = LoggerFactory.getLogger(KameCli.class);

    private static final CharSequence DEFAULT_SALT = "0000000000000000";

    private String[] args = null;
    private Options options = new Options();

    private Kame kame = null;

    /**
     * @param args
     */
    public KameCli(final String[] args) {
	this.args = args;

	options.addOption("h", "help", false, "show help.");
	options.addOption("e", "encrypt", false, "encrypt");
	options.addOption("d", "decrypt", false, "decrypt");
	options.addOption("p", "password", true, "password");


    }

    /**
     * 
     */
    private void parse() {
	CommandLineParser parser = new DefaultParser();
	CommandLine cmd = null;
	try {

	    cmd = parser.parse(options, args);
	    if (cmd.hasOption("h")) {
		help();
	    }

	    if(cmd.hasOption("p")) {
		String password = cmd.getOptionValue("p");
		Kame kame = new net.topaze.kame.impl.springsecurity.KameImpl(password, DEFAULT_SALT);
		//Kame kame = new net.topaze.kame.impl.jce.KameImpl(password, DEFAULT_SALT);
		setKame(kame);
		if (cmd.hasOption("e")) {		
		    String plain = StringUtils.join(cmd.getArgs());			
		    LOGGER.info("{} => {}", plain, kame.encrypt(plain));
		} else if (cmd.hasOption("d")) {
		    String hexCipher = StringUtils.join(cmd.getArgs());
		    LOGGER.info("{} => {}", hexCipher, kame.decrypt(hexCipher));
		} else {
		    help();
		}
	    } else {
		help();
	    }

	} catch (ParseException e) {
	    LOGGER.error("Failed to parse comand line properties", e);
	    help();
	}
    }

    /**
     * 
     */
    private void help() {
	HelpFormatter formater = new HelpFormatter();
	formater.printHelp("Main", options);	
	System.exit(0);	
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
	new KameCli(args).parse();
	//	String[] myArgs = new String[]{"-e","-p","toto","hello,","world!"};			
	//	myArgs = new String[]{"-d","-p","toto","309710fc08cb4557964162986d420de964d6c8cab71daa08e2ceff3ea82c1715"};
	//	cli = new KameCli(myArgs);
	//	cli.parse();
    }

    public Kame getKame() {
	return kame;
    }

    public void setKame(Kame kame) {
	this.kame = kame;
    }
}
