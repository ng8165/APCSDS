import java.util.*;

/**
 * TreeUtil contains the following methods for manipulating binary trees:
 * leftmost, rightmost, maxDepth, createRandom, countNodes, countLeaves,
 * preOrder, inOrder, postOrder, and fillList.
 * It uses FileUtil to provide the ability to write binary trees into files and
 * read and build them back.
 * It also provides a Morse Code decoder tree to decode Morse Code.
 * It can play a game of Twenty Questions.
 * @author Nelson Gou
 * @version 12/2/21
 */
public class TreeUtil
{
    private static Scanner in = new Scanner(System.in); // used to prompt for command line input
    private static final boolean DEBUG = false;

    /**
     * Returns the left most node of a binary tree.
     * @param t root of the binary tree
     * @return leftmost node of the binary tree
     */
    public static Object leftmost(TreeNode t)
    {
        if (t == null)
            return null;

        while (t.getLeft() != null)
        {
            t = t.getLeft();
        }

        return t.getValue();
    }

    /**
     * Returns the right most node of a binary tree.
     * @param t root of the binary tree
     * @return rightmost node of the binary tree
     */
    public static Object rightmost(TreeNode t)
    {
        if (t == null)
            return null;
        else if (t.getRight() == null)
            return t.getValue();

        return rightmost(t.getRight());
    }

    /**
     * Returns the maximum depth of a binary tree.
     * @param t root of a binary tree
     * @return maximum depth of the binary tree
     */
    public static int maxDepth(TreeNode t)
    {
        if (t == null)
            return 0;

        return 1 + Math.max(maxDepth(t.getLeft()), maxDepth(t.getRight()));
    }

    /**
     * Creates a random tree of the specified depth. No attempt to balance the tree
     * is provided.
     * @param depth of the tree
     * @return TreeNode object that points to the generated tree
     */
    public static TreeNode createRandom(int depth)
    {
        if (Math.random() * Math.pow(2, depth) < 1)
            return null;
        return new TreeNode(((int)(Math.random() * 10)),
                createRandom(depth - 1),
                createRandom(depth - 1));
    }

    /**
     * Counts the number of nodes in a binary tree.
     * @param t root of a binary tree
     * @return the number of nodes in the binary tree
     */
    public static int countNodes(TreeNode t)
    {
        if (t == null)
            return 0;

        return 1 + countNodes(t.getLeft()) + countNodes(t.getRight());
    }

    /**
     * Counts the number of leaves (no children) in a binary tree.
     * @param t root of a binary tree
     * @return the number of leaves in the binary tree
     */
    public static int countLeaves(TreeNode t)
    {
        if (t == null)
            return 0;

        if (t.getLeft() == null && t.getRight() == null)
            return 1;

        return countLeaves(t.getLeft()) + countLeaves(t.getRight());
    }

    /**
     * Traverses a binary tree pre-order.
     * @param t root of a binary tree
     * @param display TreeDisplay graphic interface
     */
    public static void preOrder(TreeNode t, TreeDisplay display)
    {
        if (t != null)
        {
            display.visit(t);
            preOrder(t.getLeft(), display);
            preOrder(t.getRight(), display);
        }
    }

    /**
     * Traverses a binary tree in-order.
     * @param t root of a binary tree
     * @param display TreeDisplay graphic interface
     */
    public static void inOrder(TreeNode t, TreeDisplay display)
    {
        if (t != null)
        {
            inOrder(t.getLeft(), display);
            display.visit(t);
            inOrder(t.getRight(), display);
        }
    }

    /**
     * Traverses a binary tree post-order.
     * @param t root of a binary tree
     * @param display TreeDisplay graphic interface
     */
    public static void postOrder(TreeNode t, TreeDisplay display)
    {
        if (t != null)
        {
            postOrder(t.getLeft(), display);
            postOrder(t.getRight(), display);
            display.visit(t);
        }
    }

    /**
     * Fills a list with the values of the nodes of a binary tree, including "$" if null.
     * @param t root of the binary tree
     * @param list list which contains binary tree values
     */
    public static void fillList(TreeNode t, List<String> list)
    {
        if (t == null)
        {
            list.add("$");
        }
        else
        {
            list.add(t.getValue().toString());
            fillList(t.getLeft(), list);
            fillList(t.getRight(), list);
        }
    }

