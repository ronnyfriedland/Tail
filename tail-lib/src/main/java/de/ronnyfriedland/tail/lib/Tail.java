package de.ronnyfriedland.tail.lib;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import de.ronnyfriedland.tail.lib.enums.Headers;

/**
 * @author Ronny Friedland
 */
public class Tail {

    private static final HttpClient httpClient = new DefaultHttpClient();
    private static long contentLength = 0;
    private static long contentRange = 2048;

    public Tail() {
        super();
    }

    public String getAvailableData(final String url) throws IOException {
        if ((null == url) || "".equals(url)) {
            throw new IllegalArgumentException("url must not be empty");
        }
        String result = "";
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
        return result;
    }

    private long getAvailableContentLength(final String url) throws IOException {
        HttpHead head = new HttpHead(url);
        HttpResponse response = httpClient.execute(head);
        Header acceptRanges = response.getFirstHeader(Headers.ACCEPT_RANGES.getValue());
        if (null == acceptRanges) {
            throw new IllegalArgumentException(String.format("ranges not supported for url %s", url));
        }
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