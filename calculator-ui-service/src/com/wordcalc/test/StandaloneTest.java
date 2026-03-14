package com.wordcalc.test;

import java.util.Locale;

import javax.swing.SwingUtilities;

import com.wordcalc.converter.impl.NumberWordConverterServiceImpl;
import com.wordcalc.ui.swing.CalculatorFrame;

public class StandaloneTest {

    public static void main(String[] args) {

        Locale locale = new Locale("tr", "TR");

        var converter = new NumberWordConverterServiceImpl();

        SwingUtilities.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame(locale, converter);
            frame.setVisible(true);
        });
    }
}