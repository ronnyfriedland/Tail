package de.ronnyfriedland.tail.cmd;

import org.junit.Assert;
import org.junit.Test;

import de.ronnyfriedland.tail.lib.script.ScriptCmd;

public class ScriptCmdTest {

    @Test
    public void test() {
        String data = new ScriptCmd().getAvailableData("java -version");
System.err.println("----------------------------------22222------------");
System.err.println(data);
        Assert.assertTrue(data.startsWith("java version "));
    }

}
