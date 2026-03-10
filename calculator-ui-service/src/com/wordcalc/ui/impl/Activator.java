package com.wordcalc.ui.impl;

import java.util.Locale;

import javax.swing.SwingUtilities;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.wordcalc.ui.swing.CalculatorFrame;

public class Activator implements BundleActivator {

    private CalculatorFrame calculatorFrame;

    @Override
    public void start(BundleContext context) {
        System.out.println("calculator-ui-service started");

        Locale appLocale = resolveApplicationLocale();

        SwingUtilities.invokeLater(() -> {
            calculatorFrame = new CalculatorFrame(appLocale);
            calculatorFrame.setVisible(true);
        });
    }

    @Override
    public void stop(BundleContext context) {
        if (calculatorFrame != null) {
            SwingUtilities.invokeLater(() -> calculatorFrame.dispose());
        }
    }

    private Locale resolveApplicationLocale() {
        Locale systemLocale = Locale.getDefault();

        if (systemLocale != null && "en".equalsIgnoreCase(systemLocale.getLanguage())) {
            return Locale.ENGLISH;
        }

        return new Locale("tr", "TR");
    }
}
