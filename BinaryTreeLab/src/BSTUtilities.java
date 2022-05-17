/**
 * The BSTUtilities class contains a collection of static methods
 * for operating on binary search trees.
 * @author Nelson Gou
 * @version 12/9/2021
 */
public abstract class BSTUtilities
{
    /**
     * Determines if a value is contained inside the BST or not.
     * @param t root of the BST
     * @param x value to search for
     * @param display TreeDisplay for graphics
     * @precondition t is a binary search tree in ascending order
     * @return true if t contains the value x; otherwise, returns false
     */
    public static boolean contains(TreeNode t, Comparable x, TreeDisplay display)
    {
        if (t == null)
            return false;

        display.visit(t);

        if (x.compareTo(t.getValue()) == 0)
            return true;

        else if (x.compareTo(t.getValue()) < 0)
            return contains(t.getLeft(), x, display);

        else
            return contains(t.getRight(), x, display);
    }

    /**
     * Inserts a value inside the BST.
     * @param t root of the BST
     * @param x value to insert
     * @param display TreeDisplay for graphics
     * @precondition t is a binary search tree in ascending order
     * @postcondition x is ignored if it is a duplicate of an element already in t;
     *                only one new TreeNode is created in the course of the traversal
     * @return if t is empty, returns a new tree containing x; otherwise, returns t,
     *         with x having been inserted at the appropriate position to maintain the binary
     *         search tree property
     */
    public static TreeNode insert(TreeNode t, Comparable x, TreeDisplay display)
    {
        if (t == null)
            return new TreeNode(x);

        display.visit(t);

        if (x.compareTo(t.getValue()) < 0)
            t.setLeft(insert(t.getLeft(), x, display));

        else if (x.compareTo(t.getValue()) > 0)
            t.setRight(insert(t.getRight(), x, display));

        return t;
    }

    /**
     * Deletes the specified node from the BST.
     * @param t the node to be deleted
     * @param display TreeDisplay for graphics
     * @precondition t is a binary search tree in ascending order
     * @postcondition no new TreeNodes have been created
     * @return returns a pointer to a binary search tree, in which
     *         the value at node t has been deleted
     */
    private static TreeNode deleteNode(TreeNode t, TreeDisplay display)
    {
        // node to delete is a leaf node
        if (t.getLeft() == null && t.getRight() == null)
            return null;

        // node to delete has one child (which is left child)
        if (t.getLeft() != null && t.getRight() == null)
            return t.getLeft();

        // node to delete has one child (which is right child)
        if (t.getLeft() == null && t.getRight() != null)
            return t.getRight();

        // replace this node with leftmost of right subtree
        Object replace = TreeUtil.leftmost(t.getRight());
        t.setRight(delete(t.getRight(), (Comparable) replace, display)); // delete replace
        t.setValue(replace); // assign t's value to replace's value

        return t;
    }

    /**
     * Deletes a value from the BST.
     * @param t root of the BST
     * @param x value to delete
     * @param display TreeDisplay for graphics
     * @precondition t is a binary search tree in ascending order
     * @postcondition no new TreeNodes have been created
     * @return a pointer to a binary search tree, in which the
     *         value x has been deleted (if present)
     */
    public static TreeNode delete(TreeNode t, Comparable x, TreeDisplay display)
    {
        if (t == null)
            return null;

        display.visit(t);

        if (x.compareTo(t.getValue()) == 0)
            return deleteNode(t, display);

        else if (x.compareTo(t.getValue()) < 0)
            t.setLeft(delete(t.getLeft(), x, display));

        else if (x.compareTo(t.getValue()) > 0)
            t.setRight(delete(t.getRight(), x, display));

        return t;
    }
}