package com.nulp.mobilepartsshop.exception.authentication;

public class UsernameNotFoundException extends AuthenticationException {

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
