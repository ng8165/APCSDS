/**
 * The Card class has a rank, a suit, and a face up variable.
 * @author Nelson Gou
 * @version 11/4/2021
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Clubs suit.
     */
    public static final String CLUBS = "c";

    /**
     * Diamonds suit.
     */
    public static final String DIAMONDS = "d";

    /**
     * Hearts suit.
     */
    public static final String HEARTS = "h";

    /**
     * Spades suit.
     */
    public static final String SPADES = "s";

    /**
     * Array of all suits.
     */
    public static final String[] SUITS = {CLUBS, DIAMONDS, HEARTS, SPADES};

    /**
     * Constructs a Card with a rank and a suit and turns the card down.
     * @param rank given rank
     * @param suit given suit
     */
    public Card(int rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
        isFaceUp = false;
    }

    /**
     * Getter for the rank.
     * @return the rank
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Getter for the suit.
     * @return the suit
     */
    public String getSuit()
    {
        return suit;
    }

    /**
     * Determines if the suit is red.
     * @return true if the suit is red; otherwise false
     */
    public boolean isRed()
    {
        return suit.equals(DIAMONDS) || suit.equals(HEARTS);
    }

    /**
     * Getter for isFaceUp (determines if the card faces up).
     * @return true if the card faces up; otherwise false
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /**
     * Turns the card up.
     * @postcondition isFaceUp is set to true
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /**
     * Turns the card down.
     * @postcondition isFaceUp is set to false
     */
    public void turnDown()
    {
        isFaceUp = false;
    }

    /**
     * Returns the filename for the current Card.
     * @return the filename for the current Card
     */
    public String getFileName()
    {
        if (!isFaceUp)
        {
            return "cards/backapcsds.gif";
        }

        String lookup = " a23456789tjqk";

        return "cards/" + lookup.substring(rank, rank+1) + suit + ".gif";
    }
}
