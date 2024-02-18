package com.nulp.mobilepartsshop.exception.image;

@SuppressWarnings("unused")
public class ImageDeleteException extends ImageStoreException {

    public ImageDeleteException() {
        super();
    }

    public ImageDeleteException(String message) {
        super(message);
    }

    public ImageDeleteException(Throwable cause) {
        super(cause);
    }

    public ImageDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
