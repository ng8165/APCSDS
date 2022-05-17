import java.util.*;

/**
 * The Document class represents a parsed Document in the form of a List of Sentences.
 * @author Nelson Gou
 * @version 4/22/22
 */
public class Document
{
    private Scanner scanner;
    private List<Sentence> sentences;
    private Token curr;

    /**
     * Constructs a Document by instantiating a new List of Sentences
     * and starting the token scanning.
     * @param scanner the Scanner used to scan the Tokens
     */
    public Document(Scanner scanner)
    {
        this.scanner = scanner;
        sentences = new ArrayList<>();
        curr = scanner.nextToken();
    }

    /**
     * Replaces the old current Token with a new one if the given Token is equal to the current.
     * @param t the given Token to test
     * @throws RuntimeException if the given Token is not equal to the current Token
     */
    public void eat(Token t)
    {
        if (curr.equals(t))
            curr = scanner.nextToken();
        else
            throw new RuntimeException(curr + " is not equal to " + t);
    }

    /**
     * Parses a Phrase by requesting new Tokens from the Scanner and adding them to the Phrase
     * until the end of the phrase, file, or sentence is reached.
     * @return a Phrase constructed from scanned Tokens
     */
    public Phrase parsePhrase()
    {
        Phrase p = new Phrase();

        while (!curr.getType().equals(Scanner.TOKEN_TYPE.END_OF_PHRASE) &&
                !curr.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE) &&
                !curr.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE))
        {
            if (curr.getType().equals(Scanner.TOKEN_TYPE.WORD))
                p.add(curr);

            eat(curr);
        }

        return p;
    }

    /**
     * Parses a Sentence by calling parsePhrase() until the end of a sentence or file is reached.
     * @return a Sentence constructed from parsed Phrases
     */
    public Sentence parseSentence()
    {
        Sentence s = new Sentence();

        while (true)
        {
            s.add(parsePhrase());

            if (curr.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE) ||
                    curr.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE))
                break;

            eat(curr);
        }

        return s;
    }

    /**
     * Parses the Document by calling parseSentence() until the end of the file is reached.
     * Adds the parsed Sentences into the sentences List.
     */
    public void parseDocument()
    {
        while (true)
        {
            if (curr.getType().equals(Scanner.TOKEN_TYPE.WORD))
                sentences.add(parseSentence());

            if (curr.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE))
                break;

            eat(curr);
        }
    }

    /**
     * Performs a shallow copy of the List of Sentences that make up the Document.
     * @return a shallow copy of the sentences List
     */
    public List<Sentence> getSentences()
    {
        return new ArrayList<>(sentences);
    }

    /**
     * Returns a String output of the List of Sentences.
     * @return a String output of the List of Sentences
     */
    public String toString()
    {
        return sentences.toString();
    }
}
