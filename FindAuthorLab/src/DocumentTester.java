import java.io.*;

/**
 * The DocumentTester class test the Document class.
 * @author Nelson Gou
 * @version 4/22/22
 */
public class DocumentTester
{
    /**
     * Main method.
     * Tests the Document by parsing a text and printing its contents.
     * @param args arguments from the command line
     * @throws IOException thrown if the file is not found
     */
    public static void main(String[] args) throws IOException
    {
        Reader reader = new BufferedReader(new FileReader("MysteryText/mystery1.txt"));
        Scanner scanner = new Scanner(reader);

        Document document = new Document(scanner);
        document.parseDocument();

        System.out.println(document);
    }
}
