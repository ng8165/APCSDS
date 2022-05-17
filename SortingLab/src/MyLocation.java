/**
 * The MyLocation class stores a row and a column for a location.
 * @author Nelson Gou
 * @version 2/10/22
*/
public class MyLocation implements Comparable
{
    private int row;
    private int col;

    /**
     * Constructs a MyLocation object with the given row and column.
     * @param r row of this MyLocation
     * @param c column of this MyLocaiton
     */
    public MyLocation(int r, int c)
    {
        row = r;
        col = c;
    }

    /**
     * Getter for the row.
     * @return the row of this MyLocation
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Getter for the column.
     * @return the column of this MyLocation
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Determines if this MyLocation is equal to another Object.
     * @param other the Object to compare equality with
     * @return true if they are equal; otherwise false
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof MyLocation))
            return false;

        MyLocation loc = (MyLocation) other;
        return row == loc.row && col == loc.col;
    }

    /**
     * Returns a String representation of this MyLocation.
     * @return a String representation of this MyLocation
     */
    @Override
    public String toString()
    {
        return "(" + row + ", " + col + ")";
    }

    /**
     * Returns 0 if MyLocation is equal to x, -1 if smaller, and 1 if greater.
     * @return 0 if MyLocation is equal to x, -1 if smaller, and 1 if greater
     */
    @Override
    public int compareTo(Object x)
    {
        if (!(x instanceof MyLocation))
            return -1;

        MyLocation loc = (MyLocation) x;

        if (row == loc.row)
            return col - loc.col;

        return row - loc.row;
    }
}