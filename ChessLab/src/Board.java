import java.awt.*;
import java.util.*;

/**
 * The Board represents a rectangular game board containing Piece objects.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Board extends BoundedGrid<Piece>
{
    /**
     * Constructs a 8x8 board.
     */
    public Board()
    {
        super(8, 8);
    }

    /**
     * Undoes the last move.
     * @param move the move to undo
     * @precondition the move has already been made on the board
     * @postcondition piece is at the source, and any captured pieces
     *                are returned to its original locations
     */
    public void undoMove(Move move)
    {
        Piece piece = move.getPiece();
        Location source = move.getSource();
        Location dest = move.getDestination();
        Piece victim = move.getVictim();

        piece.moveTo(source);

        if (victim != null)
            victim.putSelfInGrid(piece.getBoard(), dest);
    }

    /**
     * Returns all moves that Pieces of the specified color can make.
     * @param color the color of the Pieces
     * @return an ArrayList containing all possible Moves
     */
    public ArrayList<Move> allMoves(Color color)
    {
        ArrayList<Location> occupied = getOccupiedLocations();
        ArrayList<Move> moves = new ArrayList<Move>();

        for (Location l: occupied)
        {
            Piece p = get(l);

            if (p.getColor().equals(color))
            {
                for (Location d: p.destinations())
                    moves.add(new Move(p, d));
            }
        }

        return moves;
    }

    /**
     * Executes a given move.
     * @param move the move to execute
     */
    public void executeMove(Move move)
    {
        if (move.getPiece().isValidDestination(move.getDestination()))
        {
            if (move.getVictim() != null)
                move.getVictim().removeSelfFromGrid();

            move.getPiece().moveTo(move.getDestination());
        }
    }

    /**
     * Determines if the Game is over by counting the number of Kings.
     * @return true if there are less than 2 Kings; otherwise false
     */
    public boolean isGameOver()
    {
        int kings = 0;

        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (get(new Location(i, j)) instanceof King)
                    kings++;
            }
        }

        return kings < 2;
    }

    /**
     * Checks if the King of the given color is checked.
     * @param color color of the King
     * @return true if checked; otherwise false
     */
    public boolean isCheck(Color color)
    {
        Color other = Color.WHITE;
        if (other.equals(color))
            other = Color.BLACK;

        for (Move m: allMoves(other))
        {
            if (m.getVictim() instanceof King)
                return true;
        }

        return false;
    }
}