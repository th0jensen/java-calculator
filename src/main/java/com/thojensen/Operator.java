package com.thojensen;

public enum Operator {
    ADDITION("+") {
        @Override
        public String apply(String num1, String num2) {
            return String.valueOf(Integer.parseInt(num1) + Integer.parseInt(num2));
        }
    },
    SUBTRACTION("-") {
        @Override
        public String apply(String num1, String num2) {
            return String.valueOf(Integer.parseInt(num1) - Integer.parseInt(num2));
        }
    },
    MULTIPLICATION("*") {
        @Override
        public String apply(String num1, String num2) {
            return String.valueOf(Integer.parseInt(num1) * Integer.parseInt(num2));
        }
    },
    DIVISION("/") {
        @Override
        public String apply(String num1, String num2) {
            if (Integer.parseInt(num2) == 0) {
                return "Error: Division by zero";
            }
            return String.valueOf(Integer.parseInt(num1) / Integer.parseInt(num2));
        }
    };

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract String apply(String num1, String num2);

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
}