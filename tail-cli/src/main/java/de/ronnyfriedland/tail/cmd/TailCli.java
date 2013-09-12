package de.ronnyfriedland.tail.cmd;

import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.ronnyfriedland.tail.lib.cmd.Cmd;
import de.ronnyfriedland.tail.lib.http.HttpCmd;
import de.ronnyfriedland.tail.lib.script.ScriptCmd;

/**
 * Command line tool to tail logfiles.
 * 
 * @author Ronny Friedland
 */
@SuppressWarnings("static-access")
public class TailCli {

    private transient Options options = new Options();

    /**
     * Creates a new {@link TailCli} instance.
     */
    public TailCli() {
        Option optionsrc = OptionBuilder.withArgName("src").hasArg().withDescription("src of logfile")
                .withType(String.class).isRequired().create("src");
        options.addOption(optionsrc);

        Option optiontype = OptionBuilder.withArgName("type").hasArg().withDescription("type of source (http|script)")
                .withType(String.class).isRequired().create("type");
        options.addOption(optiontype);

        Option optionInterval = OptionBuilder.withArgName("interval").hasArg()
                .withDescription("interval of retrieve (default: 30 seconds)").withType(Integer.class)
                .create("interval");
        options.addOption(optionInterval);
    }

    /**
     * Initialize with the arguments given
     * 
     * @param args the arguments
     * @throws Exception any unexpected exception
     */
    public void startup(final String[] args) throws Exception {
        CommandLine line;
        try {
            CommandLineParser parser = new GnuParser();
            line = parser.parse(options, args);
        } catch (ParseException e) {
            printUsage();
            return;
        }

        // mandatory
        String src = line.getOptionValue("src");
        String type = line.getOptionValue("type");
        // optional
        Integer interval = Integer.valueOf(line.getOptionValue("interval", "30"));

        Cmd cmd;
        if ("http".equals(type)) {
            cmd = new HttpCmd();
        } else if ("script".equals(type)) {
            cmd = new ScriptCmd();
        } else {
            printUsage();
            return;
        }
        while (true) {
            System.out.print(cmd.getAvailableData(src));
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(interval));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(TailCli.class.getSimpleName(), options);
    }

    /**
     * The main method.
     * 
     * @param args the arguments
     * @throws Exception any unexpected exception
     */
    public static void main(final String[] args) throws Exception {
        new TailCli().startup(args);
    }

}
