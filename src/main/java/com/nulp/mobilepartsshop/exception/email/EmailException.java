package com.nulp.mobilepartsshop.exception.email;

public class EmailException extends Exception {

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
