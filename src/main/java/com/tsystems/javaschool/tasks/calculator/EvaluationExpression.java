package com.tsystems.javaschool.tasks.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluationExpression {
    private EvaluationExpression() {
    }

    private static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
    private static DecimalFormat df;
    private static String number;
    private static Pattern patternNumber;
    private static String operations;
    private static Pattern patternExpression;
    private static Matcher matcherForExpression;
    private static Matcher matcherForExtractNumber;

    /**
     * инициализируются строки для регулярных выражений
     * структура, в которой происхоид  вычисления выражения, представлена в виде stack (Deque представим в виде стека)
     */
    static {
        otherSymbols.setDecimalSeparator('.');
        df = new DecimalFormat("#.####", otherSymbols);
        number = "[+-]?([0-9]*[.])?[0-9]+";
        patternNumber = Pattern.compile((number));
        operations = "[\\@ | \\+ | \\* | \\- | \\/]";
        patternExpression = Pattern.compile((number + "|" + operations));
    }

    private static double calcExpr(double a, double b, String operation) throws SyntaxException {
        if (operation.equals("+"))
            return a + b;
        if (operation.equals("-"))
            return a - b;
        if (operation.equals("*"))
            return a * b;
        if (operation.equals("/")) {
            if (b == 0)
                throw new SyntaxException("divided by zero");
            return a / b;
        }
        return 0;
    }

    private static boolean isOperator(String s) {

        if (s.equals("@") || s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
            return true;
        else return false;
    }

    /**
     * @param expression - числа из арифметического выражения expression записываются в stack,
     *                   далее над числами производится арифметическая операция.
     */
    public static String calculation(String expression) throws IllegalArgumentException, SyntaxException {
        Deque<String> numbers = new ArrayDeque<>();
        matcherForExpression = patternExpression.matcher(expression);
        while (matcherForExpression.find()) {
            if (!matcherForExpression.group().equals(" ")) {
                matcherForExtractNumber = patternNumber.matcher(matcherForExpression.group());
                try {
                    if (matcherForExtractNumber.matches()) {
                        numbers.push(matcherForExpression.group());
                    } else {
                        double a = Double.parseDouble(numbers.pop());
                        if (numbers.isEmpty())
                            throw new SyntaxException("wrong expression,check operands");
                        double b = Double.parseDouble(numbers.pop());
                        if (isOperator(((Double) b).toString()))
                            throw new SyntaxException("wrong expression,check operands");
                        Double result = calcExpr(b, a, matcherForExpression.group());
                        numbers.push(result.toString());
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (expression.contains(".")) {
            double tmp = Double.parseDouble(numbers.getLast());
            return df.format(new BigDecimal(tmp));
        } else {
            return numbers.getLast().substring(0, numbers.getLast().indexOf("."));
        }
    }
}
