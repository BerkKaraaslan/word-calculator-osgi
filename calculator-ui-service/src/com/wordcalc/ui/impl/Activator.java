package com.wordcalc.ui.impl;

import java.util.Locale;

import javax.swing.SwingUtilities;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.wordcalc.converter.api.NumberWordConverterService;
import com.wordcalc.ui.swing.CalculatorFrame;

public class Activator implements BundleActivator {

    private CalculatorFrame calculatorFrame;

    @Override
    public void start(BundleContext context) {
        Locale appLocale = resolveApplicationLocale();
        
        NumberWordConverterService converterService = getConverterService(context);

        SwingUtilities.invokeLater(() -> {
            calculatorFrame = new CalculatorFrame(appLocale, converterService);
            calculatorFrame.setVisible(true);
        });
    }

    @Override
    public void stop(BundleContext context) {
        if (calculatorFrame != null) {
            SwingUtilities.invokeLater(() -> calculatorFrame.dispose());
        }
    }

    private NumberWordConverterService getConverterService(BundleContext context) {
        ServiceReference<NumberWordConverterService> reference =
                context.getServiceReference(NumberWordConverterService.class);

        if (reference == null) {
            return null;
        }

        return context.getService(reference);
    }

    private Locale resolveApplicationLocale() {
        String language = System.getProperty("user.language");

        if ("en".equals(language)) {
            return Locale.ENGLISH;
        }

        return new Locale("tr", "TR");
    }
}