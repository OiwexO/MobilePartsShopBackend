package com.nulp.mobilepartsshop.security.exception;

public class ClaimNotFoundException extends RuntimeException {

    public ClaimNotFoundException(String message) {
        super(message);
    }
    public ClaimNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}