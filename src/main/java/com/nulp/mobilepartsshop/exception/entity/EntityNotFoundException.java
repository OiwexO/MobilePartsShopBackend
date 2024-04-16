package com.nulp.mobilepartsshop.exception.entity;

public class EntityNotFoundException extends EntityException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

