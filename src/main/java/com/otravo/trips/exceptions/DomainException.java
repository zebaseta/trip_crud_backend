package com.otravo.trips.exceptions;

public class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable t) {
        super(message, t);
    }
}
