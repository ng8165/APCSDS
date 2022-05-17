import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Tests the TreeUtil class.
 * 
 * @author Anu Datar : added fileTest method on 11/25/2018
 * @author Nelson Gou: rewrote various methods with comments for Checkstyle, changed method calls
 *                      to static calls, and added Twenty Questions test option on 12/2/2021
 *
 * @author Nathan Dalal
 * @version 11/13/13
 */
public class BinaryTreeTester
{
    /**
     * Tests the basic binary tree methods of TreeUtil.
     */
    private void basicTest()
    {
        System.out.println();

        // setup
        int randomDepth = 3; // (int) (Math.random()*5);
        TreeNode t = TreeUtil.createRandom(randomDepth);

        TreeDisplay display = new TreeDisplay();
        display.setTester(this);
        display.displayTree(t);

        // maxDepth test
        if (TreeUtil.maxDepth(t) == randomDepth)
            System.out.println("maxDepth works");
        else
            System.out.println("maxDepth doesn't work");

        // sameShape test
        TreeNode copyTree = t;
        if (TreeUtil.sameShape(copyTree, t))
            System.out.println("sameShape works");
        else
            System.out.println("sameShape doesn't work");

        // copyTree test
        copyTree = TreeUtil.copy(t);
        if (TreeUtil.sameShape(copyTree, t))
            System.out.println("copyTree works");
        else
            System.out.println("copyTree doesn't work");

        // leftmost, rightmost, countNodes, countLeaves tests
        System.out.println("Leftmost node from root is " + TreeUtil.leftmost(t));
        System.out.println("Rightmost node from root is " + TreeUtil.rightmost(t));
        System.out.println("Total number of nodes is " + TreeUtil.countNodes(t));
        System.out.println("Total number of leaves is " + TreeUtil.countLeaves(t));

        // preOrder test
        System.out.println("\n\nDoing Pre Order");
        TreeUtil.preOrder(t, display);

        // fillList test
        List<String> list = new ArrayList<String>();
        TreeUtil.fillList(t, list);
        System.out.println("\n\nfillList works if below numbers match Pre Order");
        for (String s: list)
            System.out.print(s + " ");

        // inOrder test
        System.out.println("\n\nDoing In Order");
        TreeUtil.inOrder(t, display);

        // postOrder test
        System.out.println("\n\nDoing Post Order");
        TreeUtil.postOrder(t, display);

        System.out.println("\n\nCheck with the TreeDisplay to check if all of your methods work.");
    }

    /**
     * Tests the morse code methods of the TreeUtil class.
     */
    private void morseTest()
    {
        System.out.println();

        TreeDisplay display = new TreeDisplay();
        display.setTester(this);

        // createDecodingTree
        System.out.println("Generating decodingTree");
        TreeNode decodingTree = TreeUtil.createDecodingTree(display);
        System.out.println ("\nLooks like we know our ABC's!\n");

        // test decodeMorse
        String[] words = {"sos", "amerikuh", "christmas"};
        String[] cipher = {"... --- ...", ".- -- . .-. .. -.- ..- ....",
                "-.-. .... .-. .. ... - -- .- ..."};

        for (int i=0; i<words.length; i++)
        {
            System.out.println("Let's decode " + cipher[i]);
            String decoded = TreeUtil.decodeMorse(decodingTree, cipher[i], display);
            System.out.println("\nYour output: \"" + decoded + "\"");
            System.out.println("Correct: \"" + words[i] + "\"\n");
        }

        System.out.println ("\nCheck with the tree to see that all of your methods work.\n");

        userMorse(decodingTree, display);
    }

