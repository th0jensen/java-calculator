package com.thojensen;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import static com.thojensen.Evaluator.splitEquation;
import static com.thojensen.Operator.conditionalRound;

public class Calculator {
    private Shell shell;
    private Label display;
    private Label errorBox;

    /**
     * The main entrypoint of the application
     * @param args Program initialisation arguments
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.open();
    }

    /**
     * Method to open and render the application.
     * Takes no arguments and returns a GUI window.
     * May fail if no window is allowed to open on the client.
     */
    public void open() {
        Display display = new Display();
        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        shell.setText("Calculator");
        shell.setSize(300, 400);

        renderContent();
        addKeyboardListener();

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void addKeyboardListener() {
        shell.addListener(SWT.KeyDown, event -> {
            char character = event.character;
            if (Character.isDigit(character) || "+-x/.".indexOf(character) != -1) {
                addToDisplay(String.valueOf(character));
            } else if (character == SWT.CR || character == SWT.LF) {
                calculateSum(this.display.getText());
            } else if (character == SWT.BS) {
                removeFromDisplay();
            }
        });
    }

    private void renderContent() {
        shell.setLayout(new GridLayout(4, false));
        display = createDisplay();
        errorBox = createErrorBox();

        String[] buttonLabels = {
                "C"     , "CE"    ,
                "7", "8", "9", "/",
                "4", "5", "6", "x",
                "1", "2", "3", "-",
                ".", "0", "=", "+",
        };

        for (String label : buttonLabels) {
            Button button = new Button(shell, SWT.PUSH);
            button.setText(label);

            button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

            GridData topButtons = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);

            switch (label) {
                case "=":
                    button.addListener(SWT.Selection, _ -> calculateSum(display.getText()));
                    break;
                case "C":
                    button.addListener(SWT.Selection, _ -> removeFromDisplay());
                    button.setLayoutData(topButtons);
                    break;
                case "CE":
                    button.addListener(SWT.Selection, _ -> display.setText(""));
                    button.setLayoutData(topButtons);
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
        label.setFont(font);

        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));

        return label;
    }

    private void addToDisplay(String label) {
        errorBox.setText("");

        if (Operator.isOperator(label)) {
            if (!display.getText().isEmpty() && !Operator.containsOperator(display.getText())) {
                display.setText(display.getText() + label);
            }
        } else {
            display.setText(display.getText() + label);
        }
    }

    private void removeFromDisplay() {
        errorBox.setText("");

        String currentText = this.display.getText();
        if (!currentText.isEmpty()) {
            this.display.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void calculateSum(String input) {
        String[] parts = splitEquation(input);
        if (parts.length != 3) {
            errorBox.setText("Error: Invalid input");
            return;
        }

        String leftOperand = parts[0].trim();
        String operatorSymbol = parts[1].trim();
        String rightOperand = parts[2].trim();

        try {
            Operator operator = Operator.fromSymbol(operatorSymbol);
            display.setText(conditionalRound(operator.apply(leftOperand, rightOperand)).toString());
        } catch (IllegalArgumentException e) {
            errorBox.setText("Error: Invalid operator");
        }
    }
}