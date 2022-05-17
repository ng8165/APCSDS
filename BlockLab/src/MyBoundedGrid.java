import java.util.ArrayList;

/**
 * TheMyBoundedGrid<E> class creates a grid with elements of type E.
 * @author Nelson Gou
 * @version 2/28/22
 * @param <E> the type of the grid elements
 */
public class MyBoundedGrid<E>
{
    private Object[][] grid;

    /**
     * Constructs a MyBoundedGrid with a specified number of rows and columns.
     * @precondition rows and cols are positive
     * @param rows number of rows for the grid
     * @param cols number of columns for the grid
     */
    public MyBoundedGrid(int rows, int cols)
    {
        grid = new Object[rows][cols];
    }

    /**
     * Getter for the number of rows in the grid.
     * @return the number of rows in the grid
     */
    public int getNumRows()
    {
        return grid.length;
    }

    /**
     * Getter for the number of columns in the grid.
     * @return the number of columns in the grid
     */
    public int getNumCols()
    {
        return grid[0].length;
    }

    /**
     * Tests if the give Location is valid (within grid row and columns).
     * @param loc given Location to test validity for
     * @precondition loc is not null
     * @return true if rows and columns are non-negative and less than
     *         grid row and columns (respectively)
     */
    public boolean isValid(Location loc)
    {
        return loc.getRow() >= 0 && loc.getRow() < getNumRows() &&
               loc.getCol() >= 0 && loc.getCol() < getNumCols();
    }

    /**
     * Places an E element into the grid.
     * If an element is already in the grid, replaces and returns the old value.
     * @param loc location to place the element in
     * @param obj element to be placed in the grid
     * @return null if no element in that position or loc is invalid,
     *         else old element in that position
     */
    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            return null;

        E old = (E) grid[loc.getRow()][loc.getCol()];
        grid[loc.getRow()][loc.getCol()] = obj;
        return old;
    }

    /**
     * Removes an E element into the grid and returns the removed element.
     * If there is no element in that location, returns null.
     * @param loc location to remove the element from
     * @return the removed element, or null if there is no element or the loc is invalid
     */
    public E remove(Location loc)
    {
        if (!isValid(loc))
            return null;

        E old = (E) grid[loc.getRow()][loc.getCol()];
        grid[loc.getRow()][loc.getCol()] = null;
        return old;
    }

    /**
     * Returns the E element in the grid at a specified location.
     * If there is no element in that location, returns null.
     * @param loc location to get the element from
     * @return the element, or null if there is no element or loc is invalid
     */
    public E get(Location loc)
    {
        if (!isValid(loc))
            return null;

        return (E) grid[loc.getRow()][loc.getCol()];
    }

    /**
     * Returns an ArrayList of Location containing the indices of all occupied cells.
     * @return an ArrayList of Location containing the indices of all occupied cells.
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();

        for (int i=0; i<grid.length; i++)
        {
            for (int j=0; j<grid[i].length; j++)
            {
                if (grid[i][j] != null)
                    locs.add(new Location(i, j));
            }
        }

        return locs;
    }
}
