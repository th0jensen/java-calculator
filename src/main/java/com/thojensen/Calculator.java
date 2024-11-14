package com.thojensen;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Calculator {
    private Shell shell;
    private Label display;
    private Label errorBox;

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.open();
    }

    public void open() {
        Display display = new Display();
        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        shell.setText("Calculator");
        shell.setSize(300, 400);

        renderContent();

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void renderContent() {
        shell.setLayout(new GridLayout(4, false));
        display = createDisplay();
        errorBox = createErrorBox();

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "CE", "0", "=", "+"
        };

        for (String label : buttonLabels) {
            Button button = new Button(shell, SWT.PUSH);
            button.setText(label);

            button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

            switch (label) {
                case "=":
                    button.addListener(SWT.Selection, _ -> calculateSum(display.getText()));
                    break;
                case "CE":
                    button.addListener(SWT.Selection, _ -> display.setText(""));
                    break;
                default:
                    button.addListener(SWT.Selection, _ -> addToDisplay(label));
                    break;
            }
        }
    }

    private Label createDisplay() {
        Label label = new Label(shell, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);

        Font font = new Font(shell.getDisplay(), "system", 24, SWT.NORMAL);
        label.setFont(font);

        GridData calculatorDisplayGrid = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
        calculatorDisplayGrid.verticalIndent = 25;
        label.setLayoutData(calculatorDisplayGrid);

        return label;
    }

    private Label createErrorBox() {
        Label label = new Label(shell, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);

        Font font = new Font(shell.getDisplay(), "system", 12, SWT.NORMAL);
        errorBox.setFont(font);

        errorBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));

        return label;
    }

    private void addToDisplay(String label) {
        errorBox.setText("");

        if (isOperator(label)) {
            if (!display.getText().isEmpty() && !containsOperator(display.getText())) {
                display.setText(display.getText() + label);
            }
        } else {
            display.setText(display.getText() + label);
        }
    }

    private boolean isOperator(String label) {
        for (Operator op : Operator.values()) {
            if (op.getSymbol().equals(label)) { return true; }
        }
        return false;
    }

    private boolean containsOperator(String text) {
        for (Operator op : Operator.values()) {
            if (text.contains(op.getSymbol())) { return true; }
        }
        return false;
    }

    private void calculateSum(String input) {
        String[] parts = input.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");

        if (parts.length != 3) {
            errorBox.setText("Error: Invalid input");
            return;
        }

        String num1 = parts[0].trim();
        String operatorSymbol = parts[1].trim();
        String num2 = parts[2].trim();

        try {
            Operator operator = Operator.fromSymbol(operatorSymbol);
            display.setText(operator.apply(num1, num2));
        } catch (IllegalArgumentException e) {
            errorBox.setText("Error: Invalid operator");
        }
    }
}