package de.ronnyfriedland.tail.lib;

import java.io.IOException;

import de.ronnyfriedland.tail.lib.http.HttpCmd;
import de.ronnyfriedland.tail.lib.script.ScriptCmd;

/**
 * @author Ronny Friedland
 */
public class Tail {

    public enum Source {
        HTTP("http"), SCRIPT("script");
        private String source;

        private Source(final String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

    }

    public Tail() {
        super();
    }

    public String getAvailableData(final Source sourceType, final String source) throws IOException {
        if ((null == sourceType)) {
            throw new IllegalArgumentException("source type must not be empty");
        }
        if ((null == source) || "".equals(source)) {
            throw new IllegalArgumentException("source must not be empty");
        }
        switch (sourceType) {
        case HTTP:
            new HttpCmd().getAvailableData(source);
            break;
        case SCRIPT:
            new ScriptCmd().getAvailableData(source);
            break;
        default:
            throw new IllegalArgumentException("unknown source : " + sourceType);
        }
        return null;
    }

}