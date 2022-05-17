import java.util.Iterator;
import java.util.Stack;

/**
 * The TreeSet uses a BST to add an object, remove an object, or check if an object exists
 * in the TreeSet. It can return the size and a sorted String of its contents.
 * @param <E> the object which the TreeSet stores
 * @author Nelson Gou
 * @version 1/19/2022
 */
public class MyTreeSet<E>
{
    private TreeNode root;
    private int size;
    private TreeDisplay display;

    /**
     * Constructs a TreeSet by initializing the root of the BST to null,
     * initializing the size to 0, and creating a TreeDisplay for graphics.
     */
    public MyTreeSet()
    {
        root = null;
        size = 0;

        display = new TreeDisplay();

        // wait 1 millisecond when visiting a node
        display.setDelay(1);
    }

    /**
     * Returns the size of the TreeSet.
     * @return the number of nodes in the BST
     */
    public int size()
    {
        return size;
    }

    /**
     * Determines if an object is present in the TreeSet
     * @param obj the object to be searched for
     * @return true if the obj is found; otherwise false
     */
    public boolean contains(Object obj)
    {
        return BSTUtilities.contains(root, (Comparable) (obj), display);
    }

    /**
     * Adds obj to the TreeSet if not present.
     * @param obj the object to be added
     * @return true if able to add (not present before); otherwise false
     */
    public boolean add(E obj)
    {
        boolean ret = !contains(obj);
        root = BSTUtilities.insert(root, (Comparable) (obj), display);
        if (ret) size++;
        return ret;
    }

    /**
     * Removes obj from the TreeSet if present.
     * @param obj the object to be removed
     * @return true if able to remove (present before); otherwise false
     */
    public boolean remove(Object obj)
    {
        boolean ret = contains(obj);
        root = BSTUtilities.delete(root, (Comparable) (obj), display);
        if (ret) size--;
        return ret;
    }

    /**
     * Returns a sorted traversal of the TreeSet.
     * @return a String containing the elements of the TreeSet sorted
     */
    public String toString()
    {
        return toString(root);
    }

    /**
     * Returns an inorder traversal of the tree rooted at the given node.
     * @param t root of the BST
     * @return a String containing the inorder traversal
     */
    private String toString(TreeNode t)
    {
        if (t == null)
            return " ";
        return toString(t.getLeft()) + t.getValue() + toString(t.getRight());
    }
}