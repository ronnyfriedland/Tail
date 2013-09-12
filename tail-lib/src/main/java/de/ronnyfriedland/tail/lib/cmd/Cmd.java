package de.ronnyfriedland.tail.lib.cmd;

/**
 * @author Ronny Friedland
 */
public interface Cmd {

    /**
     * Retrieve data from the given source parameter
     * 
     * @param source the source of data
     * @return the data
     */
    String getAvailableData(final String source);
}
