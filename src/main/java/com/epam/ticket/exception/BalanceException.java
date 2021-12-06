package com.epam.ticket.exception;

public class BalanceException extends IllegalStateException {
    public BalanceException(String s) {
        super(s);
    }

    public BalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