    /**
     * saveTree uses the FileUtil utility class to save the tree rooted at t
     * as a file with the given file name
     * @param fileName is the name of the file to create which will hold the data
     *        values in the tree
     * @param t is the root of the tree to save
     */
    public static void saveTree(String fileName, TreeNode t)
    {
        List<String> list = new ArrayList<String>();
        fillList(t, list);
        FileUtil.saveFile(fileName, list.iterator());
    }

    /**
     * buildTree takes in an iterator which will iterate through a valid description of
     * a binary tree with String values. Null nodes are indicated by "$".
     * @param it the iterator which will iterate over the tree description
     * @return a pointer to the root of the tree built by the iteration
     */
    public static TreeNode buildTree(Iterator<String> it)
    {
        if (!it.hasNext())
        {
            return null;
        }

        String next = it.next();

        if (next.equals("$"))
        {
            return null;
        }
        else
        {
            return new TreeNode(next, buildTree(it), buildTree(it));
        }
    }

    /**
     * Reads a file description of a tree and then builds the tree.
     * @param fileName a valid file name for a file that describes a binary tree
     * @return a pointer to the root of the tree
     */
    public static TreeNode loadTree(String fileName)
    {
        return buildTree(FileUtil.loadFile(fileName));
    }

    /**
     * Utility method that waits for a user to type text into std input and then press enter.
     * @return the string entered by the user
     */
    private static String getUserInput()
    {
        return in.nextLine();
    }

    /**
     * Plays a single round of 20 questions.
     * @postcondition Plays a round of twenty questions, asking the user questions as it
     *                walks down the given knowledge tree, lighting up the display as it goes;
     *                modifies the tree to include information learned.
     * @param t a pointer to the root of the game tree
     * @param display which will show the progress of the game
     */
    private static void twentyQuestionsRound(TreeNode t, TreeDisplay display)
    {
        while (true)
        {
            display.visit(t);

            System.out.println("Is it a(n) " + t.getValue() + "?");
            String input = getUserInput().toLowerCase();

            if (input.equals("yes"))
            {
                if (t.getLeft() == null && t.getRight() == null)
                {
                    System.out.println("I win!");
                    return;
                }

                t = t.getLeft();
            }
            else if (input.equals("no"))
            {
                if (t.getLeft() == null && t.getRight() == null)
                {
                    System.out.println("I give up. What is it?");
                    String animal = t.getValue().toString();
                    String response = getUserInput();

                    System.out.println("What distinguishes a(n) " + response
                            + " from a(n) " + animal + "?");
                    String differentiator = getUserInput();

                    t.setValue(differentiator);
                    t.setLeft(new TreeNode(response));
                    t.setRight(new TreeNode(animal));
                    return;
                }

                t = t.getRight();
            }
            else
            {
                System.out.println("Invalid input: try again.");
            }
        }
    }

    /**
     * Plays a game of 20 questions.
     * Begins by reading in a starting file and then plays multiple rounds
     * until the user enters "quit". Then the final tree is saved.
     */
    public static void twentyQuestions()
    {
        boolean end = false;

        while (!end)
        {
            TreeNode root = loadTree("knowledge.txt");

            TreeDisplay display = new TreeDisplay();
            display.displayTree(root);

            twentyQuestionsRound(root, display);

            saveTree("knowledge.txt", root);

            System.out.println("Enter \"quit\" to quit or anything else to continue playing.");
            String input = getUserInput().toLowerCase();
            if (input.equals("quit"))
            {
                end = true;
            }
        }
    }

    /**
     * Copies a binary tree.
     * @param t the root of the tree to copy
     * @return a new tree, which is a complete copy of t with all new TreeNode objects
     *         pointing to the same values as t (in the same order, shape, etc.)
     */
    public static TreeNode copy(TreeNode t)
    {
        if (t == null)
        {
            return null;
        }

        return new TreeNode(t.getValue(), t.getLeft(), t.getRight());
    }

    /**
     * Tests to see if two trees have the same shape, but not necessarily the
     * same values. Two trees have the same shape if they have TreeNode objects
     * in the same locations relative to the root.
     * @param t1 pointer to the root of the first tree
     * @param t2 pointer to the root of the second tree
     * @return true if t1 and t2 describe trees having the same shape, false otherwise
     */
    public static boolean sameShape(TreeNode t1, TreeNode t2)
    {
        if (t1 == null && t2 == null)
        {
            return true;
        }
        else if (t1 == null || t2 == null)
        {
            return false;
        }

        return sameShape(t1.getLeft(), t2.getLeft()) && sameShape(t1.getRight(), t2.getRight());
    }

