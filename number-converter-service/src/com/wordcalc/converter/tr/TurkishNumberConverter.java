package com.wordcalc.converter.tr;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.wordcalc.converter.exception.ConversionException;

public class TurkishNumberConverter {

    private static final String NEGATIVE = "eksi";
    private static final String ZERO = "sıfır";
    private static final String HUNDRED = "yüz";
    private static final String THOUSAND = "bin";
    private static final String MILLION = "milyon";
    private static final String BILLION = "milyar";

    private static final String[] UNITS = {
            ZERO, "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz"
    };

    private static final String[] TENS = {
            "", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan"
    };

    public int wordsToNumber(String text) throws ConversionException {
        if (text == null || text.isBlank()) {
            throw new ConversionException("error.inputNullOrBlank");
        }

        String normalized = normalize(text);
        if (normalized.isEmpty()) {
            throw new ConversionException("error.inputEmpty");
        }

        boolean negative = false;
        if (normalized.startsWith(NEGATIVE + " ")) {
            negative = true;
            normalized = normalized.substring((NEGATIVE + " ").length()).trim();
        } else if (normalized.equals(NEGATIVE)) {
            throw new ConversionException("error.invalidNegativeExpression");
        }

        if (ZERO.equals(normalized)) {
            return 0;
        }

        String[] tokens = normalized.split(" ");
        List<String> currentChunkTokens = new ArrayList<>();

        long total = 0;
        long lastScale = Long.MAX_VALUE;

        for (String token : tokens) {
            if (isScale(token)) {
                long scaleValue = getScaleValue(token);

                if (scaleValue >= lastScale) {
                    throw new ConversionException("error.invalidScaleOrderTr", text);
                }

                int chunkValue;
                if (THOUSAND.equals(token) && currentChunkTokens.isEmpty()) {
                    chunkValue = 1;
                } else {
                    chunkValue = parseChunk(currentChunkTokens);
                    if (chunkValue == 0) {
                        throw new ConversionException("error.invalidScaleUsageTr", text);
                    }
                }

                total += chunkValue * scaleValue;
                ensureIntRange(total, text);

                currentChunkTokens.clear();
                lastScale = scaleValue;
            } else {
                currentChunkTokens.add(token);
            }
        }

        int remaining = parseChunk(currentChunkTokens);
        total += remaining;
        ensureIntRange(total, text);

        if (negative) {
            total = -total;
            ensureIntRange(total, text);
        }

        return (int) total;
    }

    public String numberToWords(int number) {
        if (number == 0) {
            return ZERO;
        }

        long value = number;
        boolean negative = value < 0;
        long absValue = Math.abs(value);

        int billions = (int) (absValue / 1_000_000_000L);
        absValue %= 1_000_000_000L;

        int millions = (int) (absValue / 1_000_000L);
        absValue %= 1_000_000L;

        int thousands = (int) (absValue / 1_000L);
        int remainder = (int) (absValue % 1_000L);

        List<String> parts = new ArrayList<>();

        if (negative) {
            parts.add(NEGATIVE);
        }

        if (billions > 0) {
            parts.add(convertBelowOneThousand(billions));
            parts.add(BILLION);
        }

        if (millions > 0) {
            parts.add(convertBelowOneThousand(millions));
            parts.add(MILLION);
        }

        if (thousands > 0) {
            if (thousands == 1) {
                parts.add(THOUSAND);
            } else {
                parts.add(convertBelowOneThousand(thousands));
                parts.add(THOUSAND);
            }
        }

        if (remainder > 0) {
            parts.add(convertBelowOneThousand(remainder));
        }

        return String.join(" ", parts).trim();
    }

    private String normalize(String text) {
        String normalized = text
                .toLowerCase(new Locale("tr", "TR"))
                .replaceAll("\\s+", " ")
                .trim();
        return normalized;
    }

    private boolean isScale(String token) {
        return THOUSAND.equals(token) || MILLION.equals(token) || BILLION.equals(token);
    }

    private long getScaleValue(String token) throws ConversionException {
        return switch (token) {
            case THOUSAND -> 1_000L;
            case MILLION -> 1_000_000L;
            case BILLION -> 1_000_000_000L;
            default -> throw new ConversionException("error.unknownTurkishScaleToken", token);
        };
    }

    private int parseChunk(List<String> tokens) throws ConversionException {
        if (tokens.isEmpty()) {
            return 0;
        }

        if (tokens.size() == 1 && ZERO.equals(tokens.get(0))) {
            return 0;
        }

        int index = 0;
        int value = 0;

        // Hundreds part
        if (index < tokens.size()) {
            String token = tokens.get(index);

            if (HUNDRED.equals(token)) {
                value += 100;
                index++;
            } else {
                int unitValue = unitValue(token);
                if (unitValue > 0 && index + 1 < tokens.size() && HUNDRED.equals(tokens.get(index + 1))) {
                    value += unitValue * 100;
                    index += 2;
                }
            }
        }

        // Tens part
        if (index < tokens.size()) {
            int tensValue = tensValue(tokens.get(index));
            if (tensValue > 0) {
                value += tensValue;
                index++;
            }
        }

        // Units part
        if (index < tokens.size()) {
            int unitValue = unitValue(tokens.get(index));
            if (unitValue >= 0 && !ZERO.equals(tokens.get(index))) {
                value += unitValue;
                index++;
            } else {
                throw new ConversionException("error.invalidTurkishNumberToken", tokens.get(index));
            }
        }

        if (index != tokens.size()) {
            throw new ConversionException("error.invalidTurkishChunk", String.join(" ", tokens));
        }

        return value;
    }

    private int unitValue(String token) throws ConversionException {
        return switch (token) {
            case ZERO -> 0;
            case "bir" -> 1;
            case "iki" -> 2;
            case "üç" -> 3;
            case "dört" -> 4;
            case "beş" -> 5;
            case "altı" -> 6;
            case "yedi" -> 7;
            case "sekiz" -> 8;
            case "dokuz" -> 9;
            default -> -1;
        };
    }

    private int tensValue(String token) {
        return switch (token) {
            case "on" -> 10;
            case "yirmi" -> 20;
            case "otuz" -> 30;
            case "kırk" -> 40;
            case "elli" -> 50;
            case "altmış" -> 60;
            case "yetmiş" -> 70;
            case "seksen" -> 80;
            case "doksan" -> 90;
            default -> -1;
        };
    }

    private String convertBelowOneThousand(int number) {
        List<String> parts = new ArrayList<>();

        int hundreds = number / 100;
        int tens = (number % 100) / 10;
        int units = number % 10;

        if (hundreds > 0) {
            if (hundreds > 1) {
                parts.add(UNITS[hundreds]);
            }
            parts.add(HUNDRED);
        }

        if (tens > 0) {
            parts.add(TENS[tens]);
        }

        if (units > 0) {
            parts.add(UNITS[units]);
        }

        return String.join(" ", parts);
    }

    private void ensureIntRange(long value, String originalText) throws ConversionException {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new ConversionException("error.valueOutOfRange", originalText);
        }
    }
}