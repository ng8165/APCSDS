/**
 * The Sorter class performs sorting algorithms such as selection sort,
 * insertion sort, merge sort, and quick sort.
 * @author Nelson Gou
 * @version 2/10/22
 */
public class Sorter
{
    private SortDisplay display;
    
    /**
     * Main method. Instantiates a Sorter Instance.
     * Creates a Sorter object, but calls no methods from Sorter
     * (the SortDisplay GUI calls sort methods in Sorter).
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        Sorter sorter = new Sorter();
    }
    
    /**
    * Constructs a Sorter. Creates a new display, which controls
    * all of the sorting by means of callbacks to this class.
    */
    public Sorter()
    {
        display = new SortDisplay(this);
    }

    /**
     * Swaps the values of two indices in an array in-place.
     * @param a the array to swap in
     * @param i1 index 1
     * @param i2 index 2;
     * @precondition 0 <= i1 < a.length and 0 <= i2 < a.length
     * @postcondition the elements at i1 and i2 have been swapped
     */
    private void swap(Object[] a, int i1, int i2)
    {
        Object temp = a[i1];
        a[i1] = a[i2];
        a[i2] = temp;
    }

    /**
     * Returns the index of the lowest value in the array
     * from startIndex to array.length-1.
     * @param a array of Comparable objects
     * @param startIndex index to start search from
     * @precondition 0 <= startIndex < a.length
     * @return index of the lowest value in the array from startIndex to the end
     */
    public int indexOfMin(Comparable[] a, int startIndex)
    {
        int min = startIndex;

        for (int i=startIndex+1; i<a.length; i++)
        {
            if (a[i].compareTo(a[min]) < 0)
                min = i;
        }

        return min;
    }

    /**
     * Performs the selection sort algorithm on a given array of Comparable elements.
     * @param a an array of Comparable elements
     * @postcondition a is sorted from least to greatest afterward
     */
    public void selectionSort(Comparable[] a)
    {
        for (int i=0; i<a.length; i++)
        {
            swap(a, i, indexOfMin(a, i));
            display.update();
        }
    }

    /**
     * Shifts the element at nextIndex left until the elements
     * from 0 to nextIndex are sorted.
     * @param a the array
     * @param nextIndex the index where the element to be shifted is
     * @precondition the elements from 0 to nextIndex-1 are sorted in increasing order
     */
    public void insert(Comparable[] a, int nextIndex)
    {
        Comparable ins = a[nextIndex];
        int i = nextIndex-1;

        while (i >= 0 && a[i].compareTo(ins) > 0)
        {
            a[i+1] = a[i];
            i--;
            display.update();
        }

        a[i+1] = ins;
    }

    /**
     * Performs the insertion sort algorithm on a given array of Comparable elements.
     * @param a an array of Comparable elements
     * @postcondition a is sorted from least to greatest afterward
     */
    public void insertionSort(Comparable[] a)
    {
        for (int i=1; i<a.length; i++)
            insert(a, i);

        display.update();
    }

    /**
     * Performs the merge sort algorithm on a given array of Comparable elements.
     * @param a an array of Comparable elements
     * @postcondition a is sorted from least to greatest afterward
     */
    public void mergesort(Comparable[] a)
    {
        mergesortHelp(a, 0, a.length-1);
    }

    /**
     * Recursively splits the elements in a into two relatively-equally sized parts.
     * Then, calls merge to merge the two sorted parts into one sorted part.
     * @param a the array to be sorted
     * @param lowIndex left index for the array section
     * @param highIndex right index for the array section
     */
    private void mergesortHelp(Comparable[] a, int lowIndex, int highIndex)
    {
        if (lowIndex < highIndex)
        {
            int mid = lowIndex + (highIndex-lowIndex)/2;
            mergesortHelp(a, lowIndex, mid);
            mergesortHelp(a, mid+1, highIndex);
            merge(a, lowIndex, mid, highIndex);
        }
    }
    
    /**
     * Merges the two halves of the input array into one.
     * The method creates an array to hold the output. It then establishes
     * two pointers into the two halves of the input array. The values at the
     * pointer locations are compared, and the smallest is added to the output
     * array. The corresponding pointer is then increased by one. In the event
     * either half becomes empty, the remaining values are copied to the output
     * array.
     * @precondition a[lowIndex] to a[midIndex] is sorted in increasing order and
     *               a[midIndex + 1] to a[highIndex] is sorted in increasing order.
     * @postcondition a[lowIndex] to a[highIndex] are in increasing order.
     *
     * @param a is the input array of Comparable values
     * @param lowIndex is the index into the array a corresponding to the beginning
     *        of the first half of the array to merge
     * @param midIndex is the index of the last value in the first half of the array
     * @param highIndex is the index of the last value in the second half of the array
     */
    private void merge(Comparable[] a, int lowIndex, int midIndex, int highIndex)
    {
        Comparable[] copy = new Comparable[a.length];
        for (int i = lowIndex; i <= highIndex; i++)
            copy[i] = a[i];

        int left = lowIndex;
        int right = midIndex + 1;

        for (int i = lowIndex; i <= highIndex; i++)
        {
            if (right > highIndex ||
                    (left <= midIndex && copy[left].compareTo(copy[right]) < 0))
            {
                a[i] = copy[left];
                left++;
            }
            else
            {
                a[i] = copy[right];
                right++;
            }

            display.update();
        }
    }

    /**     
     * Performs the quick sort algorithm on a given array of Comparable elements.
     * @param a an array of Comparable elements
     * @postcondition the array is sorted in increasing order
     */
    public void quicksort(Comparable[] a)
    {
        quicksortHelp(a, 0, a.length-1);
    }

    /**
     * Quick sorting is a recursive sorting algorithm that sets a pivot point (lowIndex in
     * this case). Then, it calls partition, which rough sorts the array by putting every element
     * less than pivot to the left of pivot and every element bigger than pivot to the
     * right of pivot. Then, quicksortHelp is called on the left and right sections
     * of the pivot point. (Base Case: when the sort section is 1 element or less)
     * @param a an array of Comparable elements
     * @param lowIndex the beginning index of the section of the array
     * @param highIndex the ending index of the section of the array
     */
    private void quicksortHelp(Comparable[] a, int lowIndex, int highIndex)
    {
        if (lowIndex < highIndex)
        {
            int pivot = partition(a, lowIndex, highIndex);
            quicksortHelp(a, lowIndex, pivot);
            quicksortHelp(a, pivot+1, highIndex);
        }
    }
    
    /**
     * Returns the index of the pivot element (a[lowIndex]). The computation is performed in place.
     * After the partition, all elements on the left side of the pivot (from lowIndex)
     * are less than or equal to the pivot, and all elements on the right
     * side of the pivot (through highIndex) are greater than or equal to the pivot.
     * @param a the array to partition
     * @param lowIndex the index of the start section of the array to consider
     * @param highIndex the index of the end section of the array to consider
     * @return the index of the pivot element in array a
     */
    private int partition(Comparable[] a, int lowIndex, int highIndex)
    {
        int pivotIndex = lowIndex;
        lowIndex++;

        while (lowIndex <= highIndex)
        {
            if (a[lowIndex].compareTo(a[pivotIndex]) > 0)
            {
                swap(a, lowIndex, highIndex);
                highIndex--;
                display.update();
            }
            else
            {
                lowIndex++;
            }
        }

        swap(a, highIndex, pivotIndex);
        display.update();

        return highIndex;
    }
}