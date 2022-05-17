import java.util.Scanner;

/**
 * The Main class tests HeapUtils with a randomly generated array of 11 integers.
 * @author Nelson Gou
 * @version 1/12/2022
 */
public class Main
{
    /**
     * Prints an array of Comparable objects starting from index 1 to the end of the array.
     * @param arr the array of Comparable objects to be printed
     */
    private static void printArray(Comparable[] arr)
    {
        System.out.print("[");

        for (int i=1; i<arr.length-1; i++)
        {
            System.out.print(arr[i] + ", ");
        }

        System.out.println(arr[arr.length-1] + "]");
    }

    /**
     * Generates an array of 11 random integers (from index 1 to 11).
     * @return an array of 11 random integers (1-100)
     */
    private static Comparable[] generateRandArray()
    {
        Comparable[] arr = new Comparable[12];

        for (int i=1; i<12; i++)
            arr[i] = (int) (Math.random()*99) + 1;

        return arr;
    }

    /**
     * Main method. Tests HeapUtils methods (heapSort, insert, remove) with a randomly
     * generated array of 11 integers.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        HeapDisplay display = new HeapDisplay();
        Scanner sc = new Scanner(System.in);
        Comparable[] arr = generateRandArray();

        // heap sort testing
        System.out.println("Testing heapSort:");

        System.out.print("Unsorted: ");
        printArray(arr);

        HeapUtils.heapSort(arr, 11);

        System.out.print("Sorted: ");
        printArray(arr);

        // generating a heap
        System.out.println("\nThe following steps will require the user to press \"enter\" on " +
                "the keyboard to continue in order to let the user see the heap after operations.");

        System.out.print("\nGenerating a heap. ");

        arr = generateRandArray();
        HeapUtils.buildHeap(arr, 11);
        display.displayHeap(arr, 11);
        sc.nextLine();

        // testing insert
        int insert = (int) (Math.random()*8) + 1;
        System.out.println("\nTesting HeapUtils insert:");
        System.out.println("Inserting " + insert + " numbers:");

        for (int i=0; i<insert; i++)
        {
            int rand = (int) (Math.random() * 100) + 1;
            arr = HeapUtils.insert(arr, rand, arr.length - 1);
            System.out.print(rand + " ");
            sc.nextLine();
            display.displayHeap(arr, arr.length-1);
        }

        // testing remove
        int remove = (int) (Math.random()*8) + 1;
        System.out.println("\nTesting HeapUtils remove:");
        System.out.println("Removing " + remove + " numbers:");

        for (int i=0, heapSize = arr.length-1; i<remove; i++)
        {
            System.out.print(arr[1] + " ");
            sc.nextLine();
            HeapUtils.remove(arr, heapSize--);
            display.displayHeap(arr, heapSize);
        }

        System.out.println("\n\nEnd of testing.\nClose HeapDisplay windows to end the program.");
    }
}
