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
            throw new ConversionException("error.localeNull");
        }

        if (text == null || text.isBlank()) {
            throw new ConversionException("error.inputNullOrEmpty");
        }

        String language = locale.getLanguage();

        if ("tr".equalsIgnoreCase(language)) {
            return turkishConverter.wordsToNumber(text);
        }

        if ("en".equalsIgnoreCase(language)) {
            return englishConverter.wordsToNumber(text);
        }

        throw new ConversionException("error.unsupportedLocale", locale);
    }

    @Override
    public String numberToWords(int number, Locale locale) throws ConversionException {

        if (locale == null) {
            throw new ConversionException("error.localeNull");
        }

        String language = locale.getLanguage();

        if ("tr".equalsIgnoreCase(language)) {
            return turkishConverter.numberToWords(number);
        }

        if ("en".equalsIgnoreCase(language)) {
            return englishConverter.numberToWords(number);
        }

        throw new ConversionException("error.unsupportedLocale", locale);
    }
}