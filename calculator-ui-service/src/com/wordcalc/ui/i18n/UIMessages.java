package com.wordcalc.ui.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class UIMessages {

    private static final String BUNDLE_NAME = "com.wordcalc.ui.i18n.messages";

    private UIMessages() {
    }

    public static String get(String key, Locale locale) {
        Locale effectiveLocale = resolveLocale(locale);

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, effectiveLocale);
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }
    
    public static String get(String key, Locale locale, Object... args) {
        Locale effectiveLocale = resolveLocale(locale);

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, effectiveLocale);
            String pattern = bundle.getString(key);

            MessageFormat format = new MessageFormat(pattern, effectiveLocale);
            return format.format(args);

        } catch (MissingResourceException e) {
            return key;
        }
    }

    private static Locale resolveLocale(Locale locale) {
        if (locale != null && "en".equalsIgnoreCase(locale.getLanguage())) {
            return Locale.ENGLISH;
        }
        return Locale.ROOT;
    }
}
