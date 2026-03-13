package com.wordcalc.ui.action;

import java.util.Locale;

import com.wordcalc.converter.api.NumberWordConverterService;
import com.wordcalc.converter.exception.ConversionException;

public class CalculatorAction {

    private final NumberWordConverterService converterService;
    private final Locale locale;

    public CalculatorAction(NumberWordConverterService converterService, Locale locale) {
        this.converterService = converterService;
        this.locale = locale;
    }

    public String add(String left, String right) throws ConversionException {

        int leftValue = converterService.wordsToNumber(left, locale);
        int rightValue = converterService.wordsToNumber(right, locale);

        int result = leftValue + rightValue;

        return converterService.numberToWords(result, locale);
    }

    public String subtract(String left, String right) throws ConversionException {

        int leftValue = converterService.wordsToNumber(left, locale);
        int rightValue = converterService.wordsToNumber(right, locale);

        int result = leftValue - rightValue;

        return converterService.numberToWords(result, locale);
    }

    public String multiply(String left, String right) throws ConversionException {

        int leftValue = converterService.wordsToNumber(left, locale);
        int rightValue = converterService.wordsToNumber(right, locale);

        int result = leftValue * rightValue;

        return converterService.numberToWords(result, locale);
    }

    public String divide(String left, String right) throws ConversionException {

        int leftValue = converterService.wordsToNumber(left, locale);
        int rightValue = converterService.wordsToNumber(right, locale);

        if (rightValue == 0) {
            throw new ConversionException("Division by zero");
        }

        int result = leftValue / rightValue;

        return converterService.numberToWords(result, locale);
    }
}