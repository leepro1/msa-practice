package com.msa.user_service.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String msg) {
        super(msg);
    }
}
