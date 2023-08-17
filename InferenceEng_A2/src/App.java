import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java App <file-path> <search-method>");
            return;
        }
        String searchMethod = args[0];
        String filePath = args[1];
        System.out.println(filePath);
        File file = new File(filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return;
        }

        Parser parser = new Parser(scanner);

        switch (searchMethod) {
            case "TT":
                ShuntingYard yard = new ShuntingYard();
                String postfix = yard.infixToPostfix(parser.returnString());
                TruthTableChecking truth = new TruthTableChecking(postfix);
                truth.evaluate(parser.returnQuery());
                break;
            case "FC":

                forwardchaining f = new forwardchaining(parser);
                System.out.println(f.test());
                break;
            case "BC":
                backwardchaining b = new backwardchaining(parser);
                System.out.println(b.test());
                break;
        }
    }
}
