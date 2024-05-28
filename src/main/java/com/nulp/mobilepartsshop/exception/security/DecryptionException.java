package com.nulp.mobilepartsshop.exception.security;

public class DecryptionException extends Exception {

    public DecryptionException() {
    }

    public DecryptionException(Throwable cause) {
        super(cause);
    }

    public DecryptionException(String message) {
        super(message);
    }

    public DecryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
