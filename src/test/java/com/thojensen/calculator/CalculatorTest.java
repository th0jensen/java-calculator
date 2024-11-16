package com.thojensen.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.MIN);
        calculator = new Calculator(shell);
    }

    @Test
    void testCalculatorCreation() {
        assertNotNull(calculator);
    }
}