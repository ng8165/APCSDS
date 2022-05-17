import java.awt.*;
import java.util.ArrayList;

/**
 * The Knight class is a Piece that can travel two steps in any of the 4 directions,
 * then can move one step in any of the two opposite directions.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Knight extends Piece
{
    private static final int[][] DIRS = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

    /**
     * Constructs the Knight with the given color and the given image.
     * @param col color of this Knight
     * @param fileName image file name for the icon
     */
    public Knight(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**
     * Returns all destinations that this Knight can visit.
     * @return all destinations that this Knight can visit
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
