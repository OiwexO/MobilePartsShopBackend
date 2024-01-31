package com.nulp.mobilepartsshop.exception.security.jwt;

public class InvalidClaimValueException extends RuntimeException {

    public InvalidClaimValueException(String message) {
        super(message);
    }
    public InvalidClaimValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
