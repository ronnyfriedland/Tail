package de.ronnyfriedland.tail.lib.enums;

public enum Headers {

    ACCEPT_RANGES("Accept-Ranges"), RANGE("Range"), CONTENT_LENGTH("Content-Length");

    private final String value;

    private Headers(final String aValue) {
        this.value = aValue;
    }

    public String getValue() {
        return value;
    }
}
