package com.nulp.mobilepartsshop.exception.auth;

public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}