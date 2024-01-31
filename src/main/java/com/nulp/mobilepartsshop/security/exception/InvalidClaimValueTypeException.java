package com.nulp.mobilepartsshop.security.exception;

public class InvalidClaimValueTypeException extends RuntimeException {

    public InvalidClaimValueTypeException(String message) {
        super(message);
    }
    public InvalidClaimValueTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