    /**
     * Generates a tree for decoding Morse code.
     * @param display the display that will show the decoding tree
     * @return the decoding tree
     */
    public static TreeNode createDecodingTree(TreeDisplay display)
    {
        TreeNode tree = new TreeNode("Morse Tree");
        display.displayTree(tree);

        insertMorse(tree, "a", ".-", display);
        insertMorse(tree, "b", "-...", display);
        insertMorse(tree, "c", "-.-.", display);
        insertMorse(tree, "d", "-..", display);
        insertMorse(tree, "e", ".", display);
        insertMorse(tree, "f", "..-.", display);
        insertMorse(tree, "g", "--.", display);
        insertMorse(tree, "h", "....", display);
        insertMorse(tree, "i", "..", display);
        insertMorse(tree, "j", ".---", display);
        insertMorse(tree, "k", "-.-", display);
        insertMorse(tree, "l", ".-..", display);
        insertMorse(tree, "m", "--", display);
        insertMorse(tree, "n", "-.", display);
        insertMorse(tree, "o", "---", display);
        insertMorse(tree, "p", ".--.", display);
        insertMorse(tree, "q", "--.-", display);
        insertMorse(tree, "r", ".-.", display);
        insertMorse(tree, "s", "...", display);
        insertMorse(tree, "t", "-", display);
        insertMorse(tree, "u", "..-", display);
        insertMorse(tree, "v", "...-", display);
        insertMorse(tree, "w", ".--", display);
        insertMorse(tree, "x", "-..-", display);
        insertMorse(tree, "y", "-.--", display);
        insertMorse(tree, "z", "--..", display);

        return tree;
    }

    /**
     * Helper method for building a Morse code decoding tree.
     * @postcondition inserts the given letter into the decodingTree,
     *                in the appropriate position, as determined by
     *                the given Morse code sequence; lights up the display
     *                as it walks down the tree
     * @param decodingTree is the partial decoding tree
     * @param letter is the letter to add
     * @param code is the Morse code for letter
     * @param display is the display that will show progress as the method walks
     *        down the tree
     */
    private static void insertMorse(TreeNode decodingTree, String letter,
                                    String code, TreeDisplay display)
    {
        while (code.length() > 0)
        {
            display.visit(decodingTree);

            String c = code.substring(0, 1);

            if (c.equals("."))
            {
                if (decodingTree.getLeft() == null)
                {
                    decodingTree.setLeft(new TreeNode(null));
                }

                decodingTree = decodingTree.getLeft();
            }
            else if (c.equals("-"))
            {
                if (decodingTree.getRight() == null)
                {
                    decodingTree.setRight(new TreeNode(null));
                }

                decodingTree = decodingTree.getRight();
            }
            code = code.substring(1);
        }

        decodingTree.setValue(letter);
    }

    /**
     * Decodes Morse code by walking the decoding tree according to the input code.
     * @param decodingTree is the Morse code decoding tree
     * @param cipherText is Morse code consisting of dots, dashes, and spaces
     * @param display is the display object that will show the decoding progress
     * @return the string represented by cipherText
     */
    public static String decodeMorse(TreeNode decodingTree, String cipherText, TreeDisplay display)
    {
        TreeNode node = decodingTree;
        display.visit(node);
        String result = "";

        while (cipherText.length() > 0)
        {
            String c = cipherText.substring(0, 1);

            if (c.equals("."))
            {
                node = node.getLeft();
            }
            else if (c.equals("-"))
            {
                node = node.getRight();
            }
            else
            {
                result += node.getValue();
                node = decodingTree;
            }

            display.visit(node);
            cipherText = cipherText.substring(1);
        }

        result += node.getValue();

        return result;
    }

    /**
     * Evaluates an expression tree.
     * @param expTree root of an expression tree
     * @return the value of the expression represented by the expression tree
     */
    public static int eval(TreeNode expTree)
    {
        Object val = expTree.getValue();

        if (val instanceof Integer)
        {
            return (Integer) val;
        }
        else
        {
            String op = (String) val;

            if (op.equals("+"))
            {
                return eval(expTree.getLeft()) + eval(expTree.getRight());
            }
            else
            {
                return eval(expTree.getLeft()) * eval(expTree.getRight());
            }
        }
    }

    /**
     * Creates an expression tree.
     * @param exp the expression
     * @return the expression tree
     */
    public static TreeNode createExpressionTree(String exp)
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * Debug print function.
     * @postcondition out is printed to System.out
     * @param out the string to send to System.out
     */
    private static void debugPrint(String out)
    {
        if (DEBUG)
            System.out.println("debug: " + out);
    }
}
	