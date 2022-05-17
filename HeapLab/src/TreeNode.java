/**
 * The TreeNode class represents a node in a binary tree,
 * with a value, left pointer, and right pointer.
 * @author Binary Tree Lab Writers
 * @author Nelson Gou: documentation
 * @version 11/19/2021
 */
public class TreeNode
{
    private Object value;
    private TreeNode left;
    private TreeNode right;

    /**
     * Constructs a leaf node (null left and right) with a value.
     * @param initValue initial value for the TreeNode
     */
    public TreeNode(Object initValue)
    {
        this(initValue, null, null);
    }

    /**
     * Constructs a TreeNode with a value, left pointer, and right pointer.
     * @param initValue initial value for the TreeNode
     * @param initLeft initial left pointer for the TreeNode
     * @param initRight initial right pointer for the TreeNode
     */
    public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
    {
        value = initValue;
        left = initLeft;
        right = initRight;
    }

    /**
     * Getter for the value.
     * @return value of the TreeNode
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Getter for the left node.
     * @return reference to the left node
     */
    public TreeNode getLeft()
    {
        return left;
    }

    /**
     * Getter for the right node.
     * @return reference to the right node
     */
    public TreeNode getRight()
    {
        return right;
    }

    /**
     * Setter for the value.
     * @param theNewValue new value for the node
     */
    public void setValue(Object theNewValue)
    {
        value = theNewValue;
    }

    /**
     * Setter for the left node.
     * @param theNewLeft new value for the left node
     */
    public void setLeft(TreeNode theNewLeft)
    {
        left = theNewLeft;
    }

    /**
     * Setter for the right node.
     * @param theNewRight new value for the right node
     */
    public void setRight(TreeNode theNewRight)
    {
        right = theNewRight;
    }
}