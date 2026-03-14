package com.wordcalc.converter.exception;

public class ConversionException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String messageKey;
    private final Object[] args;

    public ConversionException(String messageKey, Object... args) {
        this.messageKey = messageKey;
        this.args = args;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args;
    }
}