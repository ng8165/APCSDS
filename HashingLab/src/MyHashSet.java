import java.util.*;

/**
 * The MyHashSet<E> class imitates Java's built-in HashSet class. It uses hashing
 * to create a set that de-dupes and runs all operations in O(1) time.
 * @param <E> the type of each element
 * @author Nelson Gou
 * @version 1/19/2022
 */
public class MyHashSet<E>
{
    private static final int NUM_BUCKETS = 5;
    private LinkedList<E>[] buckets;
    private int size;

    /**
     * Constructs a MyHashSet.
     * Initializes all buckets as LinkedLists and sets size to 0.
     */
    public MyHashSet()
    {
        buckets = new LinkedList[NUM_BUCKETS];
        for (int i=0; i<NUM_BUCKETS; i++)
            buckets[i] = new LinkedList<E>();

        size = 0;
    }

    /**
     * Helper function that determines the index of the bucket where obj should be found.
     * @param obj an Object to find the bucket for
     * @return index of the bucket where obj should be found
     */
    private int toBucketIndex(Object obj)
    {
        return Math.abs(obj.hashCode()) % NUM_BUCKETS;
    }

    /**
     * Returns the size of the MyHashSet (how many Objects inside the all the buckets).
     * @return the size of the MyHashSet
     */
    public int size()
    {
        return size;
    }

    /**
     * Determines if a given Object is contained inside one of the buckets of the MyHashSet.
     * @param obj the Object to test if it is contained
     * @return true if it is contained; otherwise false
     */
    public boolean contains(Object obj)
    {
        return buckets[toBucketIndex(obj)].contains(obj);
    }

    /**
     * Adds a specified object into one of the buckets of the MyHashSet if not already present.
     * @param obj the specified object to add
     * @return false if obj is already present inside the MyHashSet; otherwise true
     */
    public boolean add(E obj)
    {
        if (contains(obj))
            return false;

        buckets[toBucketIndex(obj)].add(obj);
        size++;
        return true;
    }

    /**
     * Removes a specified object from one of the buckets of the MyHashSet if already present.
     * @param obj the specified object to remove
     * @return true if obj is already present inside the MyHashSet; otherwise false
     */
    public boolean remove(Object obj)
    {
        ListIterator<E> it = buckets[toBucketIndex(obj)].listIterator();

        while (it.hasNext())
        {
            if (it.next().equals(obj))
            {
                it.remove();
                size--;
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a String version of the MyHashSet.
     * @return a String version of the MyHashSet
     */
    @Override
    public String toString()
    {
        String s = "";
        for (int i = 0; i < buckets.length; i++)
            if (buckets[i].size() > 0)
                s += i + ":" + buckets[i] + " ";
        return s;
    }

    /**
     * Returns an iterator for this MyHashSet.
     * @return an iterator for this MyHashSet
     */
    public Iterator<E> iterator()
    {
        return new MyHashSetIterator();
    }

    /**
     * The MyHashSetIterator is an iterator for a MyHashSet.
     * @author Nelson Gou
     * @version 1/31/2022
     */
    private class MyHashSetIterator implements Iterator<E>
    {
        private int bucketIndex;
        private ListIterator<E> it;
        private int elem;

        /**
         * Constructs a MyHashSetIterator.
         * Initializes the bucketIndex to 0 and sets the iterator to null.
         */
        public MyHashSetIterator()
        {
            bucketIndex = 0;
            elem = 0;
            it = buckets[bucketIndex].listIterator();
        }

        /**
         * Determines if the iteration has more elements.
         * @return true if the iteration has more elements; otherwise false
         */
        public boolean hasNext()
        {
            return elem < size;
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException("the iteration has no more elements");

            if (!it.hasNext())
            {
                bucketIndex++;

                while (buckets[bucketIndex].isEmpty())
                    bucketIndex++;

                it = buckets[bucketIndex].listIterator();
            }

            elem++;

            return it.next();
        }

        /**
         * Removes the value last returned by next().
         * @throws IllegalStateException if the next method has not yet been called, or the remove
         *                               method has already been called after the last call to
         *                               the next method
         */
        public void remove()
        {
            it.remove();
        }
    }
}