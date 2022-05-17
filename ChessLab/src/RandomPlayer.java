import java.awt.*;
import java.util.ArrayList;

/**
 * The RandomPlayer class represents a Chess Player that chooses random Moves.
 * @author Nelson Gou
 * @version 4/9/22
 */
public class RandomPlayer extends Player
{
    /**
     * Constructs a new Player.
     * @param b board where the PLayer is
     * @param n the Player's name
     * @param c the Player's color
     */
    public RandomPlayer(Board b, String n, Color c)
    {
        super(b, n, c);
    }

    /**
     * Returns a random Move from all the possible moves by players of the same color.
     * @return a random Move
     */
    @Override
    public Move nextMove()
    {
        ArrayList<Move> moves = getBoard().allMoves(getColor());
        return moves.get((int) (Math.random() * moves.size()));
    }
}
