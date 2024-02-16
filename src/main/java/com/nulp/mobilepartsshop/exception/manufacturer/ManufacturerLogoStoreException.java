package com.nulp.mobilepartsshop.exception.manufacturer;

import com.nulp.mobilepartsshop.exception.image.ImageStoreException;

public class ManufacturerLogoStoreException extends ImageStoreException {

    public ManufacturerLogoStoreException(String message) {
        super(message);
    }

    public ManufacturerLogoStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
