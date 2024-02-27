package com.nulp.mobilepartsshop.exception.authentication;

public class UsernameAlreadyUsedException extends AuthenticationException {

    public UsernameAlreadyUsedException(String message) {
        super(message);
    }
}
