package com.xhan.catshare.exception.records;

public class RecordException extends RuntimeException{

    public static final String WAITING = "please waiting for the accept";
    public static final String NOACCOUNT = "no such email";

    RecordException() {
    }

    RecordException(String message) {
        super(message);
    }

    RecordException(String message, Throwable cause) {
        super(message, cause);
    }

    RecordException(Throwable cause) {
        super(cause);
    }

    RecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
