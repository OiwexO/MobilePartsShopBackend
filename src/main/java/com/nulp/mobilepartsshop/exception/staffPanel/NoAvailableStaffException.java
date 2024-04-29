package com.nulp.mobilepartsshop.exception.staffPanel;

public class NoAvailableStaffException extends StaffPanelException {

    public NoAvailableStaffException() {
    }

    public NoAvailableStaffException(String message) {
        super(message);
    }

    public NoAvailableStaffException(String message, Throwable cause) {
        super(message, cause);
    }
}
