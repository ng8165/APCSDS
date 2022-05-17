import java.awt.Color;

/**
 * The BLock class encapsulates a Block abstraction which can be placed into a GridWorld-style grid.
 * @author Block Lab Author(s)
 * @author Nelson Gou
 * @version 2/28/22
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;

    /**
     * Constructs a blue block without a BoundedGrid or Location.
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * Getter for this Block's color.
     * @return the color of the Block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Setter for this Block's color.
     * @param newColor the new color for the Block
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Getter for this Block's grid.
     * @return the MyBoundedGrid of the Block
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Getter for this Block's Location.
     * @return the Location of the Block
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Removes the current Block from its BoundedGrid.
     * @precondition the current Block is in a Grid
     * @postcondition the BoundedGrid does not have this Block,
     *                and this Block has no Grid or Location
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Puts the current Block into a specified BoundedGrid at a specified Location.
     * If there is already a Block at the new location, first removes the old block.
     * @param gr BoundedGrid to place the Block in
     * @param loc Location to place the Block at
     * @precondition gr and loc are not null
     * @postcondition the Block has been assigned a grid and location, and the Block is
     *                at the correct Location within its BoundedGrid
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        if (gr.get(loc) != null)
            gr.get(loc).removeSelfFromGrid();

        grid = gr;
        location = loc;
        grid.put(location, this);
    }

    /**
     * Moves the current Block to a new Location within the same BoundedGrid.
     * Removes itself from its old position and places it into the new position.
     * Replaces the old value at the new position.
     * @param newLocation the new Location to move the Block to
     * @precondition the Block is in a Grid, and newLocation is not null
     * @postcondition the Block is at newLocation in the Grid
     */
    public void moveTo(Location newLocation)
    {
        MyBoundedGrid<Block> gr = grid;
        removeSelfFromGrid();
        putSelfInGrid(gr, newLocation);
    }

    /**
     * Returns a String version of this Block (includes location and color).
     * @return a String version of this Block (includes location and color)
     */
    public String toString()
    {
        return "Block[location=" + location + ", color=" + color + "]";
    }
}