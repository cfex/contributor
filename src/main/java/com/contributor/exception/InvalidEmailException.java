package com.contributor.exception;

public class InvalidEmailException extends Throwable {
    public InvalidEmailException(String detailMessage) {
        super(detailMessage);
    }
}
