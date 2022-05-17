import java.io.*;
import java.util.*;

/**
 * The FileUtil class can load from a file to an iterator and
 * write from an iterator to a file.
 * @author Binary Tree Lab Writers
 * @author Nelson Gou: documentation
 * @version 11/19/2021
 */
public class FileUtil
{
    /**
     * Loads contents of a file into a list and returns its iterator.
     * @param fileName name of the file
     * @return an Iterator for a list containing a String for each line of the file
     */
    public static Iterator<String> loadFile(String fileName)
    {
        try
        {
            Scanner in = new Scanner(new File(fileName));
            List<String> list = new ArrayList<String>();
            while (in.hasNextLine())
                list.add(in.nextLine());
            in.close();
            return list.iterator();
        }
        catch(FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes each element of a list into a line in the file.
     * @param fileName name of the file
     * @param data iterator for the list
     */
    public static void saveFile(String fileName, Iterator<String> data)
    {
        try
        {
            PrintWriter out = new PrintWriter(
                    new FileWriter(fileName), true);
            while (data.hasNext())
                out.println(data.next());
            out.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}