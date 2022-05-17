import java.awt.*;
import java.util.ArrayList;

/**
 * The Queen class is a Piece that can travel as many steps as possible in any
 * of the 8 cardinal directions.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Queen extends Piece
{
    /**
     * Constructs the Queen with the given color and the given image.
     * @param col color of this Queen
     * @param fileName image file name for the icon
     */
    public Queen(Color col, String fileName)
    {
        super(col, fileName, 9);
    }

    /**
     * Returns all destinations that this Queen can visit.
     * @return all destinations that this Queen can visit
     */
    @Override
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> destinations = new ArrayList<Location>();

        sweep(destinations, Location.NORTH);
        sweep(destinations, Location.SOUTH);
        sweep(destinations, Location.WEST);
        sweep(destinations, Location.EAST);
        sweep(destinations, Location.NORTHEAST);
        sweep(destinations, Location.SOUTHEAST);
        sweep(destinations, Location.NORTHWEST);
        sweep(destinations, Location.SOUTHWEST);

        return destinations;
    }
}
