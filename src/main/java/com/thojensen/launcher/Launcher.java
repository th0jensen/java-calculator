package com.thojensen.launcher;

import com.thojensen.calculator.Calculator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

public class Launcher {
    private Shell shell;
    private static Display display;

    public static void main(String[] args) {
        display = new Display();
        Launcher launcher = new Launcher();
        launcher.open();
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
        shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.TITLE | SWT.RESIZE);
        shell.setText("Launcher");
        shell.setLayout(new RowLayout());

        createButtons();

        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    public void createButtons() {
        Button button = new Button(shell, SWT.PUSH);
        button.setText("Calculator");
        button.addListener(SWT.Selection, _ -> launchCalculator());
    }

    private void launchCalculator() {
        Calculator calculator = new Calculator();
        calculator.run();
    }

}
