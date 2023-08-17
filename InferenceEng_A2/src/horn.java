import java.util.LinkedList;

import static java.lang.System.in;

public class horn {
    // represents front element of the clause
    private LinkedList<String> firstList;
    // represents end element of the clause
    private String arrowList;
    private String clauseCopy;

    public horn(String clause) {
        firstList = new LinkedList<>();
        clauseCopy = clause;
        // clause split into two parts
        String[] splitArrow = clause.split("=>");
        if (splitArrow.length == 2) {
            String antecedent = splitArrow[0];
            String consequent = splitArrow[1];

            String[] splitAnd = antecedent.split("&");

            for (String fact : splitAnd) {
                String[] splitFact = fact.trim().split("&");
                for (String subFact : splitFact) {
                    firstList.add(subFact.trim());
                }
            }

        }

        arrowList = splitArrow[1].trim();

    }

    public LinkedList<String> getfirstList() {
        return firstList;
    }

    public String getListArrow() {
        return arrowList;
    }

    public void updatefirstList(String c) {
        firstList.remove(c);
    }

    public int firstListCount() {
        return firstList.size();
    }

    // returns literal at specified index
    public String returnfirstListIndex(int index) {
        try {
            if (index >= 0 && index < firstList.size()) {
                return firstList.get(index);
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return clauseCopy;
    }

}
