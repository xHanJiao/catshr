package com.xhan.catshare.exception.records;


public class CurrentRelationNotExistException extends CurrentRelationException {
    private static final String NOTFRIEND = "not friend yet";

    public CurrentRelationNotExistException() {
        super(NOTFRIEND);
    }

    public CurrentRelationNotExistException(String message) {
        super(message);
    }

    public CurrentRelationNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentRelationNotExistException(Throwable cause) {
        super(cause);
    }

    public CurrentRelationNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
