package com.xhan.catshare.exception.records;

public class CurrentRelationException extends RecordException{
    public CurrentRelationException() {
    }

    public CurrentRelationException(String message) {
        super(message);
    }

    public CurrentRelationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentRelationException(Throwable cause) {
        super(cause);
    }

    public CurrentRelationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
