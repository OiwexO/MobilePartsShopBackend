package com.nulp.mobilepartsshop.exception.part;

public class PartTypeNotFoundException extends Exception {

    public PartTypeNotFoundException() {
        super();
    }

    public PartTypeNotFoundException(String message) {
        super(message);
    }

    public PartTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public PartTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
