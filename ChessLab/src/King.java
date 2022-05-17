import java.awt.*;
import java.util.ArrayList;

/**
 * The King class is a Piece that can travel one step in all 8 directions.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class King extends Piece
{
    private static final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

    /**
     * Constructs the King with the given color and the given image.
     * @param col color of this King
     * @param fileName image file name for the icon
     */
    public King(Color col, String fileName)
    {
        super(col, fileName, 1000);
    }

    /**
     * Returns all destinations that this King can visit.
     * @return all destinations that this King can visit
     */
    @Override
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> destinations = new ArrayList<Location>();

        for (int[] d: DIRS)
        {
            Location dest = new Location(super.getLocation().getRow() + d[0],
                    super.getLocation().getCol() + d[1]);

            if (isValidDestination(dest))
                destinations.add(dest);
        }

        return destinations;
    }
}
