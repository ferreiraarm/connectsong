package com.amf.connectsong.model;

public enum ESpotiftKeys {
    CLIENT_ID("4680b0b539be45f5ae13d2bcae7d2b0f"),
    CLIENT_SECRET("85f01fce6dcf4d62a175699ce55e5c45");

    private String value;

    private ESpotiftKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
