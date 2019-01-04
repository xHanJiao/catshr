package com.xhan.catshare.exception.records;

public class EmailNotFoundException extends RecordException {

    private static final String NOSUCHACCOUNT = "no such email";

    public EmailNotFoundException() {
        super(NOSUCHACCOUNT);
    }

    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotFoundException(Throwable cause) {
        super(cause);
    }

    public EmailNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
