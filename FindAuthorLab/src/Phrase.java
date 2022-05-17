import java.util.ArrayList;
import java.util.List;

/**
 * The Phrase class stores a List of Token objects.
 * The List data structure was chosen because it is mutable and provides O(1) access and traversal.
 * @author Nelson Gou
 * @version 4/18/22
 */
public class Phrase
{
    private List<Token> tokens;

    /**
     * Constructs a Phrase by instantiating a List of Tokens.
     */
    public Phrase()
    {
        tokens = new ArrayList<>();
    }

    /**
     * Adds a given Token to a List of Tokens. This operation is performed in O(1).
     * @param token the given Token
     */
    public void add(Token token)
    {
        tokens.add(token);
    }

    /**
     * Performs a deep copy of the List of Tokens.
     * @return a new copied List of Tokens
     */
    public List<Token> getTokens()
    {
        List<Token> copy = new ArrayList<>();

        for (Token token: tokens)
            copy.add(new Token(token.getType(), token.getValue()));

        return copy;
    }

    /**
     * Returns a String output of the List of Tokens.
     * @return a String output of the List of Tokens
     */
    public String toString()
    {
        return tokens.toString();
    }
}
