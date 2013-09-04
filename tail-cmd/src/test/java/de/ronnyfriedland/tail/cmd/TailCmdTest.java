package de.ronnyfriedland.tail.cmd;


public class TailCmdTest {

    public void testParams() throws Exception {
        String[] args = new String[] { "-invalid=http://www.ronnyfriedlan.de" };
        new TailCmd().startup(args);

        args = new String[] { "-url" };
        new TailCmd().startup(args);

        args = new String[] { "-url=" };
        new TailCmd().startup(args);

        args = new String[] { "-url=http://www.ronnyfriedlan.de -interval=test" };
        new TailCmd().startup(args);

        args = new String[] { "-url=http://www.ronnyfriedlan.de -interval=1" };
        new TailCmd().startup(args);
    }

}
