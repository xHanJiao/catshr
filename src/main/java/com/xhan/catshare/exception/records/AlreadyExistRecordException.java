package com.xhan.catshare.exception.records;

public class AlreadyExistRecordException extends RaiseRecordException{
    public AlreadyExistRecordException() {
    }

    public AlreadyExistRecordException(String message) {
        super(message);
    }

    public AlreadyExistRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistRecordException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
