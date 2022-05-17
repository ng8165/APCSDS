/**
 * The DoubleNode class is a node that is doubly-linked for use in a LinkedList.
 * @author List Lab Starting Code
 * @author Nelson Gou
 * @version 10/11/21
 */
public class DoubleNode
{
    private Object value;
    private DoubleNode previous;
    private DoubleNode next;

    /**
     * Constructs a DoubleNode with a given value and previous and next null.
     * @param v the given value
     */
    public DoubleNode(Object v)
    {
        value = v;
        previous = null;
        next = null;
    }

    /**
     * Getter for the value.
     * @return value
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Getter for the previous DoubleNode.
     * @return previous DoubleNode
     */
    public DoubleNode getPrevious()
    {
        return previous;
    }

    /**
     * Getter for the next DoubleNode.
     * @return next DoubleNode
     */
    public DoubleNode getNext()
    {
        return next;
    }

    /**
     * Setter for the value.
     * @param v the new value
     */
    public void setValue(Object v)
    {
        value = v;
    }

    /**
     * Setter for the previous DoubleNode.
     * @param p previous DoubleNode
     */
    public void setPrevious(DoubleNode p)
    {
        previous = p;
    }

    /**
     * Setter for the next DoubleNode.
     * @param n next DoubleNode
     */
    public void setNext(DoubleNode n)
    {
        next = n;
    }
}