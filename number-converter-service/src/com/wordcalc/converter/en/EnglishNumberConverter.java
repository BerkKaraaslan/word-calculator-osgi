package com.wordcalc.converter.en;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.wordcalc.converter.exception.ConversionException;

public class EnglishNumberConverter {

    private static final String NEGATIVE = "negative";
    private static final String ZERO = "zero";
    private static final String HUNDRED = "hundred";
    private static final String THOUSAND = "thousand";
    private static final String MILLION = "million";
    private static final String BILLION = "billion";

    private static final String[] UNITS = {
            ZERO, "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] TEENS = {
            "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"
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
                    throw new ConversionException("error.invalidScaleOrderEn", text);
                }

                int chunkValue = parseChunk(currentChunkTokens);
                if (chunkValue == 0) {
                    throw new ConversionException("error.invalidScaleUsageEn", text);
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
            parts.add(convertBelowOneThousand(thousands));
            parts.add(THOUSAND);
        }

        if (remainder > 0) {
            parts.add(convertBelowOneThousand(remainder));
        }

        return String.join(" ", parts).trim();
    }

    private String normalize(String text) {
        return text
                .toLowerCase(Locale.ENGLISH)
                .replace("-", " ")
                .replace(",", " ")
                .replaceAll("\\band\\b", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private boolean isScale(String token) {
        return THOUSAND.equals(token) || MILLION.equals(token) || BILLION.equals(token);
    }

    private long getScaleValue(String token) throws ConversionException {
        return switch (token) {
            case THOUSAND -> 1_000L;
            case MILLION -> 1_000_000L;
            case BILLION -> 1_000_000_000L;
            default -> throw new ConversionException("error.unknownEnglishScaleToken", token);
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
        if (index + 1 < tokens.size()
                && unitValue(tokens.get(index)) > 0
                && HUNDRED.equals(tokens.get(index + 1))) {
            value += unitValue(tokens.get(index)) * 100;
            index += 2;
        }

        // Tens / teens / units part
        if (index < tokens.size()) {
            String token = tokens.get(index);

            int teenValue = teenValue(token);
            if (teenValue >= 10) {
                value += teenValue;
                index++;
            } else {
                int tensValue = tensValue(token);
                if (tensValue >= 20) {
                    value += tensValue;
                    index++;

                    if (index < tokens.size()) {
                        int unitValue = unitValue(tokens.get(index));
                        if (unitValue > 0) {
                            value += unitValue;
                            index++;
                        }
                    }
                } else {
                    int unitValue = unitValue(token);
                    if (unitValue > 0) {
                        value += unitValue;
                        index++;
                    } else {
                        throw new ConversionException("error.invalidEnglishNumberToken", token);
                    }
                }
            }
        }

        if (index != tokens.size()) {
            throw new ConversionException("error.invalidEnglishChunk", String.join(" ", tokens));
        }

        return value;
    }

    private int unitValue(String token) {
        return switch (token) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> -1;
        };
    }

    private int teenValue(String token) {
        return switch (token) {
            case "ten" -> 10;
            case "eleven" -> 11;
            case "twelve" -> 12;
            case "thirteen" -> 13;
            case "fourteen" -> 14;
            case "fifteen" -> 15;
            case "sixteen" -> 16;
            case "seventeen" -> 17;
            case "eighteen" -> 18;
            case "nineteen" -> 19;
            default -> -1;
        };
    }

    private int tensValue(String token) {
        return switch (token) {
            case "twenty" -> 20;
            case "thirty" -> 30;
            case "forty" -> 40;
            case "fifty" -> 50;
            case "sixty" -> 60;
            case "seventy" -> 70;
            case "eighty" -> 80;
            case "ninety" -> 90;
            default -> -1;
        };
    }

    private String convertBelowOneThousand(int number) {
        List<String> parts = new ArrayList<>();

        int hundreds = number / 100;
        int remainder = number % 100;

        if (hundreds > 0) {
            parts.add(UNITS[hundreds]);
            parts.add(HUNDRED);
        }

        if (remainder >= 20) {
            parts.add(TENS[remainder / 10]);
            if (remainder % 10 > 0) {
                parts.add(UNITS[remainder % 10]);
            }
        } else if (remainder >= 10) {
            parts.add(TEENS[remainder - 10]);
        } else if (remainder > 0) {
            parts.add(UNITS[remainder]);
        }

        return String.join(" ", parts);
    }

    private void ensureIntRange(long value, String originalText) throws ConversionException {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new ConversionException("error.valueOutOfRange", originalText);
        }
    }
}