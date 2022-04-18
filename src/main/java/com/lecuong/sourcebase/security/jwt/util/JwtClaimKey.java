package com.lecuong.sourcebase.security.jwt.util;

public enum JwtClaimKey {

    ID("id"),
    USERNAME("userName"),
    ROLE("role");

    private String value;

    private JwtClaimKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
