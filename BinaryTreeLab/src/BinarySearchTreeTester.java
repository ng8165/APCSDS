import java.util.Scanner;

/**
 * Tests a Binary Search Tree.
 * 
 * @author Anu Datar with inputs from Sorjo Baneerjee (class of 2016)
 * @author Nelson Gou: reformatted code and prompts to user and added contains method testing
 * @version 12/9/2021
 */
public class BinarySearchTreeTester extends BinaryTreeTester
{
    private Scanner sc;
    private TreeDisplay display;
    private TreeNode t;

    /**
     * Constructs a BinarySearchTreeTester. Instantiates a Scanner, TreeDisplay,
     * and builds a BST to test BSTUtilities with.
     */
    public BinarySearchTreeTester()
    {
        display = new TreeDisplay();
        display.setTester(this); // to get the display to send back the values when it visits a node
        sc = new Scanner(System.in);
        t = null;

        // build BST
        Comparable[] vals = {25, 20, 10, 22, 5, 12, 1, 8, 15, 36, 30, 40, 28, 38, 48, 45, 50};

        for (Comparable val: vals)
            t = insert(t, val);

        System.out.println();
        display.displayTree(t);
    }

    /**
     * Inserts a value inside the BST. Implemented without TreeDisplay to speed up initialization.
     * @param x value to insert
     * @precondition t is a binary search tree in ascending order
     * @postcondition x is ignored if it is a duplicate of an element already in t;
     *                only one new TreeNode is created in the course of the traversal
     * @return if t is empty, returns a new tree containing x; otherwise, returns t,
     *         with x having been inserted at the appropriate position to maintain the binary
     *         search tree property
     */
    private TreeNode insert(TreeNode node, Comparable x)
    {
        if (node == null)
            return new TreeNode(x);

        if (x.compareTo(node.getValue()) < 0)
            node.setLeft(insert(node.getLeft(), x));

        else if (x.compareTo(node.getValue()) > 0)
            node.setRight(insert(node.getRight(), x));

        return node;
    }

    /**
     * Prompts the user to continue or not until they answer "y" or "n".
     * @return true if the user last entered "y"; otherwise false
     */
    private boolean prompt()
    {
        while (true)
        {
            System.out.print("\nEnter \"y\" to continue or \"n\" to stop: ");
            String response = sc.next();

            if (response.equalsIgnoreCase("y"))
                return true;

            else if (response.equalsIgnoreCase("n"))
                return false;

            else
                System.out.print("Invalid input, try again.");
        }
    }

    /**
     * Tests the methods in the BSTUtilities class: insert, delete, and contains.
     */
    private void testBSTUtilites()
    {
        // test insert method
        System.out.println("\nTesting insert:");

        while (prompt())
        {
            System.out.print("Enter a number to insert: ");
            int insert = Integer.parseInt(sc.next());

            System.out.println("Inserting " + insert);
            t = BSTUtilities.insert(t, insert, display);
            display.displayTree(t);
        }

        // test contains method
        System.out.println("\nTesting contains: ");

        while (prompt())
        {
            System.out.print("Enter a number to check if it is contained in the BST: ");
            int num = Integer.parseInt(sc.next());

            System.out.print("The BST contains " + num + ": ");
            System.out.println(BSTUtilities.contains(t, num, display));
            display.displayTree(t);
        }

        // test delete method
        System.out.println("\nTesting delete:");

        while (prompt())
        {
            System.out.print("Enter a number to delete: ");
            int delete = Integer.parseInt(sc.next());

            System.out.println("Deleting " + delete);
            t = BSTUtilities.delete(t, delete, display);
            display.displayTree(t);
        }
    }

    /**
     * Called by the display object to send back the node value
     * when a node is visited.
     * @param value the node value
     */
    public void sendValue(Object value)
    {
        // System.out.println(value);
    }

    /**
     * Main method.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Binary Search Tree Tester.");
        System.out.println("You can test your insert, delete, and contains methods.");

        BinarySearchTreeTester tester = new BinarySearchTreeTester();
        tester.testBSTUtilites();

        System.out.println("\nThank you!");
        System.exit(0);
    }
}