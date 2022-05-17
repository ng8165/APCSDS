import java.awt.*;
import java.util.ArrayList;

/**
 * The Pawn class is a Piece that can travel one step forward (if empty), one step diagonally (away
 * from where it came from) to capture an opponent piece, and two steps forward for the first move.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Pawn extends Piece
{
    /**
     * Constructs the Pawn with the given color and the given image.
     * @param col color of this Pawn
     * @param fileName image file name for the icon
     */
    public Pawn(Color col, String fileName)
    {
        super(col, fileName, 1);
    }

    /**
     * Returns all destinations that this Pawn can visit.
     * @return all destinations that this Pawn can visit
     */
    @Override
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> destinations = new ArrayList<Location>();
        Board board = getBoard();
        Color color = getColor();
        Location loc = getLocation(), forward, doubleForward, diagLeft, diagRight;

        if (color.equals(Color.BLACK))
        {
            forward = loc.getAdjacentLocation(Location.SOUTH);
            doubleForward = forward.getAdjacentLocation(Location.SOUTH);
            diagLeft = loc.getAdjacentLocation(Location.SOUTHWEST);
            diagRight = loc.getAdjacentLocation(Location.SOUTHEAST);
        }
        else
        {
            forward = loc.getAdjacentLocation(Location.NORTH);
            doubleForward = forward.getAdjacentLocation(Location.NORTH);
            diagLeft = loc.getAdjacentLocation(Location.NORTHWEST);
            diagRight = loc.getAdjacentLocation(Location.NORTHEAST);
        }

        if (isValidDestination(forward) && board.get(forward) == null)
            destinations.add(forward);

        if (((color.equals(Color.BLACK) && loc.getRow() == 1) ||
                (color.equals(Color.WHITE) && loc.getRow() == 6)) &&
                isValidDestination(doubleForward) && board.get(doubleForward) == null)
            destinations.add(doubleForward);

        if (isValidDestination(diagLeft) && board.get(diagLeft) != null)
            destinations.add(diagLeft);

        if (isValidDestination(diagRight) && board.get(diagRight) != null)
            destinations.add(diagRight);

        return destinations;
    }
}
