package com.xhan.catshare.exception.records;

public class AccountNotFoundException extends RecordException {

    private static final String NOSUCHACCOUNT = "no such account";

    public AccountNotFoundException() {
        super(NOSUCHACCOUNT);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }

    public AccountNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
