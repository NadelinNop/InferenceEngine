
import java.util.ArrayList;
import java.util.Collections;

public class backwardchaining {
    private ArrayList<horn> clauses;
    private ArrayList<String> facts;
    private String query;
    private ArrayList<String> factsReturned;
    private boolean[] visited;

    public backwardchaining(Parser file) {
        clauses = file.returnClauses();
        facts = file.returnFacts();
        query = file.returnQuery();

        factsReturned = new ArrayList<String>();
        visited = new boolean[clauses.size()];
    }

    public boolean prove(String q) {
        // Check if the query is already in the list of facts
        if (facts.contains(q)) {
            if (!factsReturned.contains(q)) {
                factsReturned.add(q);
            }
            return true;
        } else if (factsReturned.contains(q)) {
            return true;
        }

        // Loop through all the clauses
        for (int j = 0; j < clauses.size(); j++) {

            if (!visited[j] && clauses.get(j).getListArrow().equals(q)) {
                visited[j] = true;
                if (!factsReturned.contains(q)) {
                    factsReturned.add(q);
                }
                boolean isProven = true; // Flag to keep track of the result

                // Checking for the first operand
                for (int i = 0; i < clauses.get(j).firstListCount(); i++) {

                    String factToCheck = clauses.get(j).returnfirstListIndex(i);
                    if (!prove(factToCheck)) {
                        isProven = false; // Update the flag if any fact is not proven
                    }
                }

                if (isProven) {

                    return true; // If all facts in the first operand are proven, return true
                }
            }
        }

        return false;
    }

    public boolean checkFacts() {
        return prove(query);
    }

    public String test() {
        String display = "";

        if (checkFacts()) {
            Collections.reverse(factsReturned);
            display += "YES: ";
            for (int i = 0; i < factsReturned.size(); i++) {
                display += factsReturned.get(i);
                if (i < factsReturned.size() - 1) {
                    display += ",";
                }
            }
        } else {
            display += "NO: Query cannot be proven";
        }
        return display;
    }
}
