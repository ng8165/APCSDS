import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * MyArrayList is an implementation of the ArrayList class provided by Java.
 * @author Nelson Gou
 * @version 10/11/2021
 * @param <E> Type of the ArrayList
 */
public class MyArrayList<E>
{
    private int size;
    private Object[] values;
    private int modCount;

    /**
     * Constructs a MyArrayList by setting size to 0 and creating an array of size 1.
     */
    public MyArrayList()
    {
        size = 0;
        values = new Object[1];
        modCount = 0;
    }

    /**
     * Returns a string of comma separated values in the ArrayList.
     * @return String version of the ArrayList
     */
    @Override
    public String toString()
    {
        if (size == 0)
            return "[]";

        String s = "[";
        for (int i = 0; i < size - 1; i++)
            s += values[i] + ", ";
        return s + values[size - 1] + "]";
    }

    /**
     * Doubles the capacity of the array.
     * @postcondition replaces the array with one that is
     *                twice as long, and copies all the
     *                old elements into it
     */
    private void doubleCapacity()
    {
        Object[] newValues = new Object[values.length*2];

        for (int i=0; i<values.length; i++)
        {
            newValues[i] = values[i];
        }

        values = newValues;
    }

    /**
     * Returns the capacity of the internal array (not size).
     * @return the length of the array
     */
    public int getCapacity()
    {
        return values.length;
    }

    /**
     * Returns the size of the ArrayList (not array capacity).
     * @return size of the ArrayList
     */
    public int size()
    {
        return size;
    }

    /**
     * Returns the value at the specified index.
     * @precondition index must be less than the size of the ArrayList
     * @param index requested index to retrieve
     * @return the object located at index of the ArrayList
     */
    public E get(int index)
    {
        if (index >= size)
        {
            throw new ArrayIndexOutOfBoundsException("index is too big");
        }

        return (E) values[index];
    }

    /**
     * Sets the value at the specified index to obj.
     * @precondition index must be less than the size of the ArrayList
     * @postcondition replaces the element at position index with obj
     *                returns the element formerly at the specified position
     * @param index the index to set the value to
     * @param obj the object to set the element to
     * @return the value of the element before setting the new value
     */
    public E set(int index, E obj)
    {
        E old = (E) values[index];
        values[index] = obj;
        modCount++;
        return old;
    }

    /**
     * Adds an element with value obj to the end of the ArrayList.
     * @postcondition appends obj to end of list, increments size; returns true
     * @param obj value of the element to be added
     * @return true
     */
    public boolean add(E obj)
    {
        add(size, obj);
        return true;
    }

    /**
     * Adds an element with value obj at the specified index and moves
     * elements after it forward.
     * @param index insertion index
     * @param obj value of the element to be inserted
     * @precondition  0 <= index <= size
     * @postcondition inserts obj at position index,
     *                moving elements at position index and higher
     *                to the right (adds 1 to their indices) and adjusts size
     */
    public void add(int index, E obj)
    {
        if (size >= getCapacity())
        {
            doubleCapacity();
        }

        for (int i=size-1; i>=index; i--)
        {
            values[i+1] = values[i];
        }

        set(index, obj);
        size++;
        modCount++;
    }

    /**
     * Removes the element at the specified index and returns the value of
     * the element prior to removal.
     * @param index index of the element to be removed
     * @precondition index must be less than the size of the ArrayList
     * @postcondition removes element from position index, moving elements
     *                at position index + 1 and higher to the left
     *                (subtracts 1 from their indices) and adjusts size
     *                returns the element formerly at the specified position
     * @return value of the element prior to removal
     */
    public E remove(int index)
    {
        E old = (E) values[index];

        size--;
        modCount++;

        for (int i=index; i<size; i++)
        {
            values[i] = values[i+1];
        }

        return old;
    }

