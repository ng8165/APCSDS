/**
 * The TreeMap uses a BST to add an object, remove an object, or check if an object exists
 * in the TreeMap. It can return the size and a sorted String of its contents.
 * @param <K> the key for the element which the TreeMap stores
 * @param <V> the value for the element which the TreeMap stores
 * @author Nelson Gou
 * @version 12/3/2021
 */
public class MyTreeMap<K,V>
{
    /**
     * The Pair class stores a pair of key-value pairs for use in a Map.
     * @author Nelson Gou
     * @version 12/3/2021
     */
    private class Pair implements Comparable
    {
        private Comparable key;
        private Object value;

        /**
         * Constructs a Pair with key-value pairs.
         * @param key the key
         * @param value the value
         */
        public Pair(Object key, Object value)
        {
            setKey(key);
            setValue(value);
        }

        /**
         * Getter for the key.
         * @return the key
         */
        public Object getKey() {
            return key;
        }

        /**
         * Getter for the value.
         * @return the value
         */
        public Object getValue() {
            return value;
        }

        /**
         * Setter for the key.
         * @param newKey new value of the key
         */
        public void setKey(Object newKey) {
            key = (Comparable) newKey;
        }

        /**
         * Setter for the value.
         * @param newValue new value for the value
         */
        public void setValue(Object newValue) {
            value = newValue;
        }

        /**
         * Comparator function.
         * @param o another Pair
         * @return 0 if the keys are equal, -1 if this is less than other,
         * and 1 if this is greater than other
         */
        @Override
        public int compareTo(Object o)
        {
            return key.compareTo(((Pair) o).getKey());
        }

        /**
         * Generates a String of the contents of a Pair.
         * @return a String containing a key-value pair.
         */
        @Override
        public String toString()
        {
            return key + "=" + value;
        }
    }

    private TreeNode root;
    private int size;
    private TreeDisplay display;

    /**
     * Constructs a TreeSet by initializing the root of the BST to null,
     * initializing the size to 0, and creating a TreeDisplay for graphics.
     */
    public MyTreeMap()
    {
        root = null;
        size = 0;
        display = new TreeDisplay();

        //wait 1 millisecond when visiting a node
        display.setDelay(1);
    }

    /**
     * Returns the size of the TreeMap.
     * @return the number of nodes in the BST
     */
    public int size()
    {
        return size;
    }

    /**
     * Determines if a key is present in the TreeMap.
     * @param key the key to be searched for
     * @return true if the key is found; otherwise false
     */
    public boolean containsKey(Object key)
    {
        return BSTUtilities.contains(root, (Comparable) (key), display);
    }

    /**
     * Adds the element with a key and a value to the TreeMap if not present.
     * @param key the key of the element to be added
     * @param value the value of the element to be added
     * @return true if able to add (not present before); otherwise false
     */
    public V put(K key, V value)
    {
        boolean contain = containsKey(key);
        V old = null;
        if (contain)
            old = get(key);

        root = BSTUtilities.insert(root, new Pair(key, value), display);
        size++;

        if (contain)
            return old;
        else
            return null;
    }

    public V get(Object key)
    {
        TreeNode t = getNode(root, (Comparable) key);
        Pair p = (Pair) t.getValue();
        return (V) p.getValue();
    }

    private TreeNode getNode(TreeNode t, Comparable key)
    {
        if (t == null)
        {
            return null;
        }

        display.visit(t);

        if (key.compareTo(t.getValue()) < 0)
        {
            return getNode(t.getLeft(), key);
        }
        else if (key.compareTo(t.getValue()) > 0)
        {
            return getNode(t.getRight(), key);
        }

        return t;
    }

    /**
     * Removes key from the TreeMap if present.
     * @param key the object to be removed
     * @return true if able to remove (present before); otherwise false
     */
    public V remove(Object key)
    {
        if (!containsKey(key))
            return null;

        V old = get(key);

        root = BSTUtilities.delete(root, (Comparable) (key), display);
        size--;

        return old;
    }

    /**
     * Returns a sorted traversal of the TreeMap.
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