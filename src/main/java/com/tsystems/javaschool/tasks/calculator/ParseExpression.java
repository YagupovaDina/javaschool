package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class ParseExpression {
    /**
     * @param s - входной символ
     * @return у * и / приоритет выше, чем у + и -
     */
    private int priorityOperator(String s) {
        if (s.equals("+") || s.equals("-"))
            return 1;
        return 2;
    }

    /**
     * @param s - входной символ из строки
     * @return если символ операнд - return true
     */
    private boolean isOperand(String s) {
        String number = "\\d+";//"[+-]?([0-9]*[.])?[0-9]+";
        boolean t = (s.matches(number));
        //if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("(") || s.equals(")") || s.equals(" "))
        //    return false;
        return t;//true;
        //else return true;
    }

    /**
     * @param s - входной символ из строки
     * @return если символ оператор - true, иначе false
     */
    private boolean isOperator(String s) {

        if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
            return true;
        else return false;
    }

    private boolean isAmnountOfBracketsEqual(String str) {
        int countLeft = str.length() - str.replace("(", "").length();
        int counRight = str.length() - str.replace(")", "").length();
        if (counRight == countLeft)
            return true;
        return false;
    }

    /**
     * @param str - входное выражение
     * @return выражение в обратной польской записи
     */
    public String parseToPolishNotation(String str) throws SyntaxException {
        if (str == null || str.equals(""))
            throw new SyntaxException("string is absent");
        if (!isAmnountOfBracketsEqual(str))
            throw new SyntaxException(" brackets are not equal");
        String outputString = "";
        int slength = str.length();
        Deque<String> stack = new ArrayDeque<>();
        for (int i = 0; slength > 0; i++, slength--) {
            if (isOperator(String.valueOf(str.charAt(i)))) {
                while (stack.size() > 0) {
                    if (stack.peek().equals("(")) {
                        break;
                    } else if (isOperator(stack.peek())) {
                        if (priorityOperator(stack.peek()) < priorityOperator(String.valueOf(str.charAt(i)))) {
                            break;
                        }
                    }
                    if (isOperand(stack.peek()) || isOperator(stack.peek()))
                        outputString = outputString.concat(stack.pop());
                    else
                        throw new SyntaxException("wrong string");
                }
                stack.push(String.valueOf(str.charAt(i)));
            } else if (String.valueOf(str.charAt(i)).equals("(") || String.valueOf(str.charAt(i)).equals(")")) {
                if (String.valueOf(str.charAt(i)).equals("(")) {
                    stack.push(String.valueOf(str.charAt(i)));
                } else {
                    while (stack.size() > 0) {
                        if (stack.peek().equals("(")) {
                            stack.pop();
                            break;
                        } else {
                            if (isOperand(stack.peek()) || isOperator(stack.peek()))
                                outputString = outputString.concat(stack.pop());
                            else throw new SyntaxException("wrong string");
                        }
                    }
                }
            } else {
                if (outputString.length() == 0) {
                    if (isOperand(Character.toString(str.charAt(i))) || isOperator(Character.toString(str.charAt(i))))
                        outputString = String.valueOf(str.charAt(i));
                    else if (String.valueOf(str.charAt(i)).equals(".")) {
                        if (i > 0 && isOperand(Character.toString(str.charAt(i - 1))) && isOperand(Character.toString(str.charAt(i + 1)))) {
                            outputString = outputString.concat(String.valueOf(str.charAt(i)));
                        }
                    } else {
                        throw new SyntaxException("wrong string");
                    }
                } else {
                    if (str.length() > 1 && !isOperand(String.valueOf(str.charAt(i - 1))) && !(String.valueOf(str.charAt(i - 1)).equals(".")))
                        outputString = outputString.concat(" ");
                    if (isOperand(Character.toString(str.charAt(i))) || isOperator(Character.toString(str.charAt(i))))
                        outputString = outputString.concat(String.valueOf(str.charAt(i)));
                    else if ((String.valueOf(str.charAt(i)).equals(".")) &&
                            i > 0 && isOperand(Character.toString(str.charAt(i - 1))) && isOperand(Character.toString(str.charAt(i + 1)))) {
                        outputString = outputString.concat(String.valueOf(str.charAt(i)));
                    } else throw new SyntaxException("wrong string");
                }
            }
        }
        if (stack.contains("("))
            throw new SyntaxException(" brackets are established wrong");
        while (stack.size() > 0) {
            if (isOperand(stack.peek()) || isOperator(stack.peek()))
                outputString = outputString.concat(stack.pop());
            else throw new SyntaxException("wrong string");
        }
        return outputString;
    }
}
