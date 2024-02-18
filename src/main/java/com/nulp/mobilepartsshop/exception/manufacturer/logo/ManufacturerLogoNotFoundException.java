package com.nulp.mobilepartsshop.exception.manufacturer.logo;

@SuppressWarnings("unused")
public class ManufacturerLogoNotFoundException extends ManufacturerLogoException {

    public ManufacturerLogoNotFoundException() {
        super();
    }

    public ManufacturerLogoNotFoundException(String message) {
        super(message);
    }

    public ManufacturerLogoNotFoundException(Throwable cause) {
        super(cause);
    }

    public ManufacturerLogoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
