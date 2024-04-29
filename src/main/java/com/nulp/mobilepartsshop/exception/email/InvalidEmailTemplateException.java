package com.nulp.mobilepartsshop.exception.email;

public class InvalidEmailTemplateException extends EmailException {

    public InvalidEmailTemplateException() {
    }

    public InvalidEmailTemplateException(String message) {
        super(message);
    }

    public InvalidEmailTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
