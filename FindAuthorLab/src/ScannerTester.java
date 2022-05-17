import java.io.*;

/**
 * The ScannerTester class tests the Scanner class by having it tokenize a mystery text.
 * @author Anu Datar
 * @author Nelson Gou
 * @version 4/18/22
 */
public class ScannerTester
{
    /**
     * Main method. Uses the Scanner and tokenizes a mystery text for testing.
     * @param args arguments from the command line
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        Reader reader = new BufferedReader(new FileReader("MysteryText/mystery1.txt"));
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextToken())
        {
            Token next = scanner.nextToken();
            System.out.println(next.getType() + ": " + next.getValue());
        }
    }
}