    /**
     * Returns a new Iterator for MyArrayList.
     * @return a MyArrayListIterator
     */
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }

    /**
     * Returns a new ListIterator for MyArrayList
     * @return a MyArrayListListIterator
     */
    public ListIterator<E> listIterator()
    {
        return new MyArrayListListIterator();
    }

    /**
     * The MyArrayListIterator class is an Iterator for a MyArrayList.
     * @author Nelson Gou
     * @version 10/11/2021
     */
    private class MyArrayListIterator implements Iterator<E>
    {
        private int nextIndex;

        /**
         * Constructs a MyArrayListIterator with the nextIndex set to 0.
         */
        public MyArrayListIterator()
        {
            nextIndex = 0;
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
                throw new ConcurrentModificationException("Modified ArrayList without" +
                                                          "using Iterator methods");
            }
        }

        /**
         * Determines if the iterator has a valid next element (if it has reached the end or not).
         * @return true if there is a next element; otherwise false
         */
        public boolean hasNext()
        {
            checkForModException();
            return nextIndex < size;
        }

        /**
         * Returns the value of the next element.
         * @postcondition the Iterator moves forward by one element
         * @return next Object in the ArrayList
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next()
        {
            checkForModException();

            if (!hasNext())
            {
                throw new NoSuchElementException("Iteration has no more elements");
            }

            E val = get(nextIndex);
            nextIndex++;
            return val;
        }

        /**
         * Removes the element that was previously returned by next().
         * @postcondition removes the last element that was returned by next
         * @throws IllegalStateException if the next method has not yet been called
         */
        public void remove()
        {
            checkForModException();

            if (nextIndex == 0)
            {
                throw new IllegalStateException("next method has not yet been called");
            }

            MyArrayList.this.remove(nextIndex-1);
            modCount--; // un-increment from remove call by iterator
        }
    }

    /**
     * The MyArrayListListIterator class is an ListIterator for a MyArrayList.
     * @author Nelson Gou
     * @version 10/12/2021
     */
    private class MyArrayListListIterator extends MyArrayListIterator implements ListIterator<E>
    {
        private int nextIndex;
        private int previousIndex;
        private boolean forward;

        /**
         * Constructs a new MyArrayListListIterator.
         */
        public MyArrayListListIterator()
        {
            nextIndex = 0;
            previousIndex = -1;
            forward = true;
            modCount = 0;
        }

        /**
         * Determines if the ArrayList was modified without using the ListIterator.
         * Called whenever an ListIterator method is used.
         * @throws ConcurrentModificationException if ArrayList was modified without
         *                                         using ListIterator methods
         */
        private void checkForModException()
        {
            if (modCount > 0)
            {
                throw new ConcurrentModificationException("Modified ArrayList without" +
                        " using ListIterator methods");
            }
        }

        /**
         * Returns next element and moves pointer forward.
         * @postcondition the ListIterator moves forward by one element
         * @return next Object in MyArrayList
         * @throws NoSuchElementException if the iteration has no next element
         */
        @Override
        public E next()
        {
            checkForModException();

            if (!hasNext())
            {
                throw new NoSuchElementException("Iteration has no next elements");
            }

            forward = true;
            previousIndex++;
            nextIndex++;
            return super.next();
        }

        /**
         * Adds element before element that would be returned by next.
         * @param obj element to add
         * @postcondition inserts an element before nextIndex and after previousIndex
         */
        public void add(E obj)
        {
            checkForModException();

            MyArrayList.this.add(nextIndex, obj);
            previousIndex++;
            nextIndex++;

            modCount--; // un-increment from add call by ListIterator
        }

        /**
         * Determines whether there is another element in MyArrayList
         * while traversing in the backward direction.
         * @return true if there is another element, false otherwise
         */
        public boolean hasPrevious()
        {
            checkForModException();
            return previousIndex >= 0;
        }

        /**
         * Returns the value of the previous element.
         * @postcondition the ListIterator moves backwards by one element
         * @return previous Object in ArrayList
         * @throws NoSuchElementException if the iteration has no previous element
         */
        public E previous()
        {
            checkForModException();

            if (!hasPrevious())
            {
                throw new NoSuchElementException("Iteration has no previous element");
            }

            forward = false;
            E val = get(previousIndex);
            previousIndex--;
            nextIndex--;
            modCount--; // un-increment from remove call by iterator

            return val;
        }

        /**
         * Returns index of the next element.
         * @return index of element that would be
         *         returned by a call to next()
         */
        public int nextIndex()
        {
            checkForModException();
            return nextIndex;
        }

        /**
         * Returns index of the previous element.
         * @return index of element that would be
         *         returned by a call to previous()
         */
        public int previousIndex()
        {
            checkForModException();
            return previousIndex;
        }

        /**
         * Removes element that was returned by next() or previous().
         * @postcondition removes the last returned element from the MyArrayList
         * @throws IllegalStateException if neither next nor previous have been called
         */
        @Override
        public void remove()
        {
            checkForModException();

            if (nextIndex <= 0 || previousIndex >= size)
            {
                throw new IllegalStateException("neither next nor previous have been called");
            }

            if (forward)
            {
                super.remove();
            }
            else
            {
                MyArrayList.this.remove(previousIndex+1);
            }

            modCount--; // un-increment from remove call by ListIterator
        }

        /**
         * Sets of the value that was returned by next() or previous().
         * @param obj new value for the element
         * @postcondition sets the value of the previously returned element
         * @throws IllegalStateException if neither next nor previous have been called
         */
        public void set(E obj)
        {
            checkForModException();

            if (nextIndex == 0 || previousIndex >= size)
            {
                throw new IllegalStateException("neither next nor previous have been called");
            }

            if (forward)
            {
                MyArrayList.this.set(nextIndex-1, obj);
            }
            else
            {
                MyArrayList.this.set(previousIndex+1, obj);
            }

            modCount--; // un-increment from remove call by iterator
        }
    }
}