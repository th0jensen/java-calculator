package com.thojensen;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class OperatorTest {

    @Test
    void testAddition() {
        assertEquals(5.0, Operator.ADDITION.apply("2", "3"));
    }

    @Test
    void testSubtraction() {
        assertEquals(2.0, Operator.SUBTRACTION.apply("5", "3"));
    }

    @Test
    void testMultiplication() {
        assertEquals(15.0, Operator.MULTIPLICATION.apply("3", "5"));
    }

    @Test
    void testDivision() {
        assertEquals(2.0, Operator.DIVISION.apply("6", "3"));
    }

    @Test
    void testDivisionByZero() {
        assertEquals(0.0, Operator.DIVISION.apply("5", "0"));
    }

    @Test
    void testFromSymbol() {
        assertEquals(Operator.ADDITION, Operator.fromSymbol("+"));
        assertEquals(Operator.SUBTRACTION, Operator.fromSymbol("-"));
        assertEquals(Operator.MULTIPLICATION, Operator.fromSymbol("x"));
        assertEquals(Operator.DIVISION, Operator.fromSymbol("/"));
    }

    @Test
    void testFromSymbolInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Operator.fromSymbol("*"));
    }

    @Test
    void testIsOperator() {
        assertTrue(Operator.isOperator("+"));
        assertTrue(Operator.isOperator("-"));
        assertTrue(Operator.isOperator("x"));
        assertTrue(Operator.isOperator("/"));
        assertFalse(Operator.isOperator("5"));
    }

    @Test
    void testContainsOperator() {
        assertTrue(Operator.containsOperator("5+3"));
        assertTrue(Operator.containsOperator("10-2"));
        assertTrue(Operator.containsOperator("4x3"));
        assertTrue(Operator.containsOperator("15/3"));
        assertFalse(Operator.containsOperator("123"));
    }

    @Test
    void testConditionalRound() {
        assertEquals(new BigDecimal("5"), Operator.conditionalRound(5.0));
        assertEquals(new BigDecimal("3.141"), Operator.conditionalRound(3.14159));
        assertEquals(new BigDecimal("10.123"), Operator.conditionalRound(10.1234567));
    }
}