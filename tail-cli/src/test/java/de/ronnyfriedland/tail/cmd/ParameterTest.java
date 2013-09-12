package de.ronnyfriedland.tail.cmd;

import org.junit.Test;

public class ParameterTest {

    @Test
    public void testParams() throws Exception {
        String[] args = new String[] { "-invalid=http://www.ronnyfriedlan.de" };
        new TailCli().startup(args);

        args = new String[] { "-src" };
        new TailCli().startup(args);

        args = new String[] { "-src=" };
        new TailCli().startup(args);

        args = new String[] { "-src=http://www.ronnyfriedlan.de -interval=test" };
        new TailCli().startup(args);

        args = new String[] { "-src=http://www.ronnyfriedlan.de -interval=1" };
        new TailCli().startup(args);

        args = new String[] { "-src=http://www.ronnyfriedlan.de -interval=1 -type=unknown" };
        new TailCli().startup(args);

        args = new String[] { "-src=http://www.ronnyfriedlan.de -interval=1 -type=http" };
        new TailCli().startup(args);
    }

}
