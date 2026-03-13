package com.wordcalc.converter.impl;

import java.util.Locale;

import com.wordcalc.converter.api.NumberWordConverterService;
import com.wordcalc.converter.en.EnglishNumberConverter;
import com.wordcalc.converter.exception.ConversionException;
import com.wordcalc.converter.tr.TurkishNumberConverter;

public class NumberWordConverterServiceImpl implements NumberWordConverterService {

    private final TurkishNumberConverter turkishConverter = new TurkishNumberConverter();
    private final EnglishNumberConverter englishConverter = new EnglishNumberConverter();

    @Override
    public int wordsToNumber(String text, Locale locale) throws ConversionException {

        if (locale == null) {
            throw new ConversionException("Locale cannot be null.");
        }

        if (text == null || text.isBlank()) {
            throw new ConversionException("Input text cannot be null or empty.");
        }

        String language = locale.getLanguage();

        if ("tr".equalsIgnoreCase(language)) {
            return turkishConverter.wordsToNumber(text);
        }

        if ("en".equalsIgnoreCase(language)) {
            return englishConverter.wordsToNumber(text);
        }

        throw new ConversionException("Unsupported locale: " + locale);
    }

    @Override
    public String numberToWords(int number, Locale locale) throws ConversionException {

        if (locale == null) {
            throw new ConversionException("Locale cannot be null.");
        }

        String language = locale.getLanguage();

        if ("tr".equalsIgnoreCase(language)) {
            return turkishConverter.numberToWords(number);
        }

        if ("en".equalsIgnoreCase(language)) {
            return englishConverter.numberToWords(number);
        }

        throw new ConversionException("Unsupported locale: " + locale);
    }
}