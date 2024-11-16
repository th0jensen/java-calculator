package com.thojensen.launcher;

import com.thojensen.calculator.Calculator;
import com.thojensen.snake.SnakeGame;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.io.InputStream;

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


    public void open() {
        // Create the display and shell
        shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.TITLE | SWT.RESIZE);
        shell.setText("Launcher");
        shell.setLayout(new GridLayout(1, false));

        createImageLabel();
        createButtons();

        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public void createImageLabel() {
        InputStream imageStream = getClass().getResourceAsStream("/com/thojensen/launcher/banner.jpg");

        // Load the original image
        Image originalImage = new Image(display, imageStream);

        // Get the dimensions of the label
        int labelWidth = shell.getSize().x;  // Set your desired width
        int labelHeight = shell.getSize().y;  // Set your desired height

        // Calculate new dimensions while maintaining aspect ratio
        int originalWidth = originalImage.getBounds().width;
        int originalHeight = originalImage.getBounds().height;

        double aspectRatio = (double) originalWidth / (double) originalHeight;

        int newWidth, newHeight;

        if (aspectRatio > 1) { // Image is wider than it is tall
            newWidth = labelWidth;
            newHeight = (int) (labelWidth / aspectRatio);
        } else { // Image is taller than it is wide or square
            newHeight = labelHeight;
            newWidth = (int) (labelHeight * aspectRatio);
        }

        // Create a scaled version of the image
        Image scaledImage = new Image(display, newWidth, newHeight);

        GC gc = new GC(scaledImage);
        gc.drawImage(originalImage, 0, 0, originalWidth, originalHeight, 0, 0, newWidth, newHeight);
        gc.dispose();

        Label imageLabel = new Label(shell, SWT.NONE);
        imageLabel.setImage(scaledImage);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        gridData.minimumHeight = labelHeight;
        gridData.heightHint = labelHeight;
        imageLabel.setLayoutData(gridData);

        // Dispose of the original image to free resources
        originalImage.dispose();
    }

    public void createButtons() {
        Composite buttonComposite = new Composite(shell, SWT.NONE);
        buttonComposite.setLayout(new GridLayout(2, true));

        Button calculatorButton = new Button(buttonComposite, SWT.PUSH);
        calculatorButton.setText("Calculator");
        calculatorButton.addListener(SWT.Selection, _ -> launchCalculator());

        Button snakeButton = new Button(buttonComposite, SWT.PUSH);
        snakeButton.setText("Snake");
        snakeButton.addListener(SWT.Selection, _ -> launchSnake());

        GridData buttonGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);

        calculatorButton.setLayoutData(buttonGridData);
        snakeButton.setLayoutData(buttonGridData);
    }

    private void launchCalculator() {
        Calculator calculator = new Calculator(shell);
        calculator.run();
    }

    private void launchSnake() {
        SnakeGame snake = new SnakeGame(shell);
        snake.run();
    }
}