import java.awt.*;
import java.util.ArrayList;

/**
 * The Rook class is a Piece that can travel infinite steps in all 4 cardinal directions.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Rook extends Piece
{
    /**
     * Constructs the Rook with the given color and the given image.
     * @param col color of this Rook
     * @param fileName image file name for the icon
     */
    public Rook(Color col, String fileName)
    {
        super(col, fileName, 5);
    }

    /**
     * Returns all destinations that this Rook can visit.
     * @return all destinations that this Rook can visit
     */
    @Override
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> destinations = new ArrayList<Location>();

        sweep(destinations, Location.NORTH);
        sweep(destinations, Location.SOUTH);
        sweep(destinations, Location.WEST);
        sweep(destinations, Location.EAST);

        return destinations;
    }
}
