package com.wordcalc.converter.exception;

public class ConversionException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}