package com.wordcalc.converter.api;

import java.util.Locale;

import com.wordcalc.converter.exception.ConversionException;

public interface NumberWordConverterService {

    int wordsToNumber(String text, Locale locale) throws ConversionException;

    String numberToWords(int number, Locale locale) throws ConversionException;
}
