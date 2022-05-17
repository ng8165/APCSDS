import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * The Tetris class makes a Tetris window that the user can play Tetris in.
 * @author Nelson Gou
 * @version 3/10/22
 */
public class Tetris implements ArrowListener
{
    private BlockDisplay display;
    private MyBoundedGrid<Block> grid;
    private Tetrad activeTetrad;
    private double wait;

    private int completedRows;
    private long points;

    /**
     * Constructs a Tetris object.
     * Initializes the grid to 20 rows and 10 columns, then creates a window named "Tetris".
     */
    public Tetris()
    {
        grid = new MyBoundedGrid<Block>(20, 10);

        // display set up
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        display.setArrowListener(this);

        newTetrad();

        wait = 1000;
        completedRows = 0;
        points = 0;

        // audio (background music)
        new JFXPanel();
        MediaPlayer player = new MediaPlayer(new Media(new File("tetris.mp3").toURI().toString()));
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(0.1);
        player.play();

        play();
    }

    /**
     * Sets the currently active Tetrad to a new Tetrad.
     * The purpose is to "hide" the display parameter for Tetrad (only used to throw an exception).
     */
    private void newTetrad()
    {
        activeTetrad = new Tetrad(grid, display);
    }

    /**
     * Rotates the active tetrad and updates the display.
     * Called whenever the up arrow on the keyboard is pressed.
     * @postcondition the active tetrad should be rotated (if possible)
     */
    @Override
    public void upPressed()
    {
        activeTetrad.rotate();
        display.showBlocks();
    }

    /**
     * Shifts the active tetrad down by 1 row and updates the display.
     * Called whenever the down arrow on the keyboard is pressed.
     * @postcondition the active tetrad should be shifted down (if possible)
     */
    @Override
    public void downPressed()
    {
        activeTetrad.translate(1, 0);
        display.showBlocks();
    }

    /**
     * Shifts the active tetrad left by 1 column and updates the display.
     * Called whenever the left arrow on the keyboard is pressed.
     * @postcondition the active tetrad should be shifted left (if possible)
     */
    @Override
    public void leftPressed()
    {
        activeTetrad.translate(0, -1);
        display.showBlocks();
    }

    /**
     * Shifts the active tetrad right by 1 column and updates the display.
     * Called whenever the right arrow on the keyboard is pressed.
     * @postcondition the active tetrad should be shifted right (if possible)
     */
    @Override
    public void rightPressed()
    {
        activeTetrad.translate(0, 1);
        display.showBlocks();
    }

    /**
     * Shifts the active tetrad down by 1 row until it cannot move anymore.
     * Creates a new active tetrad. Then updates the display.
     * Called whenever the space button on the keyboard is pressed.
     * @postcondition the old active tetrad should be shifted all the way down,
     *                and there should be a new active tetrad
     */
    @Override
    public void spacePressed()
    {
        boolean shouldContinue = true;

        while (shouldContinue)
            shouldContinue = activeTetrad.translate(1, 0);

        clearCompletedRows();

        newTetrad();

        display.showBlocks();
    }

    /**
     * Repeatedly pauses for 1 second, moves the active tetrad down 1 row, and updates the display.
     * If unable to move the active tetrad, creates a new active tetrad.
     */
    public void play()
    {
        while (true)
        {
            try
            {
                Thread.sleep((long) wait);
            }
            catch(InterruptedException e)
            {
                // ignore
            }

            if (!activeTetrad.translate(1, 0))
            {
                clearCompletedRows();
                newTetrad();
            }

            display.showBlocks();
        }
    }

    /**
     * Determines if a given row is completed or not (completely filled with blocks).
     * @param row row to check completion for
     * @precondition 0 <= row < number of rows
     * @return true if every cell in the given row is occupied; otherwise false
     */
    private boolean isCompletedRow(int row)
    {
        for (int i=0; i<grid.getNumCols(); i++)
        {
            if (grid.get(new Location(row, i)) == null)
                return false;
        }

        return true;
    }

    /**
     * Clears all blocks in the given row, then shifts each row down.
     * @param row row to clear
     * @precondition 0 <= row < number of rows and the given row is full of blocks
     * @postcondition every block in the given row has been removed, and every block in each
     *                row above has been moved down one row
     */
    private void clearRow(int row)
    {
        for (int i=row-1; i>0; i--)
        {
            for (int j=0; j<grid.getNumCols(); j++)
            {
                Block top = grid.get(new Location(i, j));

                if (top != null)
                    top.moveTo(new Location(i+1, j));
                else
                    grid.remove(new Location(i+1, j));
            }
        }

        for (int i=0; i<grid.getNumCols(); i++)
        {
            Block temp = grid.get(new Location(0, i));

            if (temp != null)
                temp.removeSelfFromGrid();
        }
    }

    /**
     * Clears all completed rows.
     * If a row is completed, removes all blocks and shifts everything down.
     * @postcondition all completed rows have been cleared
     */
    private void clearCompletedRows()
    {
        int cleared = 0;

        for (int i=0; i<grid.getNumRows(); i++)
        {
            if (isCompletedRow(i))
            {
                clearRow(i);

                wait *= 0.95;
                cleared++;
            }
        }

        if (cleared == 1)
            points += 40;
        else if (cleared == 2)
            points += 100;
        else if (cleared == 3)
            points += 300;
        else if (cleared == 4)
            points += 1200;

        completedRows += cleared;

        display.updateLevelsAndScore((completedRows/10+1), points);
    }

    /**
     * Main method.
     * Instantiates a new Tetris object, which starts running the Tetris game.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        new Tetris();
    }
}
