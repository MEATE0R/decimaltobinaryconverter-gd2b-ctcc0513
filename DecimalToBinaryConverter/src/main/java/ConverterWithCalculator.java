import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Stack;

public class ConverterWithCalculator extends JFrame {
    private JTextField binaryInput;
    private JLabel decimalOutput;
    private JButton convertButton, reverseButton;
    private JTextField num1Input, num2Input;
    private JComboBox<String> operatorSelector;
    private JButton calculateButton;
    private JLabel calculationResult;
    private JCheckBox binaryModeCheckbox;
    private boolean isBinaryToDecimal = true;

    public ConverterWithCalculator() {
        setTitle("Converter and Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initializeComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Converter panel
        JPanel converterPanel = createConverterPanel();
        mainPanel.add(converterPanel, BorderLayout.NORTH);

        // Calculator panel
        JPanel calculatorPanel = createCalculatorPanel();
        mainPanel.add(calculatorPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createConverterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Binary-Decimal Converter"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel binaryLabel = new JLabel("Binary:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(binaryLabel, gbc);

        binaryInput = new JTextField(10);
        gbc.gridx = 1;
        panel.add(binaryInput, gbc);

        JLabel decimalLabel = new JLabel("Decimal:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(decimalLabel, gbc);

        decimalOutput = new JLabel("-");
        gbc.gridx = 1;
        panel.add(decimalOutput, gbc);

        convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> convert());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(convertButton, gbc);

        reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(e -> reverseConversion());
        gbc.gridy = 3;
        panel.add(reverseButton, gbc);

        return panel;
    }

    private JPanel createCalculatorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Decimal/Binary Calculator"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        num1Input = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(num1Input, gbc);

        operatorSelector = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        gbc.gridx = 1;
        panel.add(operatorSelector, gbc);

        num2Input = new JTextField(5);
        gbc.gridx = 2;
        panel.add(num2Input, gbc);

        binaryModeCheckbox = new JCheckBox("Binary Mode");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(binaryModeCheckbox, gbc);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> calculate());
        gbc.gridy = 2;
        panel.add(calculateButton, gbc);

        calculationResult = new JLabel("Result: -");
        gbc.gridy = 3;
        panel.add(calculationResult, gbc);

        return panel;
    }

    private void convert() {
        try {
            if (isBinaryToDecimal) {
                String binary = binaryInput.getText();
                decimalOutput.setText(String.valueOf(binaryToDecimal(binary)));
            } else {
                int decimal = Integer.parseInt(binaryInput.getText());
                decimalOutput.setText(decimalToBinary(decimal));
            }
        } catch (NumberFormatException e) {
            decimalOutput.setText("Invalid input");
        }
    }

    private void reverseConversion() {
        isBinaryToDecimal = !isBinaryToDecimal;
        String temp = binaryInput.getText();
        binaryInput.setText(decimalOutput.getText());
        decimalOutput.setText(temp);
    }

    private void calculate() {
        try {
            boolean isBinaryMode = binaryModeCheckbox.isSelected();

            int num1 = isBinaryMode? Integer.parseInt(num1Input.getText(), 2) : Integer.parseInt(num1Input.getText());
            int num2 = isBinaryMode? Integer.parseInt(num2Input.getText(), 2) : Integer.parseInt(num2Input.getText());
            String operator = (String) operatorSelector.getSelectedItem();
            int result = 0;

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        calculationResult.setText("Error: Division by zero");
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            String resultText = isBinaryMode ? Integer.toBinaryString(result) : String.valueOf(result);
            calculationResult.setText("Result: " + resultText);
        } catch (NumberFormatException e) {
            calculationResult.setText("Invalid input");
        }
    }

    private int binaryToDecimal(String binary) {
        Stack<Integer> stack = new Stack<>();
        for (char c : binary.toCharArray()) {
            stack.push(c - '0');
        }

        int decimal = 0;
        int base = 1;

        while (!stack.isEmpty()) {
            decimal += stack.pop() * base;
            base *= 2;
        }

        return decimal;
    }

    private String decimalToBinary(int decimal) {
        Stack<Integer> stack = new Stack<>();
        while (decimal > 0) {
            stack.push(decimal % 2);
            decimal /= 2;
        }

        StringBuilder binary = new StringBuilder();
        while (!stack.isEmpty()) {
            binary.append(stack.pop());
        }

        return binary.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConverterWithCalculator().setVisible(true));
    }
}
