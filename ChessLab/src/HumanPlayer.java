import java.awt.*;
import java.util.ArrayList;

/**
 * The HumanPlayer class represents a human Chess Player.
 * @author Nelson Gou
 * @version 4/9/22
 */
public class HumanPlayer extends Player
{
    private BoardDisplay display;

    /**
     * Constructs a new Player.
     * @param b board where the PLayer is
     * @param n the Player's name
     * @param c the Player's color
     * @param d the BoardDisplay
     */
    public HumanPlayer(Board b, String n, Color c, BoardDisplay d)
    {
        super(b, n, c);
        display = d;
    }

    /**
     * Returns the Move that the HumanPlayer has selected.
     * If the requested Move is invalid, denies the Move and asks again.
     * @return the Move that the HumanPlayer has selected
     */
    @Override
    public Move nextMove()
    {
        ArrayList<Move> moves = getBoard().allMoves(getColor());
        Move move = null;

        while (!moves.contains(move))
            move = display.selectMove();

        return move;
    }
}
