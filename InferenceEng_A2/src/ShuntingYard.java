import java.util.*;

public class ShuntingYard {

    public static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Deque<String> operatorStack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            if (Character.isLetterOrDigit(token)) {
                if (Character.isLetter(token) && i + 1 < expression.length()
                        && Character.isDigit(expression.charAt(i + 1))) {
                    // Handle p1, p2, p14 as single tokens
                    int j = i + 1;
                    while (j < expression.length() && Character.isDigit(expression.charAt(j))) {
                        j++;
                    }
                    postfix.append(expression.substring(i, j)).append(" ");
                    i = j - 1; // Skip the characters already processed
                } else {
                    postfix.append(token).append(" ");
                }
            } else if (token == '(') {

                operatorStack.push(Character.toString(token));
            } else if (token == ')') {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {

                    postfix.append(operatorStack.pop()).append(" ");

                }
                if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                    operatorStack.pop(); // Discard the opening parenthesis
                }
            } else {
                StringBuilder operator = new StringBuilder(Character.toString(token));

                if (token == '=' && i + 1 < expression.length() && expression.charAt(i + 1) == '>') {
                    operator = new StringBuilder("=>");
                    i++;
                } else if (token == '<' && i + 2 < expression.length() && expression.charAt(i + 1) == '='
                        && expression.charAt(i + 2) == '>') {
                    operator = new StringBuilder("<=>");
                    i += 2;
                } else if (token == '|' && i + 1 < expression.length() && expression.charAt(i + 1) == '|') {
                    operator = new StringBuilder("||");
                    i++;
                }

                while (!operatorStack.isEmpty()
                        && getPrecedence(operator.toString()) <= getPrecedence(operatorStack.peek())) {

                    if (!operatorStack.peek().equals("(") && !operatorStack.peek().equals(")")) {

                        postfix.append(operatorStack.pop()).append(" ");
                    } else {
                        operatorStack.pop(); // Discard the parentheses

                    }
                }

                operatorStack.push(operator.toString());

            }
        }

        while (!operatorStack.isEmpty())

        {
            if (!operatorStack.peek().equals("(") && !operatorStack.peek().equals(")")) {

                postfix.append(operatorStack.pop()).append(" ");
            } else {
                operatorStack.pop(); // Discard the parentheses
            }
        }
        expression = postfix.toString().trim().replace(";", "");

        return expression;
    }

    private static int getPrecedence(String operator) {
        switch (operator) {
            case "~":
                return 3;
            case "&":
                return 2;
            case "||":
                return 2;
            case "=>":
                return 1;
            case "<=>":
                return 1; // Set the same precedence for both directions of the bidirectional operator
            default:
                return -1;
        }
    }

}