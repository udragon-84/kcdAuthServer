package com.kcd.common.exception;

public class DecryptionFailedException extends RuntimeException {
    public DecryptionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
