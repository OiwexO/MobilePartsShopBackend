package com.nulp.mobilepartsshop.security.exception;

public class InvalidClaimValueException extends RuntimeException {

    public InvalidClaimValueException(String message) {
        super(message);
    }
    public InvalidClaimValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
