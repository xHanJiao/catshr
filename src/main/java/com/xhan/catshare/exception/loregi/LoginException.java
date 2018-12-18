package com.xhan.catshare.exception.loregi;

public class LoginException extends RuntimeException{

    public static final String ERRORINPUT = "error input";
    public static final String NOACCOUNT = "no account";

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
