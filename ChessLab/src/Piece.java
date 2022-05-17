import java.awt.*;
import java.util.*;

/**
 * The Piece class describes a Chess piece.
 * @author Nelson Gou
 * @version 4/9/22
 */
public abstract class Piece
{
    private Board board;
    private Location location;
    private Color color;
    private String imageFileName;
    private int value;

    /**
     * Constructs a Chess Piece. Sets a color, file name for the icon, and a value.
     * @param col color for this Piece
     * @param fileName file name for the icon
     * @param val value (represents relative value for this Piece)
     */
    public Piece(Color col, String fileName, int val)
    {
        color = col;
        imageFileName = fileName;
        value = val;
    }

    /**
     * Returns the board this piece is on.
     * @return the board this piece is on
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * Returns the location of this piece on the board.
     * @return the location of this piece on the board
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Returns the color of this piece.
     * @return the color of this piece
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Returns the name of the file used to display this piece.
     * @return the name of the file used to display this piece
     */
    public String getImageFileName()
    {
        return imageFileName;
    }

    /**
     * Returns a number representing the relative value of this piece.
     * @return a number representing the relative value of this piece
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Puts this piece into a board. If there is another
     * piece at the givenLocation, it is removed.
     * @precondition this Piece is not contained in a grid, and loc is valid in gr
     * @param brd the board into which this piece should be placed
     * @param loc the location into which the piece should be placed
     * @throws IllegalStateException if the piece is already in a board
     */
    public void putSelfInGrid(Board brd, Location loc)
    {
        if (board != null)
            throw new IllegalStateException(
                    "This piece is already contained in a board.");

        Piece piece = brd.get(loc);
        if (piece != null)
            piece.removeSelfFromGrid();
        brd.put(loc, this);
        board = brd;
        location = loc;
    }

    /**
     * Removes this Piece from its board.
     * @precondition this Piece is contained in a board
     * @throws IllegalStateException if the piece is not in a board or the board has a different
     *                               at the location
     */
    public void removeSelfFromGrid()
    {
        if (board == null)
            throw new IllegalStateException(
                    "This piece is not contained in a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");

        board.remove(location);
        board = null;
        location = null;
    }

    /**
     * Moves this piece to a new location. If there is
     * another piece at the given Location, it is removed.
     * @precondition this Piece is contained in a grid, and newLocation is valid in the grid
     * @param newLocation the new location
     * @throws IllegalStateException if the Piece is not on a board or the board
     *                               has a different Piece at the location
     * @throws IllegalArgumentException if the given Location is invalid
     */
    public void moveTo(Location newLocation)
    {
        if (board == null)
            throw new IllegalStateException("This piece is not on a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");
        if (!board.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(location))
            return;
        board.remove(location);
        Piece other = board.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        board.put(location, this);
    }

    /**
     * Determines if the given Location is valid for this piece to move to.
     * @param dest the Location to test validity for
     * @return true if the Location is valid, and it is either empty or another color in the grid;
     *         otherwise false
     */
    public boolean isValidDestination(Location dest)
    {
        return board.isValid(dest) &&
                (board.get(dest) == null || !board.get(dest).getColor().equals(color));
    }

    /**
     * Returns the destinations that this Piece can reach.
     * @return the destinations that this Piece can reach
     */
    public abstract ArrayList<Location> destinations();

    /**
     * Sweeps in a given direction until the edge of the board
     * is reached or another piece is reached.
     * @param dests the ArrayList to add the sweeped Locations to
     * @param direction the direction to sweep in
     */
    public void sweep(ArrayList<Location> dests, int direction)
    {
        Location curr = location.getAdjacentLocation(direction);

        while (curr != null && isValidDestination(curr))
        {
            dests.add(curr);

            if (board.get(curr) != null && board.get(curr).getColor() != color)
                curr = null;
            else
                curr = curr.getAdjacentLocation(direction);
        }
    }
}