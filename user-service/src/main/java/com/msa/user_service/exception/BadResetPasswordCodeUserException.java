package com.msa.user_service.exception;

public class BadResetPasswordCodeUserException extends RuntimeException {

    public BadResetPasswordCodeUserException(String msg) {
        super(msg);
    }
}
