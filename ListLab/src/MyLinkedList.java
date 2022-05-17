import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyLinkedList is an implementation of the LinkedList class provided by Java.
 * @author Nelson Gou
 * @version 10/11/2021
 * @param <E> Type of the LinkedList
 */
public class MyLinkedList<E>
{
    private DoubleNode first;
    private DoubleNode last;
    private int size;
    private int modCount;

    /**
     * Constructs a MyLinkedList by setting size to 0 and
     * setting the first and last DoubleNode references to null.
     */
    public MyLinkedList()
    {
        first = null;
        last = null;
        size = 0;
        modCount = 0;
    }

    /**
     * Returns a string of comma separated values in the LinkedList.
     * @return String version of the LinkedList
     */
    public String toString()
    {
        DoubleNode node = first;
        if (node == null)
            return "[]";
        String s = "[";
        while (node.getNext() != null)
        {
            s += node.getValue() + ", ";
            node = node.getNext();
        }
        return s + node.getValue() + "]";
    }

    /**
     * Retrieves the element at the specified index from the beginning.
     * @param index the specified index
     * @return the DoubleNode at the specified index
     * @precondition  0 <= index <= size / 2
     * @postcondition starting from first, returns the node
     *                with given index (where index 0
     *                returns first)
     */
    private DoubleNode getNodeFromFirst(int index)
    {
        DoubleNode curr = first;

        while (index > 0)
        {
            curr = curr.getNext();
            index--;
        }

        return curr;
    }

    /**
     * Retrieves the element at the specified index from the end.
     * @param index the specified index
     * @return the DoubleNode at the specified index
     * @precondition  size / 2 <= index < size
     * @postcondition starting from last, returns the node
     *                with given index (where index size-1
     *                returns last)
     */
    private DoubleNode getNodeFromLast(int index)
    {
        DoubleNode curr = last;

        while (index < size-1)
        {
            curr = curr.getPrevious();
            index++;
        }

        return curr;
    }

    /**
     * Retrieves the DoubleNode at the specified index.
     * @param index the specified index
     * @return the DoubleNode at the specified index
     * @precondition  0 <= index < size
     * @postcondition starting from first or last (whichever
     *                is closer), returns the node with given
     *                index
     */
    private DoubleNode getNode(int index)
    {
        if (index <= size/2)
        {
            return getNodeFromFirst(index);
        }
        else
        {
            return getNodeFromLast(index);
        }
    }

    /**
     * Returns the size of the LinkedList.
     * @return the size of the LinkedList
     */
    public int size()
    {
        return size;
    }

    /**
     * Returns the object stored by the DoubleNode at the specified index.
     * @param index the specified index
     * @precondition index must be less than the size of the LinkedList
     * @return the object stored by the DoubleNode at the specified index
     */
    public E get(int index)
    {
        return (E) getNode(index).getValue();
    }

    /**
     * Sets the element at the specified index to obj.
     * @param index the specified index
     * @param obj the value of the obj
     * @precondition index must be less than the size of the LinkedList
     * @postcondition replaces the element at position index with obj
     *                returns the element formerly at the specified position
     * @return previous value of the element
     */
    public E set(int index, E obj)
    {
        E old = get(index);
        getNode(index).setValue(obj);
        modCount++;
        return old;
    }

    /**
     * Removes the element at the specified index.
     * @param index the specified index
     * @precondition index must be less than the size of the LinkedList
     * @postcondition removes element from position index, moving elements
     *                at position index + 1 and higher to the left
     *                (subtracts 1 from their indices) and adjusts size
     *                returns the element formerly at the specified position
     * @return value of the element prior to removal
     */
    public E remove(int index)
    {
        DoubleNode remove = getNode(index);
        E val = (E) remove.getValue();

        if (size == 1)
        {
            first = null;
            last = null;
        }
        else if (index == 0)
        {
            first = first.getNext();
            first.setPrevious(null);
        }
        else if (index == size-1)
        {
            last = last.getPrevious();
            last.setNext(null);
        }
        else
        {
            DoubleNode prev = remove.getPrevious();
            DoubleNode next = remove.getNext();

            prev.setNext(next);
            next.setPrevious(prev);
        }

        size--;
        modCount++;

        return val;
    }

    /**
     * Removes the first element in the LinkedList.
     * @postcondition first has been modified to start at the next one and size has been decremented
     * @return the value of the first element in the LinkedList before removal
     */
    public E removeFirst()
    {
        return remove(0);
    }

    /**
     * Removes the last element in the LinkedList.
     * @postcondition last has been modified to start at the previous one and
     *                size has been decremented
     * @return the value of the last element in the LinkedList before removal
     */
    public E removeLast()
    {
        return remove(size-1);
    }

