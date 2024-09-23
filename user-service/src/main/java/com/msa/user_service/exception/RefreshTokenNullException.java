package com.msa.user_service.exception;

public class RefreshTokenNullException extends RuntimeException {

    public RefreshTokenNullException(String msg) {
        super(msg);
    }
}