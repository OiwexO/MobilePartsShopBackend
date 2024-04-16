package com.nulp.mobilepartsshop.api.utils;

public abstract class RequestValidator {

    public static boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    protected static boolean isValidString(String str) {
        return str != null && !str.isBlank();
    }
}
