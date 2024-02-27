package com.nulp.mobilepartsshop.exception.image;

@SuppressWarnings("unused")
public class ImageSaveException extends ImageStoreException {

    public ImageSaveException() {
        super();
    }

    public ImageSaveException(String message) {
        super(message);
    }

    public ImageSaveException(Throwable cause) {
        super(cause);
    }

    public ImageSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}

