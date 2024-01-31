package com.nulp.mobilepartsshop.exception.security.jwt;

public class InvalidClaimValueTypeException extends RuntimeException {

    public InvalidClaimValueTypeException(String message) {
        super(message);
    }
    public InvalidClaimValueTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
