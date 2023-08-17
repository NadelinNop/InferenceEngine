import java.util.ArrayList;

public class forwardchaining {
    private ArrayList<horn> clauses;
    private ArrayList<String> facts;
    private String query;
    private ArrayList<String> factsReturned;
    ArrayList<horn> clausesCopy = new ArrayList<horn>();
    ArrayList<String> factsCopy = new ArrayList<String>();

    public forwardchaining(Parser file) {
        clauses = file.returnClauses();
        for (horn c : clauses) {
            // System.out.println(c.toString());
            clausesCopy.add(new horn(c.toString()));
        }
        facts = file.returnFacts();
        for (String f : facts) {
            factsCopy.add(f);
        }

        query = file.returnQuery();

        factsReturned = new ArrayList<String>();
    }

    public boolean checkFacts() {
        // Make a copy of the original clauses list

        // all the elements in the facts list is searched through
        while (!factsCopy.isEmpty()) {
            // an element from the facts list will be removed
            String factTocheck = factsCopy.remove(0);

            // removed element will be added to a new list which will store all the facts
            factsReturned.add(factTocheck);

            // checks whether the facts and query matches or not
            if (factTocheck.equals(query)) {

                return true;
            }

            // looping through all the clauses
            for (int i = 0; i < clausesCopy.size(); i++) {
                // loops through individual element in a clause
                for (int j = 0; j < clausesCopy.get(i).firstListCount(); j++) {
                    // the fact being checked will be compared against an element from the clause
                    if (factTocheck.equals(clausesCopy.get(i).returnfirstListIndex(j))) {
                        // if there is a match, that element will be removed from clause
                        clausesCopy.get(i).updatefirstList(factTocheck);
                    }
                }
            }

            // looping through all the clauses
            for (int i = 0; i < clausesCopy.size(); i++) {
                // checks whether the length of a specific clause is 0 or not
                if (clausesCopy.get(i).firstListCount() == 0) {
                    // if it is zero, the entailment will be a fact and it will be added to the
                    // facts list
                    factsCopy.add(clausesCopy.get(i).getListArrow());
                    clausesCopy.remove(i);
                }
            }
        }

        return false;
    }

    public String test() {
        String display = "";
        ArrayList<String> facts = new ArrayList<>();
        if (checkFacts()) {
            for (String s : factsReturned) {
                if (!facts.contains(s)) {
                    facts.add(s);
                }
            }
            display += "YES: ";
            for (int i = 0; i < facts.size(); i++) {
                display += facts.get(i);
                if (i < facts.size() - 1) {
                    display += ",";
                }
            }
        } else {
            display += "NO: Query cannot be proven";
        }
        return display;
    }
}