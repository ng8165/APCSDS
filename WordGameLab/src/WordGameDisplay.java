import java.awt.*;
import java.util.List;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/*
 * DO NOT MODIFY THIS CLASS.
 * There are no user serviceable parts inside.
 * Altering this class in any way will void your warranty.
 *
 * WordGameDisplay is responsible for managing the Word Game lab graphical user interface.
 * It provides the following services:
 *      constructor - initializes the GUI and displays it
 *      setTitle(String title) - sets the title on the GUI frame
 *      getGuess - gets the content of the guess window.  The method blocks until the user
 *                 inputs into the text field.  Typing "enter" into the guess field
 *                 triggers an event that is captured by this object.  The event signals that
 *                 input is ready.  When input is ready, the getGuess method will return the
 *                 contents of the input text field.  Note that the text field must signal an
 *                 event each time getGuess is called.
 *      setText(String text) - replaces the text in the output text area with the value specified
 *                  as the method parameter.
 *      loadWords(String file) - The parameter file specifies a path to a file that must exist.  The
 *                 file is read into an internal data structure and an iterator object is returned
 *                 allowing the calling object to iterate over the data.
 */
public class WordGameDisplay implements ActionListener
{
    private JFrame frame;
    private boolean buttonPressed;
    private JTextField guessField;
    private JTextArea textArea;
    /**
     * constructor initializes the word game display
     */
    public WordGameDisplay()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        guessField = new JTextField(40);
        guessField.addActionListener(this);
        frame.getContentPane().add(guessField);

        textArea = new JTextArea(40, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane);

        guessField.requestFocusInWindow();

        frame.pack();
        frame.setVisible(true);
    }
    /**
     * constructor for future expansion
     * @param key
     */
    public WordGameDisplay(int key)
    {
        this();
    }
    /**
     * sets the GUI title field
     * @param title - the new title for the GUI
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }
    /**
     *gets the content of the guess window.  The method blocks until the user
     *     inputs into the text field.  Typing "enter" into the guess field
     *     triggers an event that is captured by this object.  The event signals that
     *     input is ready.  When input is ready, the getGuess method will return the
     *     contents of the input text field.  Note that the text field must signal an
     *     event each time getGuess is called.
     * @return a String object whose value is the value of the input text field
     */
    public String getGuess()
    {
        buttonPressed = false;
        while (!buttonPressed)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
            }
        }
        String guess = guessField.getText();
        guessField.selectAll();
        return guess;
    }
    /**
     * action listener - fired whenever the input text box changes
     */
    public void actionPerformed(ActionEvent event)
    {
        buttonPressed = true;
    }
    /**
     * replaces the text in the output box with the value in the parameter
     * @param text the new text to display
     */
    public void setText(String text)
    {
        textArea.setText(text);
    }
    /**
     * read the user file into an internal data structure and then return an iterator
     * for the data structure
     * @param file the fully qualified path to the text file to open
     * @return an iterator for the internal data structure
     */
    public Iterator<String> loadWords(String file)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            List<String> words = new ArrayList<String>();
            while (line != null)
            {
                words.add(line);
                line = in.readLine();
            }
            in.close();
            return words.iterator();
        }
        catch(IOException e)
        {
            throw new IllegalArgumentException(e.toString());
        }
    }
}