    /**
     * Adds an element at the specified index with value obj.
     * @param index the specified index
     * @param obj the value of the element
     * @precondition  0 <= index <= size
     * @postcondition inserts obj at position index,
     *                moving elements at position index and higher
     *                to the right (adds 1 to their indices) and adjusts size
     */
    public void add(int index, E obj)
    {
        DoubleNode insert = new DoubleNode(obj);

        if (size == 0)
        {
            first = insert;
            last = insert;
        }
        else if (index == 0)
        {
            insert.setNext(first);
            first.setPrevious(insert);
            first = insert;
        }
        else if (index == size)
        {
            last.setNext(insert);
            insert.setPrevious(last);
            last = insert;
        }
        else
        {
            DoubleNode next = getNode(index);
            DoubleNode prev = next.getPrevious();

            insert.setPrevious(prev);
            insert.setNext(next);
            next.setPrevious(insert);
            prev.setNext(insert);
        }

        size++;
        modCount++;
    }

    /**
     * Adds an element at the beginning of the LinkedList.
     * @param obj the value of the element
     * @postcondition inserts obj at the front of the LinkedList
     */
    public void addFirst(E obj)
    {
        add(0, obj);
    }

    /**
     * Adds an element at the end of the LinkedList.
     * @param obj the value of the element
     * @postcondition inserts obj at the end of the LinkedList
     */
    public void addLast(E obj)
    {
        add(size, obj);
    }

    /**
     * Adds an element to the end of the LinkedList.
     * @param obj obj to be appended
     * @postcondition appends obj to end of list
     * @return true
     */
    public boolean add(E obj)
    {
        add(size, obj);
        return true;
    }

    /**
     * Returns the value of the first element in the LinkedList.
     * @precondition first must not be null
     * @return the value of the first element in the LinkedList
     */
    public E getFirst()
    {
        return (E) first.getValue();
    }

    /**
     * Returns the value of the last element in the LinkedList.
     * @precondition last must not be null
     * @return the value of the last element in the LinkedList
     */
    public E getLast()
    {
        return (E) last.getValue();
    }

    /**
     * Returns an Iterator for the LinkedList.
     * @return an Iterator for the LinkedList
     */
    public Iterator<E> iterator()
    {
        return new MyLinkedListIterator();
    }

    /**
     * The MyLinkedListIterator class is an Iterator for a MyLinkedList.
     * @author Nelson Gou
     * @version 10/11/2021
     */
    private class MyLinkedListIterator implements Iterator<E>
    {
        private DoubleNode nextNode;

        /**
         * Constructs a MyLinkedListIterator with the nextNode set to first.
         */
        public MyLinkedListIterator()
        {
            nextNode = first;
            modCount = 0;
        }

        /**
         * Determines if the ArrayList was modified without using the Iterator.
         * Called whenever an Iterator method is used.
         * @throws ConcurrentModificationException if ArrayList was modified without
         *                                         using Iterator methods
         */
        private void checkForModException()
        {
            if (modCount > 0)
            {
                throw new ConcurrentModificationException("Modified LinkedList without" +
                        " using Iterator methods");
            }
        }

        /**
         * Determines if the iterator has a valid next element (if it has reached the end or not).
         * @return true if there is a next element; otherwise false
         */
        public boolean hasNext()
        {
            checkForModException();
            return nextNode != null;
        }

        /**
         * Returns the value of the next element.
         * @postcondition the Iterator moves forward by one element
         * @return next Object in the LinkedList
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next()
        {
            checkForModException();

            if (!hasNext())
            {
                throw new NoSuchElementException("Iteration has no more elements");
            }

            E val = (E) nextNode.getValue();
            nextNode = nextNode.getNext();
            return val;
        }

        /**
         * Removes the element that was previously returned by next().
         * @postcondition removes the last element that was returned by next and decrements size
         * @throws IllegalStateException if the next method has not yet been called
         */
        public void remove()
        {
            checkForModException();

            if (nextNode == first)
            {
                throw new IllegalStateException("next method has not yet been called");
            }
            else if (nextNode == null)
            {
                MyLinkedList.this.removeLast();
                modCount--; // un-increment from remove call from Iterator
            }

            DoubleNode remove = nextNode.getPrevious();

            if (remove.getPrevious() == null)
            {
                MyLinkedList.this.removeFirst();
                modCount--; // un-increment from remove call from Iterator
            }
            else
            {
                DoubleNode prev = remove.getPrevious();
                prev.setNext(nextNode);
                nextNode.setPrevious(prev);
                size--;
            }
        }
    }
}