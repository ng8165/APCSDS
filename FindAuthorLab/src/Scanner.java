import java.io.IOException;
import java.io.Reader;

/**
 * A Scanner is responsible for reading an input stream one character at a
 * time and separating the input into tokens. A token is defined as:
 *  1. A 'word' which is defined as a non-empty sequence of characters that
 *     begins with an alpha character and then consists of alpha characters, 
 *     numbers, the single quote character "'", or the hyphen character "-".
 *  2. An 'end-of-sentence' delimiter defined as any one of the characters ".", "?", "!".
 *  3. An end-of-file token which is returned when the scanner is asked for a
 *     token and the input is at the end-of-file.
 *  4. A phrase separator which consists of one of the characters ",", ":", or  ";".
 *  5. A digit.
 *  6. Any other character not defined above.
 * @author Mr. Page
 * @author Nelson Gou
 * @version 5/16/22
 */
public class Scanner
{
    private Reader in;
    private String currentChar;
    private boolean endOfFile;

    public enum TOKEN_TYPE
    {
        /**
         * The WORD type represents a word (see 1 in class documentation).
         */
        WORD,
        /**
         * The END_OF_SENTENCE type represents the end of a sentence (see 2 in class documentation).
         */
        END_OF_SENTENCE,
        /**
         * The END_OF_FILE type represents the end of a file (see 3 in class documentation).
         */
        END_OF_FILE,
        /**
         * The END_OF_PHRASE type represents the end of a phrase (see 4 in class documentation).
         */
        END_OF_PHRASE,
        /**
         * The DIGIT type represents a digit (see 5 in class documentation).
         */
        DIGIT,
        /**
         * The UNKNOWN type represents an unknown character (see 6 in class documentation).
         */
        UNKNOWN
    }

    /**
     * Constructor for Scanner objects.  The Reader object should be one of
     * 1. A StringReader
     * 2. A BufferedReader wrapped around an InputStream
     * 3. A BufferedReader wrapped around a FileReader
     * The instance field for the Reader is initialized to the input parameter,
     * and the endOfFile indicator is set to false.  The currentChar field is
     * initialized by the getNextChar method.
     * @param in the reader object supplied by the program constructing this Scanner object.
     */
    public Scanner(Reader in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
    }

    /**
     * The getNextChar method attempts to get the next character from the input
     * stream. It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * @postcondition the input stream is advanced one character if it is not at end of file,
     *                and the currentChar instance field is set to the String representation of
     *                the character read from the input stream. The flag endOfFile is set true if
     *                the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if (inp == -1)
                endOfFile = true;
            else 
                currentChar = "" + (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Compares the current character with an input String.
     * If they are equal, scans the next character.
     * @param str the given String to compare
     * @throws IllegalArgumentException if the input String is not equal to the current character
     */
    private void eat(String str)
    {
        if (str.equals(currentChar))
            getNextChar();
        else
            throw new IllegalArgumentException(str + " is not equal to " + currentChar);
    }

    /**
     * Determines if the given String is a character in the alphabet.
     * @param str the input String
     * @precondition str must have a length of 1
     * @return true if str is a letter; otherwise false
     */
    private boolean isLetter(String str)
    {
        return "abcdefghijklmnopqrstuvwxyz".contains(str.toLowerCase());
    }

    /**
     * Determines if the given String is a digit.
     * @param str the input String
     * @precondition str must have a length of 1
     * @return true if str is a digit; otherwise false
     */
    private boolean isDigit(String str)
    {
        return "1234567890".contains(str);
    }

    /**
     * Determines if the given String is a single quote (') or a hyphen (-).
     * @param str the input String
     * @precondition str must have a length of 1
     * @return true if str is a special character; otherwise false
     */
    private boolean isSpecialCharacter(String str)
    {
        return "'-".contains(str);
    }

    /**
     * Determines if the given String is a comma (,), a colon (:), or a semicolon (;).
     * @param str the input String
     * @precondition str must have a length of 1
     * @return true if str is a phrase terminator; otherwise false
     */
    private boolean isPhraseTerminator(String str)
    {
        return ",:;".contains(str);
    }

    /**
     * Determines if the given String is a period (.), a question mark (?),
     * or an exclamation mark (!).
     * @param str the input String
     * @precondition str must have a length of 1
     * @return true if str is a sentence terminator; otherwise false
     */
    private boolean isSentenceTerminator(String str)
    {
        return ".?!".contains(str);
    }

    /**
     * Determines if the given String is whitespace, a new line, carriage return, or a tab.
     * @param str the input String
     * @precondition str must have a length of 1
     * @return true if str is a space; otherwise false
     */
    private boolean isSpace(String str)
    {
        return " \n\r\t".contains(str);
    }

    /**
     * Determines if there is another Token (not at the end of file).
     * @return true if there is another Token; otherwise false
     */
    public boolean hasNextToken()
    {
        return !endOfFile;
    }

    /**
     * Outputs the next Token by processing scanned characters into Tokens.
     * See the class documentation for detailed descriptions of Token types.
     * @return the next Token
     */
    public Token nextToken()
    {
        while (isSpace(currentChar) && hasNextToken())
            eat(currentChar);

        if (!hasNextToken())
            return new Token(TOKEN_TYPE.END_OF_FILE, null);

        Token ret;

        if (isLetter(currentChar))
        {
            StringBuilder sb = new StringBuilder();

            while (hasNextToken() && (isLetter(currentChar) ||
                    isSpecialCharacter(currentChar) || isDigit(currentChar)))
            {
                sb.append(currentChar);
                eat(currentChar);
            }

            return new Token(TOKEN_TYPE.WORD, sb.toString().toLowerCase());
        }
        else if (isSentenceTerminator(currentChar))
            ret = new Token(TOKEN_TYPE.END_OF_SENTENCE, currentChar);
        else if (isPhraseTerminator(currentChar))
            ret = new Token(TOKEN_TYPE.END_OF_PHRASE, currentChar);
        else if (isDigit(currentChar))
            ret = new Token(TOKEN_TYPE.DIGIT, currentChar);
        else
            ret = new Token(TOKEN_TYPE.UNKNOWN, currentChar);

        eat(currentChar);
        return ret;
    }
}
