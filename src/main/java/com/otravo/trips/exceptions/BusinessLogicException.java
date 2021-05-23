package com.otravo.trips.exceptions;

public class BusinessLogicException extends Exception {
    public BusinessLogicException(String message) {
        super(message);
    }
    public BusinessLogicException(String message, Throwable t) {
        super(message, t);
    }
}
