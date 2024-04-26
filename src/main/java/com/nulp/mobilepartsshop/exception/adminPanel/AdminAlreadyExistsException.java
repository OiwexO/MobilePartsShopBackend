package com.nulp.mobilepartsshop.exception.adminPanel;

public class AdminAlreadyExistsException extends AdminPanelException {

    public AdminAlreadyExistsException() {
    }

    public AdminAlreadyExistsException(String message) {
        super(message);
    }

    public AdminAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
