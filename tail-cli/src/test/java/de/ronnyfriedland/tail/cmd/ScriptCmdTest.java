package de.ronnyfriedland.tail.cmd;

import org.junit.Assert;
import org.junit.Test;

import de.ronnyfriedland.tail.lib.script.ScriptCmd;

public class ScriptCmdTest {

    @Test
    public void test() {
        String data = new ScriptCmd().getAvailableData("java -version");
        Assert.assertTrue(data.startsWith("java version "));
    }

}
