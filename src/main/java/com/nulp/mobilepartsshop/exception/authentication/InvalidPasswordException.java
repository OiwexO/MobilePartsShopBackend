package com.nulp.mobilepartsshop.exception.authentication;

public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
