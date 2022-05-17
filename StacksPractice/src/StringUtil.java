import java.util.Stack;

/**
 * The StringUtil Class can reverse a String and check if a String is a palindrome.
 * It performs all operations using a Stack.
 * @author Anu Datar
 * @author Nelson Gou
 * @version 11/8/2021
 */
public class StringUtil
{
    /**
     * Reverse a string using a Stack and substring.
     * @param str the string to reverse
     * @return the reversed string
     */
    public static String reverseString(String str)
    {
        Stack<String> stack = new Stack<String>();
        for (int i=0; i<str.length(); i++)
        {
            stack.push(str.substring(i, i+1));
        }

        String reversed = "";
        while (!stack.isEmpty())
        {
            reversed += stack.pop();
        }

        return reversed;
    }

    /**
     * Determines if the given string is a palindrome or not.
     * Uses the reverseString method written above.
     * @param s the string to run palindrome checking on
     * @return true if the string is a palindrome; otherwise false
     */
    public static boolean isPalindrome(String s)
    {
        return s.equals(reverseString(s));
    }

    /**
     * Main method. Tester for checking that reverse and isPalindrome work well.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        String test =  "racecar";
        String test2 = "notapalindrome";

        if ( !("".equalsIgnoreCase(reverseString(""))) )
            System.out.println("** Oops Something went wrong. Check your reverse method **");

        if ( !("a".equalsIgnoreCase(reverseString("a"))) )
            System.out.println("** Oops Something went wrong. Check your reverse method **");

        if (!test.equalsIgnoreCase(reverseString(test)))
            System.out.println("** Oops Something went wrong. Check your reverse method **");
        else
            System.out.println("Success " + test + " matched " + reverseString(test));
            
        if (test2.equalsIgnoreCase(reverseString(test2)))
            System.out.println("** Oops Something went wrong. Check your reverse method **");

    }
}
