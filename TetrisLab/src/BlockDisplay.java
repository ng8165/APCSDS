import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The BlockDisplay class is used to display the contents of a game board.
 *
 * @author Anu Datar
 * Changed block size and added a split panel display for next block and Score
 * 
 * @author Ryan Adolf
 * Fixed the lag issue with block rendering 
 * Removed the JPanel
 *
 * @author Nelson Gou
 * Added space key functionality to have block jump down, and added error message
 *
 * @version 3/13/22
 */
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color BORDER = Color.DARK_GRAY;

    private static final int OUTLINE = 2;
    private static final int BLOCKSIZE = 20;
    private static final int SHIFT = 2*BLOCKSIZE;

    private MyBoundedGrid<Block> board;
    private JFrame frame;
    private ArrowListener listener;

    private int level;
    private long points;

    // Constructs a new display for displaying the given board
    public BlockDisplay(MyBoundedGrid<Block> board)
    {
        this.board = board;

        level = 1;
        points = 0;

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                BLOCKSIZE * board.getNumCols() + 2*SHIFT,
                BLOCKSIZE * board.getNumRows() + 3*BLOCKSIZE
        ));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BORDER);
        g.fillRect(SHIFT, BLOCKSIZE, BLOCKSIZE * board.getNumCols() + OUTLINE,
                               BLOCKSIZE * board.getNumRows());

        g.setColor(Color.WHITE);
        g.drawString("Level " + level + ", " + points + " points", 9*BLOCKSIZE/2,
                (board.getNumRows()+2)*BLOCKSIZE);

        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumCols(); col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect(col * BLOCKSIZE + OUTLINE/2 + SHIFT,
                        row * BLOCKSIZE + OUTLINE/2 + BLOCKSIZE,
                        BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }

    }

    //Redraws the board to include the pieces and border colors.
    public void showBlocks()
    {
        repaint();
    }

    // Sets the title of the window.
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
    }

    public void setArrowListener(ArrowListener l)
    {
        listener = l;
    }

    /**
     * Shows an alert on screen that shows the losing message and the player's level and score.
     */
    public void endGameMessage()
    {
        setTitle("skill issue");
        String message = "You lost :(\nLevel " + level + ", " + points + " points";
        JOptionPane.showMessageDialog(frame, message, "Tetris", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Joint setter for level and points (to display below the Tetris window).
     * @param l new level value (might be same as before)
     * @param p new points value (might be same as before)
     */
    public void updateLevelsAndScore(int l, long p)
    {
        level = l;
        points = p;
    }
}
