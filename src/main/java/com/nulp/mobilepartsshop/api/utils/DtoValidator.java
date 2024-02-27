package com.nulp.mobilepartsshop.api.utils;

public abstract class DtoValidator {
    public static boolean isValidId(Long id) {
        return id > 0;
    }
}
