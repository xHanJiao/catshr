package com.xhan.catshare.exception.records;

public class RaiseRecordException extends RecordException{
    public RaiseRecordException() {
    }

    public RaiseRecordException(String message) {
        super(message);
    }

    public RaiseRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public RaiseRecordException(Throwable cause) {
        super(cause);
    }

    public RaiseRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
