package com.thojensen;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Operator {
    ADDITION("+") {
        @Override
        public Double apply(String num1, String num2) {
            return Double.parseDouble(num1) + Double.parseDouble(num2);
        }
    },
    SUBTRACTION("-") {
        @Override
        public Double apply(String num1, String num2) {
            return Double.parseDouble(num1) - Double.parseDouble(num2);
        }
    },
    MULTIPLICATION("x") {
        @Override
        public Double apply(String num1, String num2) {
            return Double.parseDouble(num1) * Double.parseDouble(num2);
        }
    },
    DIVISION("/") {
        @Override
        public Double apply(String num1, String num2) {
            if (Double.parseDouble(num2) == 0 || Double.parseDouble(num1) == 0) {
                return 0.0;
            }
            return Double.parseDouble(num1) / Double.parseDouble(num2);
        }
    };

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract Double apply(String num1, String num2);

    public static Operator fromSymbol(String symbol) {
        for (Operator op : values()) {
            if (op.getSymbol().equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid operator symbol: " + symbol);
    }

    public static boolean isOperator(String label) {
        for (Operator op : Operator.values()) {
            if (op.getSymbol().equals(label)) { return true; }
        }
        return false;
    }

    public static boolean containsOperator(String text) {
        for (Operator op : Operator.values()) {
            if (text.contains(op.getSymbol())) { return true; }
        }
        return false;
    }

    public static BigDecimal conditionalRound(double number) {
        BigDecimal bd = new BigDecimal(Double.toString(number));

        if (bd.stripTrailingZeros().scale() <= 0) {
            return bd.setScale(0, RoundingMode.DOWN);
        }

        return bd.setScale(3, RoundingMode.DOWN).stripTrailingZeros();
    }
}