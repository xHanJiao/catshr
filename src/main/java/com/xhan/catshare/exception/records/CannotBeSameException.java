package com.xhan.catshare.exception.records;

public class CannotBeSameException extends RecordException {
    public CannotBeSameException() {
        super("the email cannot be same");
    }

    public CannotBeSameException(String message) {
        super(message);
    }

    public CannotBeSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotBeSameException(Throwable cause) {
        super(cause);
    }

    public CannotBeSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
