/**
 * Tests HeapUtils methods on a randomly generated heap array of size 12, with the heapSize 11
 * 
 * @author  Anu Datar
 * based on the work of Vijay B and Gwyneth C.
 * @version Dec 6, 2016
 */
public class HeapSortTester
{

    /**
     * runs a randomly generated array through HeapUtils methods to test
     * 
     * @param   args arguments from the command line
     */
    public static void main(String[] args)
    {
                
        Comparable[] arr = new Comparable[12];
        HeapDisplay unsorted = new HeapDisplay();
        System.out.println("This is the initial array ");
        System.out.println("-----------------------------------");
        System.out.print("|");
        for(int i = 1; i < 12; i++)
        {
            arr[i] = (int)(Math.random( )* 100) + 1;
            System.out.print(arr[i] + "|");
        }
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println();
        unsorted.displayHeap(arr,11);
        System.out.println("Unsorted array displayed. Now constructing heap...");
        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
            //ignore
        }
        HeapUtils.heapSort(arr, 11);
        HeapDisplay sorted = new HeapDisplay();
        sorted.displayHeap(arr, 11);
        int insert = (int)(Math.random( )* 100) + 1;
        System.out.println("Heap constructed. Inserting value " + insert + " into the array...");
        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
            //ignore
        }
        arr = HeapUtils.insert(arr, insert, 11);
        boolean inserted = false;
        for(int i = 1; i <= 12; i++)
        {
            if(arr[i].equals(insert))
                inserted = true;
        }
        if( ! inserted)
        {
            throw new RuntimeException("Item was not inserted");
        }
        HeapDisplay insertUnsorted = new HeapDisplay();
        insertUnsorted.displayHeap(arr,12);
        System.out.println(insert + " has been inserted. Now removing the root node from heap...");
        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
            //ignore
        }
        HeapUtils.remove(arr, 12);
        System.out.println("The root node has been removed. Now, heapsort will be called.");
        try
        {
            Thread.sleep(300);
        }
        catch(InterruptedException e)
        {
            //ignore
        }
        HeapUtils.heapSort(arr, 11);
        HeapDisplay insertSorted = new HeapDisplay();
        insertSorted.displayHeap(arr, 11);
        System.out.println("This is the sorted array using a max heap");
        System.out.println("-----------------------------------");
        System.out.print("|");
        for(int i = 1; i < 12; i++)
        {
            System.out.print(arr[i] + "|");
//            if(i > 1 && arr[i].compareTo(arr[i - 1]) < 0)
//            {
//                throw new RuntimeException("Items are not sorted in order from least to greatest");
//            }
        }
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println();
        System.out.println("Have a nice day"); 
        
    }
}