    /**
     * Allows the user to test their own code with user-inputted morse code.
     * @param decodingTree the decoding tree in the display window
     * @param display the TreeDisplay object that needs to be passed
     */
    private void userMorse(TreeNode decodingTree, TreeDisplay display)
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter \"y\" to decode your own morse!" +
                "\nEnter \"n\" to end the morse code test.");
        String response = in.nextLine();

        while (response.equals("y"))
        {
            System.out.println("\nType valid morse code. The tester will break if code" +
                    "is not valid.\nAlso do not add spaces after the final morse code segment." +
                    "\n\nLet's try your own Morse Code!\nPlace your code below:");
            String code = in.nextLine();

            System.out.println ("Let's decode " + code);
            String userDecoded = TreeUtil.decodeMorse (decodingTree, code, display);
            System.out.println ("\nOutput: " + userDecoded + "\n");

            System.out.println ("Enter \"y\" to enter a new morse code.\n" +
                    "Enter \"n\" to end the user-inputted morse code sequence.");
            response = in.nextLine();
        }

        System.out.println ("\nHope you had fun!");
    }

    /**
     * Tests the TreeUtil methods to save a Binary Tree to file and reconstruct it
     * by reading back the contents.
     */
    private void fileTest()
    {
        System.out.println();

        // setup
        boolean problem = false;
        int randomDepth = 3; // (int) (Math.random()*5);
        TreeNode t = TreeUtil.createRandom(randomDepth);

        TreeDisplay initial = new TreeDisplay();
        initial.setTester(this);
        initial.displayTree(t);
        TreeDisplay copyDisplay = new TreeDisplay();        

        // saveTree
        System.out.println("Writing to file...");
        TreeUtil.saveTree("tree.txt", t);

        // loadTree
        System.out.println ("Reading back and reconstructing tree from file...");
        TreeNode copyTree = TreeUtil.loadTree("tree.txt");

        copyDisplay.displayTree(copyTree);
        System.out.println ("Done. Checking for success");

        // wait 3 seconds
        int count = 0;
        try
        {
            while (count<3)
            {
                Thread.sleep(500);
                System.out.print(".");
                count++;
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        if (!TreeUtil.sameShape(copyTree, t))
            problem = true;

        System.out.println("\n\nDoing PreOrder on both trees");

        // generate list for original tree
        TreeUtil.preOrder(t, initial);
        List<String> list = new ArrayList<String>();
        TreeUtil.fillList(t, list);

        System.out.println();

        // generate list for file-written tree
        copyDisplay.setTester(this);
        TreeUtil.preOrder(copyTree, copyDisplay);
        List<String> list2 = new ArrayList<String>();
        TreeUtil.fillList(copyTree, list2);

        // compare contents of both lists
        if (list.size() != list2.size())
            problem = true;

        for (int i=0; i<list.size(); i++)
        {
            if (!list.get(i).equals(list2.get(i)))
            {
                problem = true;
            }
        }

        if (problem)
            System.out.println ("saveTree and loadTree don't work");
        else    
            System.out.println("\n\nsaveTree and loadTree seem to work well."
                    + "\nTake a look at both displays and confirm yourself.");
    }

    /**
     * Prompts the user with selections until valid input is given and returns the response.
     * Helper method for the test method below.
     * @return a valid inputted number representing the tester selection
     */
    private int prompt()
    {
        Scanner in = new Scanner(System.in);
        int response = 0;
        boolean isInvalidInput = true;

        while (isInvalidInput)
        {
            try
            {
                System.out.println("\nEnter 1 to test basic tree methods, 2 for morse code methods,"
                        + " 3 to test Save and Read to and from a File, 4 to play Twenty Questions,"
                        + " and 5 to quit.");

                response = Integer.parseInt(in.nextLine());
                isInvalidInput = false;
            }
            catch (IllegalArgumentException i)
            {
                System.out.print("\nInvalid input, please try again.");
            }
        }

        return response;
    }

    /**
     * Compiles all aspects of the tester in a menu-based system.
     */
    public void test()
    {
        System.out.println("Welcome to the Binary Tree Tester.");
        System.out.println("This tests the implementation of the TreeUtil class.");

        while (true)
        {
            int response = prompt();

            if (response == 1)
            {
                basicTest();
            }
            if (response == 2)
            {
                morseTest();
            }
            if (response == 3)
            {
                fileTest();
            }
            if (response == 4)
            {
                TreeUtil.twentyQuestions();
            }
            if (response == 5)
            {
                System.out.println("\nYou win!");
                System.exit(1);
            }
            if (response < 1 || response > 5)
            {
                System.out.print ("\nInvalid input, please try again.");
            }
        }
    }

    /**
     * Called by the display object to send back the node value
     * when a node is visited.
     * @param value the value passed that is printed out
     */
    public void sendValue(Object value)
    {
        System.out.print(value + " ");
    }

    /**
     * Main method.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        new BinaryTreeTester().test();
    }
}
