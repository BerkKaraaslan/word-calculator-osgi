package com.wordcalc.ui.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wordcalc.ui.i18n.UIMessages;

public class CalculatorFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Locale locale;

    private JTextField firstNumberField;
    private JTextField secondNumberField;
    private JTextField resultField;

    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;

    public CalculatorFrame(Locale locale) {
        this.locale = locale;
        initialize();
    }

    private void initialize() {
        setTitle(UIMessages.get("window.title", locale));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 240);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel firstNumberLabel = new JLabel(UIMessages.get("label.firstNumber", locale));
        JLabel secondNumberLabel = new JLabel(UIMessages.get("label.secondNumber", locale));
        JLabel resultLabel = new JLabel(UIMessages.get("label.result", locale));

        firstNumberField = new JTextField(20);
        secondNumberField = new JTextField(20);
        resultField = new JTextField(20);
        resultField.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(firstNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(firstNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(secondNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(secondNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(resultLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(resultField, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        addButton = new JButton(UIMessages.get("button.add", locale));
        subtractButton = new JButton(UIMessages.get("button.subtract", locale));
        multiplyButton = new JButton(UIMessages.get("button.multiply", locale));
        divideButton = new JButton(UIMessages.get("button.divide", locale));

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);

        return buttonPanel;
    }

    public JTextField getFirstNumberField() {
        return firstNumberField;
    }

    public JTextField getSecondNumberField() {
        return secondNumberField;
    }

    public JTextField getResultField() {
        return resultField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getSubtractButton() {
        return subtractButton;
    }

    public JButton getMultiplyButton() {
        return multiplyButton;
    }

    public JButton getDivideButton() {
        return divideButton;
    }
}