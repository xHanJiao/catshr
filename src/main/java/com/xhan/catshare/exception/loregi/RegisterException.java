package com.xhan.catshare.exception.loregi;

public class RegisterException extends RuntimeException{

    public static final String ERRORINPUT = "error input";
    public static final String SAMEACCOUNT = "same account";
    public static final String CHECKERROR = "check error";
    public static final String CANNOTSENDMAIL = "cannot send the mail";
    public static final String NOID = "no id";

    public RegisterException() {
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }

    public RegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
