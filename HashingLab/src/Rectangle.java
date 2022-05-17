/**
 * The Rectangle class describes a rectangle of a specified length and width.
 * It can be used with the MyHashSet class.
 * @author Nelson Gou
 * @version 1/31/2022
 */
public class Rectangle
{
    private int length;
    private int width;

    /**
     * Constructs a Rectangle with a specified length and width.
     * @param len length of the rectangle
     * @param w width of the rectangle
     */
    public Rectangle(int len, int w)
    {
        length = len;
        width = w;
    }

    /**
     * Getter for the private instance field length.
     * @return the length of this Rectangle
     */
    public int getLength()
    {
        return length;
    }

    /**
     * Getter for the private instance field width.
     * @return the width of this Rectangle
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns a String version of this Rectangle.
     * @return a String version of this Rectangle
     */
    @Override
    public String toString()
    {
        return length + "x" + width;
    }

    /**
     * Returns a hash code for this Rectangle, equivalent to
     * the absolute value of 31*length*width + 29*length + 53*width.
     * @return a hash code for this Rectangle
     */
    @Override
    public int hashCode()
    {
        return Math.abs(31*length*width-29*length+53*width);
    }

    /**
     * Determines if the values of other are equivalent to this Rectangle.
     * If other is not a Rectangle, returns false. If it is, checks if the lengths
     * and widths are equivalent, and if so, then returns true.
     * @param other an Object to check equality with
     * @return true if other is equal; otherwise false
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Rectangle))
            return false;

        Rectangle o = (Rectangle) other;
        return length == o.length && width == o.width;
    }
}