package com.thojensen.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator implements Runnable {
    private static Display display;
    private static Shell parentShell;
    private Shell shell;
    private Label calcDisplay;
    private Label errorBox;

    public Calculator(Shell parentShell) {
        Calculator.parentShell = parentShell;
    }

    @Override
    public void run() {
        open();
    }

    /**
     * The main entrypoint of the application
     * @param args Program initialisation arguments
     */
    public static void main(String[] args) {
        display = new Display();
        Calculator calculator = new Calculator(parentShell);
        calculator.run();
        while (!display.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    /**
     * Method to open and render the application.
     * Takes no arguments and returns a GUI window.
     * May fail if no window is allowed to open on the client.
     */
    public void open() {
        shell = new Shell(parentShell, SWT.CLOSE | SWT.MODELESS);
        shell.setText("Calculator");
        shell.setSize(300, 400);

        renderContent();
        addKeyboardListener();

        shell.open();
        while (!shell.isDisposed()) {
            if (!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
    }

    private void addKeyboardListener() {
        shell.addListener(SWT.KeyDown, event -> {
            char character = event.character;
            if (Character.isDigit(character) || "+-x*/.".indexOf(character) != -1) {
                if (character == '*') {
                    addToDisplay(String.valueOf('x'));
                } else {
                    addToDisplay(String.valueOf(character));
                }
            } else if (character == SWT.CR || character == SWT.LF) {
                calculateSum(this.calcDisplay.getText());
            } else if (character == SWT.BS) {
                removeFromDisplay();
            }
        });
    }

    private void renderContent() {
        shell.setLayout(new GridLayout(4, false));
        calcDisplay = createDisplay();
        errorBox = createErrorBox();

        ArrayList<String> buttonLabels = new ArrayList<>(Arrays.asList(
                "C"     , "CE"    ,
                "7", "8", "9", "/",
                "4", "5", "6", "x",
                "1", "2", "3", "-",
                ".", "0", "=", "+"
        ));

        buttonLabels.forEach(this::createButtons);
    }

    private void createButtons(String label) {
        Button button = new Button(shell, SWT.PUSH);
        button.setText(label);

        button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        GridData topButtons = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);

        switch (label) {
            case "=":
                button.addListener(SWT.Selection, _ -> calculateSum(calcDisplay.getText()));
                break;
            case "C":
                button.addListener(SWT.Selection, _ -> removeFromDisplay());
                button.setLayoutData(topButtons);
                break;
            case "CE":
                button.addListener(SWT.Selection, _ -> calcDisplay.setText(""));
                button.setLayoutData(topButtons);
                break;
            default:
                button.addListener(SWT.Selection, _ -> addToDisplay(label));
                break;
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
        label.setFont(font);

        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));

        return label;
    }

    private void addToDisplay(String label) {
        errorBox.setText("");

        if (Operator.isOperator(label)) {
            if (!calcDisplay.getText().isEmpty() && !Operator.containsOperator(calcDisplay.getText())) {
                calcDisplay.setText(calcDisplay.getText() + label);
            }
        } else {
            calcDisplay.setText(calcDisplay.getText() + label);
        }
    }

    private void removeFromDisplay() {
        errorBox.setText("");

        String currentText = this.calcDisplay.getText();
        if (!currentText.isEmpty()) {
            this.calcDisplay.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void calculateSum(String input) {
        String[] parts = Evaluator.splitEquation(input);
        if (parts.length != 3) {
            errorBox.setText("Error: Invalid input");
            return;
        }

        String leftOperand = parts[0].trim();
        String operatorSymbol = parts[1].trim();
        String rightOperand = parts[2].trim();

        try {
            Operator operator = Operator.fromSymbol(operatorSymbol);
            calcDisplay.setText(Operator.conditionalRound(operator.apply(leftOperand, rightOperand)).toString());
        } catch (IllegalArgumentException e) {
            errorBox.setText("Error: Invalid operator");
        }
    }
}