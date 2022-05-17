import java.awt.*;

/**
 * The Player class represents a Chess player.
 * @author Nelson Gou
 * @version 4/9/22
 */
public abstract class Player
{
    private Board board;
    private String name;
    private Color color;

    /**
     * Constructs a new Player.
     * @param b board where the PLayer is
     * @param n the Player's name
     * @param c the Player's color
     */
    public Player(Board b, String n, Color c)
    {
        board = b;
        name = n;
        color = c;
    }

    /**
     * Getter for the board.
     * @return the board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * Getter for the name.
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for the color.
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Returns the next Move that this player will make.
     * @return the next Move that this player will make
     */
    public abstract Move nextMove();
}
