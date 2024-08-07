package com.demoBank.core.model;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
