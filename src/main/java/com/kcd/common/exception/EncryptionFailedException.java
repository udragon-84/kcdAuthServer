package com.kcd.common.exception;

public class EncryptionFailedException extends RuntimeException {
    public EncryptionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
