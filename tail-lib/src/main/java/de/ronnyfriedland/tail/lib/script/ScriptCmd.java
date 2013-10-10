package de.ronnyfriedland.tail.lib.script;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

import de.ronnyfriedland.tail.lib.cmd.Cmd;

/**
 * Access logdata via custom script
 * 
 * @author Ronny Friedland
 */
public class ScriptCmd implements Cmd {

    /** Logger for {@link ScriptCmd} */
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(ScriptCmd.class.getName());

    private static long contentLength = 0;
    private static long contentRange = 2048;

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

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            long availableSize = outputStream.size();
            if (availableSize > contentLength) {
                long newDataSize;
                if (0 == contentLength) {
                    contentLength = availableSize - contentRange;
                    newDataSize = contentRange;
                } else {
                    newDataSize = Math.min(contentRange, availableSize - contentLength);
                }
                IOUtils.copyLarge(new ByteArrayInputStream(outputStream.toByteArray()), result, contentLength,
                        contentLength + newDataSize);
                contentLength += newDataSize;
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "error retrieving data", e);
        }
        return (result.toString());
    }
}
