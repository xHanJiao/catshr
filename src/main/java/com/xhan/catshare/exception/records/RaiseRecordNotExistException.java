package com.xhan.catshare.exception.records;

public class RaiseRecordNotExistException extends RaiseRecordException{

    private static final String MESSAGE = "record not found";

    public RaiseRecordNotExistException() {
        super(MESSAGE);
    }

    public RaiseRecordNotExistException(String message) {
        super(message);
    }

    public RaiseRecordNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RaiseRecordNotExistException(Throwable cause) {
        super(cause);
    }

    public RaiseRecordNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
