/**
 * The immutable Token class represents a token in a given text.
 * Each Token has a type (WORD, END_OF_SENTENCE, etc.) and a value.
 * @author Nelson Gou
 * @version 4/18/22
 */
public final class Token
{
    private final Scanner.TOKEN_TYPE type;
    private final String value;

    /**
     * Constructs a Token with a given type and a value.
     * @param t a TOKEN_TYPE
     * @param str a String value
     */
    public Token(Scanner.TOKEN_TYPE t, String str)
    {
        type = t;
        value = str;
    }

    /**
     * Getter for the Token type.
     * @return the TOKEN_TYPE
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Getter for the Token value.
     * @return the value
     */
    public Scanner.TOKEN_TYPE getType()
    {
        return type;
    }

    /**
     * Returns a String output of the Token (type and value).
     * @return a String output of the Token
     */
    @Override
    public String toString()
    {
        return /* type + ": " + */ value;
    }

    /**
     * Compares equality with this Token and a given Object.
     * @param o the Object to compare equality with
     * @return true if the Object is a Token and has the same type and value; otherwise false
     * @throws IllegalArgumentException if the given Object is not a Token
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Token))
            throw new IllegalArgumentException("Object is not a Token");

        Token other = (Token) o;

        return type.equals(other.getType()) && value.equals(other.getValue());
    }
}
