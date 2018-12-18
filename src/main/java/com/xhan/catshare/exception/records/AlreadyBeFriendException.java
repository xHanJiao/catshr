package com.xhan.catshare.exception.records;

public class AlreadyBeFriendException extends RaiseRecordException{
    public AlreadyBeFriendException() {
    }

    public AlreadyBeFriendException(String message) {
        super(message);
    }

    public AlreadyBeFriendException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyBeFriendException(Throwable cause) {
        super(cause);
    }

    public AlreadyBeFriendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
