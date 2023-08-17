
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    private ArrayList<horn> clauses;

    private ArrayList<String> facts;
    private ArrayList<String> symbolList = new ArrayList<>();
    private String query;
    private String expression;

    public Parser(Scanner filename) {
        clauses = new ArrayList<horn>();
        facts = new ArrayList<String>();
        read_line(filename);
    }

    public void read_line(Scanner file) {

        String Tell = file.nextLine();

        String nextLine = file.nextLine();

        System.out.println("Tell: " + nextLine);

        String ask = file.nextLine();

        String check = file.nextLine();
        System.out.println("Ask: " + check);
        query = check;

        nextLine = nextLine.replaceAll("\\s", "");

        expression = nextLine;

        String[] split = nextLine.split(";");

        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("=>")) {
                clauses.add(new horn(split[i]));
                String[] parts = split[i].trim().split("=>|&");
                for (String part : parts) {
                    String symbol = part.trim();
                    if (!symbolList.contains(symbol)) {
                        symbolList.add(symbol);
                    }
                }
            } else {
                facts.add(split[i]);

            }
        }

    }

    public String returnString() {
        return expression;
    }

    public ArrayList<horn> returnClauses() {
        return clauses;
    }

    public ArrayList<String> returnFacts() {
        return facts;
    }

    public ArrayList<String> returnSymbols() {
        return symbolList;
    }

    public String returnQuery() {
        return query;
    }
}
//