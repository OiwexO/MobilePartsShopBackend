package com.nulp.mobilepartsshop.exception.auth;

public class UsernameNotFoundException extends AuthenticationException {

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
