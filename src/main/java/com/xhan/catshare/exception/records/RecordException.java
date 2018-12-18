package com.xhan.catshare.exception.records;

public class RecordException extends RuntimeException{

    public static final String WAITING = "please waiting for the accept";
    public static final String NOACCOUNT = "no such account";

    public RecordException() {
    }

    public RecordException(String message) {
        super(message);
    }

    public RecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordException(Throwable cause) {
        super(cause);
    }

    public RecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
