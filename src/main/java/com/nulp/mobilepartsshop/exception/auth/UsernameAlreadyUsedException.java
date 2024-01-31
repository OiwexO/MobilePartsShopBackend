package com.nulp.mobilepartsshop.exception.auth;

public class UsernameAlreadyUsedException extends AuthenticationException {

    public UsernameAlreadyUsedException(String message) {
        super(message);
    }

    public UsernameAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}
