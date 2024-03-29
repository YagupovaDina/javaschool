package com.tsystems.javaschool.tasks.calculator;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        String res = null;
        try {
            ParseExpression parseExpression = new ParseExpression();
            String str = parseExpression.parseToPolishNotation(statement);
            res = EvaluationExpression.calculation(str);
        } catch (SyntaxException e) {
            System.err.println(e.toString());
        }
        return res;
    }

}
