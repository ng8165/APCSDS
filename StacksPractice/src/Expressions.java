import java.util.Stack;

/**
 * The Expressions class can convert infix expressions to postfix,
 * evaluate postfix expressions, and check if an expression's parentheses match.
 * It performs all operations using a Stack.
 * @author Anu Datar
 * @author Nelson Gou
 * @version 11/8/2021
 */

public class Expressions
{
    /**
     * Parenthesis Matching: An expression is said to be balanced if
     * every opener has a corresponding closer, in the right order.
     * {, }, [, ], (, or ) are the only types of brackets allowed.
     * @param expression containing operands operators and any of the 6 supported brackets
     * @return true if the parenthesis are balanced; false otherwise
     */
    public static boolean matchParenthesis(String expression)
    {
        Stack<String> stack = new Stack<String>();

        for (int i=0; i<expression.length(); i++)
        {
            String c = expression.substring(i, i+1);

            if (c.equals("(") || c.equals("[") || c.equals("{"))
            {
                stack.push(c);
            }
            else if (c.equals(")") || c.equals("]") || c.equals("}"))
            {
                if (stack.isEmpty())
                {
                    return false;
                }

                String peek = stack.peek();

                if ((peek.equals("(") && c.equals(")")) ||
                        (peek.equals("[") && c.equals("]")) ||
                        (peek.equals("{") && c.equals("}")))
                {
                    stack.pop();
                }
                else
                {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    /**
     * Helper method for infixToPostfix. Returns the precedence for an operator.
     * @param op the given operator
     * @return the precedence of that operator
     */
    private static int precedence(String op)
    {
        if (op.equals("+") || op.equals("-"))
        {
            return 1;
        }
        else if (op.equals("*") || op.equals("/") || op.equals("%"))
        {
            return 2;
        }
        else
        {
            return -1;
        }
    }

    /**
     * Determines if a given String is a number or not.
     * @param s String to test if it is a number or not
     * @return true if it is a number; otherwise false
     */
    private static boolean isNumber(String s)
    {
        try
        {
            Double.parseDouble(s);
            return true;
        }
        catch(Exception E)
        {
            return false;
        }
    }

    /**
     * Returns a string in postfix form if given an expression in infix form as a parameter.
     * Does this conversion using a Stack.
     * @param expr valid expression in infix form
     * @return equivalent expression in postfix form
     */
    public static String infixToPostfix(String expr)
    {
        Stack<String> postFix = new Stack<String>();
        String strPostfix = "";

        String[] tokens = expr.split(" ");

        for (String token: tokens)
        {
            if (isNumber(token)) // rule 1
            {
                strPostfix += token + " ";
            }
            else if (postFix.isEmpty() || postFix.peek().equals("(")) // rule 2
            {
                postFix.push(token);
            }
            else if (token.equals("(")) // rule 3
            {
                postFix.push(token);
            }
            else if (token.equals(")")) // rule 4
            {
                while (!postFix.isEmpty() && !postFix.peek().equals("("))
                {
                    strPostfix += postFix.pop() + " ";
                }

                postFix.pop();
            }
            else if (precedence(token) > precedence(postFix.peek())) // rule 5
            {
                postFix.push(token);
            }
            else if (precedence(token) == precedence(postFix.peek())) // rule 6
            {
                strPostfix += postFix.pop() + " ";
                postFix.push(token);
            }
            else if (precedence(token) < precedence(postFix.peek())) // rule 7
            {
                while (!postFix.isEmpty() && precedence(token) < precedence(postFix.peek()))
                {
                    strPostfix += postFix.pop() + " ";
                }

                postFix.push(token);
            }
        }

        while (!postFix.isEmpty()) // rule 8
        {
            strPostfix += postFix.pop() + " ";
        }

        return strPostfix;
    }

    /**
     * Returns the value of an expression in postfix form. Does this computation using a Stack.
     * @param expr valid expression in postfix form
     * @return value of the expression
     * @precondition postfix expression contains numbers and operators + - * / and %
     *               and that operands and operators are separated by spaces
     */
    public static double evalPostfix(String expr)
    {
        Stack<Double> postfixOperands = new Stack<Double>();

        String[] tokens = expr.split(" ");

        for (String token: tokens)
        {
            if (isNumber(token))
            {
                postfixOperands.push(Double.parseDouble(token));
            }
            else
            {
                double s2 = postfixOperands.pop(), s1 = postfixOperands.pop();

                if (token.equals("+"))
                {
                    postfixOperands.push(s1 + s2);
                }
                else if (token.equals("-"))
                {
                    postfixOperands.push(s1 - s2);
                }
                else if (token.equals("*"))
                {
                    postfixOperands.push(s1 * s2);
                }
                else if (token.equals("/"))
                {
                    postfixOperands.push(s1 / s2);
                }
                else if (token.equals("%"))
                {
                    postfixOperands.push(s1 % s2);
                }
            }
        }

        return postfixOperands.peek();
    }

    /**
     * Main method. Tester to check if infix to postfix and evaluate postfix work well.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        String exp = "2 + 3 * 4";
        test(exp, 14);

        exp = "8 * 12 / 2";
        test(exp, 48);

        exp = "5 % 2 + 3 * 2 - 4 / 2";
        test(exp, 5);

        /*
        exp = "( 2 + 3 ) * 5";
        test(exp, 25);
         */

        // test balanced expressions
        testBalanced("{ 2 + 3 } * ( 4 + 3 )", true);
        testBalanced("} 4 + 4 { * ( 4 + 3 )", false);
        testBalanced("[ [ [ ] ]", false);
        testBalanced("{ ( } )", false);
        testBalanced("( ( ( ) ) )", true);
    }

    public static void test(String expr, double expect)
    {
        String post = infixToPostfix(expr);        
        double val = evalPostfix(post);

        System.out.println("Infix: " + expr);
        System.out.println("Postfix: " + post);
        System.out.println("Value: " + val);
        if (val == expect)
        {
            System.out.print("** Success! Great Job **");
        }
        else
        {
            System.out.print("** Oops! Something went wrong. ");
            System.out.println("Check your postfix and eval methods **");
        }
    }

    public static void testBalanced(String ex, boolean expected)
    {
        boolean act = matchParenthesis(ex);
        if (act == expected)
            System.out.println("** Success!: matchParenthesis(" + ex + ") returned " + act);
        else
        {
            System.out.print("** Oops! Something went wrong check : matchParen(" + ex + ")");
            System.out.println(" returned " + act + " but should have returned " + expected);
        }
    }
}
