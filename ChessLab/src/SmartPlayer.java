import java.awt.*;
import java.util.ArrayList;

/**
 * The SmartPlayer class represents a Chess Player that uses the minimax algorithm
 * to look ahead and choose the optimal Move to make.
 * @author Nelson Gou
 * @version 4/9/22
 */
public class SmartPlayer extends Player
{
    /**
     * Constructs a new Player.
     * @param b board where the PLayer is
     * @param n the Player's name
     * @param c the Player's color
     */
    public SmartPlayer(Board b, String n, Color c)
    {
        super(b, n, c);
    }

    /**
     * Returns the next Move that the SmartPlayer will make.
     * @return the next Move that the SmartPlayer will make
     */
    @Override
    public Move nextMove()
    {
        // mini(mum)-max(imum) algorithm
        return valueOfBestMove(3).getMove();
    }

    /**
     * Returns the score for the whole board.
     * Adds the value for all pieces of the same color and subtracts for the opposite.
     * @return the score for the whole board
     */
    private int score()
    {
        ArrayList<Location> occupied = getBoard().getOccupiedLocations();
        int score = 0;

        for (Location loc: occupied)
        {
            Piece piece = getBoard().get(loc);
            if (piece.getColor().equals(getColor()))
                score += piece.getValue();
            else
                score -= piece.getValue();
        }

        return score;
    }

    /**
     * This function makes up one half of the minimax algorithm (the mini part).
     * It chooses the minimum score out of all moves. This represents the other Player.
     * @param lookAhead the number of layers to look ahead
     * @return the best move the other Player can make
     */
    private Pair valueOfMeanestResponse(int lookAhead)
    {
        // mini(mum)

        if (lookAhead <= 0)
            return new Pair(score(), null);

        lookAhead--;

        Color color = Color.BLACK;
        if (getColor().equals(Color.BLACK))
            color = Color.WHITE;

        ArrayList<Move> moves = getBoard().allMoves(color);
        int min = Integer.MAX_VALUE;
        Move best = null;

        for (Move move: moves)
        {
            getBoard().executeMove(move);

            Pair p = valueOfBestMove(lookAhead);
            if (p.getScore() < min)
            {
                min = p.getScore();
                best = move;
            }

            getBoard().undoMove(move);
        }

        return new Pair(min, best);
    }

    /**
     * This function makes up one half of the minimax algorithm (the max part).
     * It chooses the maximum score out of all moves. This represents this SmartPlayer.
     * @param lookAhead the number of layers to look ahead
     * @return the best move the SmartPlayer can make
     */
    private Pair valueOfBestMove(int lookAhead)
    {
        // max(imum)

        if (lookAhead <= 0)
            return new Pair(score(), null);

        lookAhead--;

        ArrayList<Move> moves = getBoard().allMoves(getColor());
        int max = Integer.MIN_VALUE;
        Move best = null;

        for (Move move: moves)
        {
            getBoard().executeMove(move);

            Pair p = valueOfMeanestResponse(lookAhead);
            if (p.getScore() > max)
            {
                max = p.getScore();
                best = move;
            }

            getBoard().undoMove(move);
        }

        return new Pair(max, best);
    }

    /**
     * The Pair class pairs an Integer and a Move, which represent a score and a Move.
     * @author Nelson Gou
     * @version 4/9/22
     */
    private static class Pair
    {
        private int score;
        private Move move;

        /**
         * Constructs a Pair comprising of a score and a Move.
         * @param sc the score
         * @param m the Move
         */
        public Pair(int sc, Move m)
        {
            score = sc;
            move = m;
        }

        /**
         * Getter for the score.
         * @return the score
         */
        public int getScore()
        {
            return score;
        }

        /**
         * Getter for the Move.
         * @return the Move
         */
        public Move getMove()
        {
            return move;
        }
    }
}
