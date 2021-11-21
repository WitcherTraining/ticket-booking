package com.epam.ticket.exception;

public class EntityNotFoundException extends IllegalArgumentException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String s) {
        super(s);
    }
}
