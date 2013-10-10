package de.ronnyfriedland.tail.lib.script;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import de.ronnyfriedland.tail.lib.cmd.Cmd;

/**
 * Access logdata via custom script
 * 
 * @author Ronny Friedland
 */
public class ScriptCmd implements Cmd {

    /** Logger for {@link ScriptCmd} */
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(ScriptCmd.class.getName());

    private static int contentLength = 0;
    private static int contentRange = 2048;

    /**
     * {@inheritDoc}
     * 
     * @see de.ronnyfriedland.tail.lib.cmd.Cmd#getAvailableData(java.lang.String)
     */
    @Override
    public String getAvailableData(final String script) {
        if ((null == script) || "".equals(script)) {
            throw new IllegalArgumentException("script must not be empty");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        CommandLine cmdLine = CommandLine.parse(script);
        DefaultExecutor executor = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "error calling script", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                LOG.log(Level.WARNING, "error closing data stream", e);
            }
        }

        String result = "";
        int availableSize = outputStream.size();
        if (availableSize > contentLength) {
            int newDataSize;
            if (0 == contentLength) {
               if(availableSize > contentRange) {
                   contentLength = availableSize - contentRange;
                   newDataSize = availableSize;
               } else {
                   newDataSize = contentRange;
               }
            } else {
               newDataSize = Math.min(contentRange, availableSize - contentLength);
            }
            result = StringUtils.substring(outputStream.toString(), contentLength, contentLength + newDataSize);
            contentLength += newDataSize;
        }
        return result;
    }
}
