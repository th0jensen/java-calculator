package com.thojensen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {

    /**
     * Method that prepares equation for evaluation.
     * @param input String
     * @return String[] of operands and operator
     */
    public static String[] splitEquation(String input) {
        List<String> parts = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)|([+\\-x/])");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                parts.add(matcher.group(1)); // operand
            } else if (matcher.group(2) != null) {
                parts.add(matcher.group(2)); // operator
            }
        }

        return parts.toArray(new String[0]);
    }
}
