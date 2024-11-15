package com.thojensen.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    void testSplitEquation() {
        String[] result = Evaluator.splitEquation("12+34");
        assertArrayEquals(new String[]{"12", "+", "34"}, result);

        result = Evaluator.splitEquation("5.5-3.2");
        assertArrayEquals(new String[]{"5.5", "-", "3.2"}, result);

        result = Evaluator.splitEquation("10x2");
        assertArrayEquals(new String[]{"10", "x", "2"}, result);

        result = Evaluator.splitEquation("20/4");
        assertArrayEquals(new String[]{"20", "/", "4"}, result);
    }

    @Test
    void testSplitEquationWithSpaces() {
        String[] result = Evaluator.splitEquation("12 + 34");
        assertArrayEquals(new String[]{"12", "+", "34"}, result);
    }

    @Test
    void testSplitEquationInvalidInput() {
        String[] result = Evaluator.splitEquation("12++34");
        assertNotEquals(3, result.length);
    }
}