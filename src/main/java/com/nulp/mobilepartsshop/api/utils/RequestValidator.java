package com.nulp.mobilepartsshop.api.utils;

public abstract class RequestValidator<T> {

    public boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    public abstract boolean isValidRequest(T request);

    protected boolean isValidString(String str) {
        return str != null && !str.isBlank();
    }
}
