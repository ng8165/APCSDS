import java.awt.*;
import java.util.ArrayList;

/**
 * The Bishop class is a Piece that can travel two steps in any of the 4 directions,
 * then can move one step in any of the two opposite directions.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Bishop extends Piece
{
    /**
     * Constructs the Bishop with the given color and the given image.
     * @param col color of this Bishop
     * @param fileName image file name for the icon
     */
    public Bishop(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**
     * Returns all destinations that this Bishop can visit.
     * @return all destinations that this Bishop can visit
     */
    @Override
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> destinations = new ArrayList<Location>();

        sweep(destinations, Location.NORTHEAST);
        sweep(destinations, Location.SOUTHEAST);
        sweep(destinations, Location.NORTHWEST);
        sweep(destinations, Location.SOUTHWEST);

        return destinations;
    }
}
