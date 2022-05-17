import java.lang.reflect.Method;
import java.lang.reflect.Type;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

//import myArrayList.*;
//import myList.*;

public class WordGameMonster
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        out.println("Testing for the methods (named correctly)");
        try
        {
            Class<?> c = Class.forName("WordGame");

            Method[] allMethods = c.getDeclaredMethods();
            // search for the methods that are required
            // echo with no parameters
            // dictionaryIndex with a String parameter returns an int
            // getRandomWord with no parameters returns a String
            // getRandomWord with 1 int parameter returns a String
            // commonLetters with 2 String parameters returns an int
            List<String> names = new ArrayList<String>();
            for(Method m : allMethods)
            {
                names.add(m.getName());
            }
            if(!names.contains("echo") || !names.contains("dictionaryIndex") ||
                    !names.contains("getRandomWord") || !names.contains("commonLetters"))
            {
                throw new RuntimeException("WordGame - methods are named incorrectly");
            }
        }
        catch (ClassNotFoundException x) 
        {
            System.exit(1);
        }
        out.println("testing dictionaryIndex");
        WordGame theGame = new WordGame();
        if(
                theGame.dictionaryIndex("abbeys") != 15 ||
                theGame.dictionaryIndex("knoll")  != 19739 ||
                theGame.dictionaryIndex("soap")   != 32972  ||
                theGame.dictionaryIndex("zooms")  != 40154)
            throw new RuntimeException("dictionaryIndex ");
        int test = 0;
        out.println("testing getRandomWord(int)");
        if(theGame.getRandomWord(9).length() != 9)
            throw new RuntimeException("getRandomWord is dumb!");
        out.println("testing commonLetters");
        if(theGame.commonLetters("bee","eye") != 2 ||
                theGame.commonLetters("bee","ebb") != 2  ||
                theGame.commonLetters("zoom","zoomed") != 4 ||
                theGame.commonLetters("mothball","mothballing") != 8)
            throw new RuntimeException("commonLetters is dumb!");
        System.out.println("you win");
    }

}
