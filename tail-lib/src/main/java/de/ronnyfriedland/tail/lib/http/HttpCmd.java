/*
 * Copyright (c) 2013 T-Systems Multimedia Solutions GmbH
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 *
 * $Id$
 */
package de.ronnyfriedland.tail.lib.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import de.ronnyfriedland.tail.lib.cmd.Cmd;

/**
 * @author Ronny Friedland
 */
public class HttpCmd implements Cmd {

    /** Logger for UrlCmd */
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(HttpCmd.class.getName());

    private static final HttpClient httpClient = new DefaultHttpClient();
    private static long contentLength = 0;
    private static long contentRange = 2048;

    /**
     * {@inheritDoc}
     * 
     * @see de.ronnyfriedland.tail.lib.cmd.Cmd#getAvailableData(java.lang.String)
     */
    @Override
    public String getAvailableData(final String url) {
        if ((null == url) || "".equals(url)) {
            throw new IllegalArgumentException("url must not be empty");
        }
        String result = "";
        try {
            long availableSize = getAvailableContentLength(url);
            // only if new data is available
            if (availableSize > contentLength) {
                long newDataSize;
                if (0 == contentLength) {
                    newDataSize = availableSize;
                } else {
                    newDataSize = Math.min(contentRange, availableSize - contentLength);
                }
                HttpGet get = new HttpGet(url);
                get.addHeader(Headers.RANGE.getValue(),
                        String.format("bytes=%d-%d", contentLength, contentLength + newDataSize));
                HttpResponse response = httpClient.execute(get);
                result = processResponse(response, newDataSize);
                contentLength += newDataSize;
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "error retrieving data", e);
        }
        return result;
    }

    private long getAvailableContentLength(final String url) throws IOException {
        HttpHead head = new HttpHead(url);
        HttpResponse response = httpClient.execute(head);
        Header contentLength = response.getFirstHeader(Headers.CONTENT_LENGTH.getValue());
        return Long.valueOf(contentLength.getValue());
    }

    private String processResponse(final HttpResponse response, final long length) throws IOException {
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();

        String resultString = "";
        try {
            byte[] result = new byte[(int) length];
            in.read(result, 0, (int) length);
            resultString = new String(result, "UTF-8");
        } finally {
            in.close();
        }

        return resultString;
    }
}
