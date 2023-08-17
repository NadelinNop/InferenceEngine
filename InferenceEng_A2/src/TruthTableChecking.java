import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TruthTableChecking {
    private String postfixExpression;
    private ArrayList<String> variables;
    private int columnSize;
    private int rowSize;
    private boolean[][] table;
    private boolean queryResult;

    public TruthTableChecking(String postfixExpression) {
        this.postfixExpression = postfixExpression;

        String[] tokens = postfixExpression.split("\\s+");
        variables = new ArrayList<>();

        // Extract variable names from tokens
        for (String token : tokens) {
            if (!isOperator(token) && !variables.contains(token)) {
                variables.add(token);
            }
        }
        // Remove variables that are just spaces
        variables.removeIf(variable -> variable.trim().isEmpty());

        columnSize = variables.size();

        rowSize = (int) Math.pow(2, columnSize);

        table = new boolean[rowSize][columnSize];
    }

    public void evaluate(String query) {
        int trueCount = 0;

        for (int i = 0; i < rowSize; i++) {
            HashMap<String, Boolean> model = new HashMap<>();

            // Assign variable values for the current row
            for (int j = 0; j < columnSize; j++) {
                String variable = variables.get(j);
                boolean value = ((i >> (columnSize - 1 - j)) & 1) != 1;
                model.put(variable, value);
            }

            // Evaluate the postfix expression using the model
            boolean result = evaluateExpression(postfixExpression, model);

            if (!postfixExpression.contains(query)) {
                System.out.println("NO");
                return;
            } else {
                queryResult = evaluateExpression(query, model);
            }

            // Evaluate the query using the model

            // Store the result in the truth table
            for (int j = 0; j < columnSize; j++) {
                table[i][j] = result;
            }

            if (queryResult && result) {
                trueCount++;

            }

        }
        if (trueCount > 0) {
            System.out.println("YES: " + trueCount);
        } else {
            System.out.println("NO");
        }

    }

    public boolean evaluateExpression(String postfixExpression, HashMap<String, Boolean> model) {
        Stack<Boolean> stack = new Stack<>();
        String[] tokens = postfixExpression.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue; // Skip empty tokens
            }
            if (!isOperator(token)) {
                stack.push(model.get(token));
            } else {
                boolean result;
                switch (token) {
                    case "~":
                        boolean operand = stack.pop();
                        result = !operand;
                        break;
                    case "&":
                        boolean operand2 = stack.pop();
                        boolean operand1 = stack.pop();
                        result = operand1 && operand2;
                        break;
                    case "=>":
                        boolean b = stack.pop();
                        boolean a = stack.pop();
                        result = !a || b;
                        break;
                    case "<=>":
                        boolean d = stack.pop();
                        boolean c = stack.pop();
                        result = (c && d) || (!c && !d);
                        break;
                    case "||":
                        boolean f = stack.pop();
                        boolean e = stack.pop();
                        result = e || f;
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
                stack.push(result);
            }
        }

        while (!stack.isEmpty()) {
            if (!stack.pop()) {
                return false;
            }
        }

        return true;
    }

    private boolean isOperator(String token) {
        return token.equals("~") || token.equals("&") || token.equals("=>") || token.equals("<=>")
                || token.equals("||");
    }
}
