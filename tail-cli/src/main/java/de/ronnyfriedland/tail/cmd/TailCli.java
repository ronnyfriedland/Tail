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

import de.ronnyfriedland.tail.lib.Tail;
import de.ronnyfriedland.tail.lib.Tail.Source;

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
        Option optionUrl = OptionBuilder.withArgName("url").hasArg().withDescription("url of logfile")
                .withType(String.class).create("url");
        options.addOption(optionUrl);

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

        String url = null;
        Integer interval = null;
        // mandatory
        if (line.hasOption("url")) {
            url = line.getOptionValue("url");
        } else {
            printUsage();
            return;
        }
        // optional
        interval = Integer.valueOf(line.getOptionValue("interval", "30"));

        Tail tail = new Tail();
        while (true) {
            // TODO: add possibility to choose
            System.out.print(tail.getAvailableData(Source.HTTP, url));
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
