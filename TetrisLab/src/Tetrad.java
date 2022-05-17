import javax.swing.*;
import java.awt.Color;
import java.util.concurrent.Semaphore;

/**
 * The Tetrad class describes a Tetris piece.
 * @author Nelson Gou
 * @version 3/10/22
 */
public class Tetrad
{
    private static final Color[] COLORS = {Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.ORANGE,
                                           Color.BLUE, Color.GREEN, Color.RED};

    private Block[] blocks;
    private Semaphore lock;

    /**
     * Constructs a Tetrad (Tetris piece).
     * Chooses a random color for the 4 blocks and puts them in the blocks array.
     * @param grid grid in which the Tetrad should exist
     * @param display used to show error message in case game ends
     */
    public Tetrad(MyBoundedGrid<Block> grid, BlockDisplay display)
    {
        blocks = new Block[4];

        lock = new Semaphore(1, true);

        int rand = (int) (Math.random() * 7);

        for (int i=0; i<4; i++)
        {
            blocks[i] = new Block();
            blocks[i].setColor(COLORS[rand]);
        }

        Location[] locs;

        if (rand == 0) // Red
            locs = new Location[] {new Location(1, 4), new Location(0, 4),
                                   new Location(2, 4), new Location(3, 4)};
        else if (rand == 1) // Gray
            locs = new Location[] {new Location(0, 4), new Location(0, 3),
                                   new Location(0, 5), new Location(1, 4)};
        else if (rand == 2) // Cyan
            locs = new Location[] {new Location(0, 4), new Location(0, 5),
                                   new Location(1, 4), new Location(1, 5)};
        else if (rand == 3) // Yellow
            locs = new Location[] {new Location(2, 4), new Location(1, 4),
                                   new Location(0, 4), new Location(2, 5)};
        else if (rand == 4) // Magenta
            locs = new Location[] {new Location(2, 5), new Location(1, 5),
                                   new Location(0, 5), new Location(2, 4)};
        else if (rand == 5) // Blue
            locs = new Location[] {new Location(0, 4), new Location(1, 3),
                                   new Location(1, 4), new Location(0, 5)};
        else // Green
            locs = new Location[] {new Location(0, 4), new Location(0, 3),
                                   new Location(1, 4), new Location(1, 5)};

        if (!areEmpty(grid, locs))
        {
            display.endGameMessage();
            System.exit(69);
        }

        addToLocations(grid, locs);
    }

    /**
     * Adds the four blocks to the four specified locations in the specified grid.
     * @param grid grid to add the blocks to
     * @param locs locations the blocks should be added to
     * @precondition blocks are not in any grid; locs.length == 4
     * @postcondition the locations of blocks match locs, and the blocks have been put in grid
     */
    private void addToLocations(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for (int i=0; i<4; i++)
            blocks[i].putSelfInGrid(grid, locs[i]);
    }

    /**
     * Removes blocks from their grid and returns their old locations.
     * @precondition blocks are in a grid
     * @postcondition blocks have been removed from the grid
     * @return array containing old locations of the blocks
     */
    private Location[] removeBlocks()
    {
        Location[] old = new Location[4];

        for (int i=0; i<4; i++)
        {
            old[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }

        return old;
    }

    /**
     * Checks to see if all Locations in locs are empty and valid in the given grid.
     * @param grid grid to check Locations in
     * @param locs array of Locations to check
     * @return true if all Locations in locs are valid and empty in grid; otherwise false.
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for (Location loc: locs)
        {
            if (!grid.isValid(loc) || grid.get(loc) != null)
                return false;
        }

        return true;
    }

    /**
     * Attempts to translate this Tetrad by deltaRows down and deltaCol right.
     * Returns true if okay to do so or false if not.
     * @param deltaRow how many rows to move down
     * @param deltaCol how many columns to move to the right
     * @postcondition Attempts to move this tetrad deltaRow rows down and deltaCol
     *                columns to the right if those positions are valid and empty
     * @return true if successful; otherwise false
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        try
        {
            lock.acquire();

            MyBoundedGrid<Block> grid = blocks[0].getGrid();

            Location[] locs = removeBlocks();
            Location[] translated = new Location[4];

            for (int i=0; i<4; i++)
                translated[i] = new Location(locs[i].getRow()+deltaRow, locs[i].getCol()+deltaCol);

            if (!areEmpty(grid, translated))
            {
                addToLocations(grid, locs);
                return false;
            }

            addToLocations(grid, translated);
            return true;
        }
        catch (InterruptedException e)
        {
            // did not modify the tetrad
            return false;
        }
        finally
        {
            lock.release();
        }

    }

    /**
     * Rotates this tetrad clockwise by 90 degrees about its center
     * if necessary positions are valid and empty.
     * @return true if successful; otherwise false
     */
    public boolean rotate()
    {
        try
        {
            lock.acquire();

            MyBoundedGrid<Block> grid = blocks[0].getGrid();

            Location[] locs = removeBlocks();
            Location[] rotated = new Location[4];

            rotated[0] = locs[0];

            for (int i=1; i<4; i++)
                rotated[i] = new Location(locs[0].getRow()-locs[0].getCol()+locs[i].getCol(),
                        locs[0].getRow()+locs[0].getCol()-locs[i].getRow());

            if (!areEmpty(grid, rotated))
            {
                addToLocations(grid, locs);
                return false;
            }

            addToLocations(grid, rotated);
            return true;
        }
        catch (InterruptedException e)
        {
            // did not modify the tetrad
            return false;
        }
        finally
        {
            lock.release();
        }
    }
}
