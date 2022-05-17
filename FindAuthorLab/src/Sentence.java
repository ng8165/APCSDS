import java.util.ArrayList;
import java.util.List;

/**
 * The Sentence class stores a List of Phrase objects.
 * The List data structure was chosen because it is mutable and provides fast access and traversal.
 * Adding to the end and random access are O(1) constant-time operations, and traversing is O(n).
 * @author Nelson Gou
 * @version 4/18/22
 */
public class Sentence
{
    private List<Phrase> phrases;

    /**
     * Constructs a Sentence by instantiating the List of Phrases.
     */
    public Sentence()
    {
        phrases = new ArrayList<>();
    }

    /**
     * Adds a given Phrase to the List of Phrases.
     * @param phrase a given Phrase
     */
    public void add(Phrase phrase)
    {
        phrases.add(phrase);
    }

    /**
     * Performs a deep copy of the List of Phrases.
     * @return a new copied List of Phrases
     */
    public List<Phrase> getPhrases()
    {
        List<Phrase> copy = new ArrayList<>();

        for (Phrase phrase: phrases)
        {
            Phrase p = new Phrase();
            for (Token t: phrase.getTokens())
                p.add(t);
            copy.add(p);
        }

        return copy;
    }

    /**
     * Returns a String output of the List of Phrases.
     * @return a String output of the List of Phrases
     */
    public String toString()
    {
        return phrases.toString();
    }
}
