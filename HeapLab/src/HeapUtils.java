/**
 * The HeapUtils class contains methods that are used in heaps, such as
 * heapify, buildHeap, insert, and remove. It also includes heapSort.
 * @author Nelson Gou
 * @version 1/12/2022
 */
public class HeapUtils
{
    /**
     * Swaps the values at index i1 and i2 in heap.
     * @param heap the array to swap in
     * @param i1 an index
     * @param i2 another index
     * @precondition 0 <= i1, i2 < heap.length
     * @postcondition the objects at index i1 and index i2 have been swapped
     */
    private static void swap(Comparable[] heap, int i1, int i2)
    {
        Comparable temp = heap[i1];
        heap[i1] = heap[i2];
        heap[i2] = temp;
    }

    /**
     * Ensures that the tree rooted at index is a max heap. For each node, checks if its children
     * are bigger than it. If bigger than left and right child, stops. Otherwise, swaps with the
     * bigger child, then recursively calls heapify on the swapped child to ensure that the new
     * tree still satisfies the max heap condition.
     * Big O Analysis: O(log n)
     * @param heap the input array representing a complete binary tree
     *             containing heapSize nodes (although length is heapSize+1)
     * @param index the root of the tree that is being heapified
     *              (which may not be the root of the heap)
     * @param heapSize size of the heap (one less than heap's length)
     * @precondition 1 <= index <= heapSize, and both children are max heaps
     * @postcondition tree rooted at index satisfies the max heap condition
     */
    public static void heapify(Comparable[] heap, int index, int heapSize)
    {
        if (index <= heapSize)
        {
            int left = 2*index, right = 2*index+1;

            int greatest = index;

            if (left <= heapSize && heap[left].compareTo(heap[greatest]) > 0)
                greatest = left;

            if (right <= heapSize && heap[right].compareTo(heap[greatest]) > 0)
                greatest = right;

            if (index != greatest)
            {
                swap(heap, greatest, index);
                heapify(heap, greatest, heapSize);
            }
        }
    }

    /**
     * Builds a max heap by calling heapify from all non-leaf nodes (heapSize/2 is the last
     * non-leaf node). Calling heapify backwards ensures that all children of index are always max
     * heaps. After calling heapify on the root, the entire array satisfies the max heap condition.
     * Big O Analysis: O(n log n)
     * @param heap the input array containing heapSize+1 Comparable objects
     * @param heapSize size of the heap (one less than heap's length)
     * @precondition heapSize >= 0
     * @postcondition heap contains a complete binary tree satisfying the max heap condition
     */
    public static void buildHeap(Comparable[] heap, int heapSize)
    {
        for (int i=heapSize/2; i>0; i--)
            heapify(heap, i, heapSize);
    }

    /**
     * Removes the largest value in the heap and returns the largest value in the heap.
     * Swaps the first and last node, which moves the largest value to the end.
     * Then calls heapify on the root (the node that was originally at the end)
     * with heapSize-1 to ensure that the array still satisfies the max heap condition.
     * Returns the node at the end of the heap (the node originally at the beginning).
     * Big O Analysis: O(log n),
     * @param heap the input array representing a complete binary tree
     *             containing heapSize nodes (although length is heapSize+1)
     * @param heapSize size of the heap (one less than heap's length)
     * @return the largest value in the heap
     * @precondition heap is a non-empty array that contains a complete binary
     *               tree satisfying the max heap condition
     * @postcondition size of the heap is one smaller than before and
     *                still satisfies the max heap condition
     */
    public static Comparable remove(Comparable[] heap, int heapSize)
    {
        if (heapSize == 0)
            return null;

        swap(heap, 1, heapSize);
        heapify(heap, 1, heapSize-1);

        return heap[heapSize];
    }

    /**
     * Inserts a value into the heap.
     * Creates a new array that is one longer than heap and copies the values in the heap,
     * then inserts the new value at the end of the new array. To ensure that the array still
     * satisfies the max heap condition, performs the bubble up operation. Starting from the
     * insertion index, compares value with parent. If the parent is smaller, swaps and continues
     * the loop; if the parent is bigger, stops.
     * Big O Analysis: O(n).
     * @param heap the input array representing a complete binary tree
     *             containing heapSize nodes (although length is heapSize+1)
     * @param item item to be inserted
     * @param heapSize size of the heap
     * @precondition heap is a non-empty array that contains a complete binary
     *               tree satisfying the max heap condition
     * @return an array with length one longer than heap's length, has all values that
     *         were originally in heap but also item, and satisfies the max heap condition
     */
    public static Comparable[] insert(Comparable[] heap, Comparable item, int heapSize)
    {
        Comparable[] newHeap = new Comparable[heap.length+1];

        for (int i=1; i<=heapSize; i++)
            newHeap[i] = heap[i];

        heap = newHeap;

        int i = heapSize+1;
        heap[i] = item;
        boolean shouldContinue = true;

        while (i > 0 && shouldContinue)
        {
            int parent = i/2;

            if (parent > 0 && heap[parent].compareTo(item) < 0)
            {
                swap(heap, i, parent);
                i = parent;
            }
            else
            {
                shouldContinue = false;
            }
        }

        return newHeap;
    }

    /**
     * Sorts an array from least to greatest.
     * First, builds a heap from the array (which is not a heap) using the buildHeap function.
     * Then, calls remove for all nodes; this will move the largest object to the end of the
     * heap until the heapSize is 0, sorting the array from least to greatest.
     * Big O Analysis: O(n log n).
     * @param heap the input array containing heapSize+1 Comparable objects
     * @param heapSize size of the heap (one less than heap's length)
     * @precondition heapSize >= 0
     * @postcondition heap is sorted from least to greatest
     */
    public static void heapSort(Comparable[] heap, int heapSize)
    {
        buildHeap(heap, heapSize);

        for (int i=heapSize; i>0; i--)
            remove(heap, i);
    }
}
