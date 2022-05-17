import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

/**
 * DO NOT MODIFY THIS CODE
 * There are no user serviceable components.  Any changes will void your warranty.
 */

/**
 * The test class BlockTest. An attempt to recreate GridMonster as a Junit based tester.
 * Demonstrates a sample regression testing harness for revisions of Blocks code.
 * Replace GridMonster with this file.
 *
 * Reference information
 * BlueJ Junit
 *   http://www.bluej.org/tutorial/testing-tutorial.pdf
 *   http://www.bluej.org/papers/2003-06-iticse-unittest.pdf
 *
 * Junit reference information
 *   http://junit.sourceforge.net/javadoc/org/junit/Assert.html
 *
 * Use of @attribute in Junit (not employed in this demo)
 *    Flexible, Reliable Software: Using Patterns and Agile Development
 *       By Henrik B. Christensen
 *       see pages 20 - 21
 *
 * Use of asserts - only to explain Junit
 *   http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html
 *
 * @author  Anu Datar with inputs provided by Martin Baynes
 * @version April 22, 2015
 */
public class BlockTest
{
    /**
     * Default constructor for test class BlockTest - created by 'Create Test Class'
     */
    public BlockTest()
    {
    }

    /**
     * Sets up the test fixture - created by 'Create Test Class'
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture - created by 'Create Test Class'
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Tests
     * Each is preceded by attribute @Test
     * In GridMonster
     * 		if (grid.getNumRows() != 2)
     *	        throw new RuntimeException("getNumRows is dumb");
     * Test be replaced by as assert
     *      assert grid.getNumRows() == 2 ;
     * Or with messaging
     *      assert grid.getNumRows() == 2 : "getNumRows is dumb";
     * Or with actual value
     *      assert grid.getNumRows() == 2 : grid.getNumRows();
     *
     * Junit encapsulates all this in its assertEquals method, and other methods
     *      assertEquals("getNumRows fails",2, grid.getNumRows());
     *
     * Note: Junit runs the tests in random order. Each needs to be complete.
     *
     */

    /**
     * Test method - A sample can be created by 'Create Test Method'
     * Each is preceded by the attribute tag @Test - if commented out will not appear in list of tests, 
     * nor run in all tests
     */
    @Test
    public void Level1()
    {
        testingPreamble();
        System.out.println("Level 1:  Testing getNumRows and getNumCols");

        MyBoundedGrid<String> grid = new MyBoundedGrid<String>(2, 1);
        //            error message, expected, test
        assertEquals("getNumRows fails",2, grid.getNumRows());
        assertEquals("getNumCols fails",1, grid.getNumCols());
        System.out.println("  passed :)\n Test results shows a green bar");
    }

    /**
     * Test method should fail on correct code - to show use of assert debugger (Test Results)
     */
    @Test
    public void Level1fail()
    {
        testingPreamble();
        System.out.println("Level 1:  Testing getNumRows and getNumCols \n - fails on correct code to show debug");

        MyBoundedGrid<String> grid = new MyBoundedGrid<String>(4, 1); // error in this line
        //            error message, expected, test
        assertEquals("getNumRows fails",4, grid.getNumRows());
        assertEquals("getNumCols fails",1, grid.getNumCols());
        System.out.println("  passed :)");
    }

    /**
     * testingPreamble - student instructions
     */
    public void testingPreamble()
    {
        System.out.println("\nRunning a series of tests.");
        System.out.println("Test will stop on first failure, and show a red bar. If it does -");
        System.out.println("Find Test Result window and click on the failed test in the top window ");
        System.out.println("Lower window will then show detail to help debug why it failed\n");
    }

    /**
     * LevelsA
     * Tests levels 1 - 4
     * These tests are independent of Levels 5 - 10
     */@Test
    public void Levels()
    {
        testingPreamble();
        //Level 1
        System.out.println("Level 1:  Testing getNumRows and getNumCols");

        MyBoundedGrid<String> grid = new MyBoundedGrid<String>(2, 1);
        //Fix the assertion below
        assertEquals("getNumRows fails",2, grid.getNumRows());
        assertEquals("getNumCols fails",1, grid.getNumCols());
        System.out.println("  passed :)");

        //Level 2
        System.out.println("Level 2:  Testing isValid");

        assertEquals("isValid fails", true, grid.isValid(new Location(0, 0)));
        //Fix the assertion below
        assertEquals("isValid fails", true, grid.isValid(new Location(1, 0)));
        assertEquals("isValid fails", false, grid.isValid(new Location(2, 0)));
        assertEquals("isValid fails", false, grid.isValid(new Location(0, 1)));
        assertEquals("isValid fails", false, grid.isValid(new Location(0, -1)));
        assertEquals("isValid fails", false, grid.isValid(new Location(-1, 0)));
        System.out.println("  passed :)");

        //Level 3
        System.out.println("Level 3:  Testing getting, putting, and removing");
        Location loc = new Location(1, 0);
        String firstValue = "first";
        String secondValue = "second";

        //note In this block of tests (only), the assert calls affect the test state - can be tricky to follow
        assertNull("get fails",grid.get(loc));
        assertNull("put fails",grid.put(loc, firstValue));
        assertEquals("put/get fails",firstValue,grid.get(loc)) ;
        assertEquals("put fails",firstValue,grid.put(loc, secondValue)) ;
        assertEquals("put fails",secondValue,grid.get(loc));
        assertEquals("remove fails",secondValue,grid.remove(loc));
        assertNull("remove fails",grid.get(loc));
        assertNull("remove fails",grid.remove(loc));
        System.out.println("  passed :)");

        //Level 4
        System.out.println("Level 4:  Testing getting occupied locations");
        String value = grid.remove(loc);

        ArrayList<Location> locs = grid.getOccupiedLocations();
        assertEquals("getOccupiedLocations fails",0,locs.size());
        grid.put(new Location(1, 0), firstValue);
        locs = grid.getOccupiedLocations();
        assertEquals("getOccupiedLocations fails",1,locs.size());
        assertEquals("getOccupiedLocations fails",new Location(1, 0),locs.get(0));
        grid.put(new Location(0, 0), secondValue);
        locs = grid.getOccupiedLocations();
        assertEquals("getOccupiedLocations fails",2,locs.size());
        assertEquals("get fails", true,
                ( (locs.get(0).equals(new Location(0, 0)) && locs.get(1).equals(new Location(1, 0)))
                        ||
                        (locs.get(0).equals(new Location(1, 0)) && locs.get(1).equals(new Location(0, 0))) ) );

        System.out.println("  passed :)");

        //Level 5
        System.out.println("Level 5:  Testing putting into an empty location");

        Block h = new Block();
        Color red = Color.RED;
        h.setColor(red);
        assertEquals("setColor/getColor fails",red, h.getColor());
        MyBoundedGrid<Block> grid2 = new MyBoundedGrid<Block>(4, 5);
        BlockDisplay display = new BlockDisplay(grid2);

        h.putSelfInGrid(grid2, new Location(0, 2));
        display.showBlocks();
        assertEquals("putSelfInGrid fails", h, grid2.get(new Location(0, 2)));
        assertEquals("putSelfInGrid fails", grid2, h.getGrid());
        assertEquals("putSelfInGrid fails", new Location(0, 2), h.getLocation());
        System.out.println("  passed :)");

        //level 6
        System.out.println("Level 6:  Testing removing from grid");
        h.removeSelfFromGrid();
        display.showBlocks();
        assertNull("removeSelfFromGrid fails", grid2.get(new Location(0, 2)));
        assertNull("removeSelfFromGrid fails", h.getGrid() );
        assertNull("removeSelfFromGrid fails", h.getLocation() );
        System.out.println("  passed :)");

        System.out.println("Level 7:  Testing putting into an occupied location");
        h.putSelfInGrid(grid2, new Location(0, 2));
        display.showBlocks();
        Block r = new Block();
        r.putSelfInGrid(grid2, new Location(0, 2));
        display.showBlocks();
        assertNull("putSelfInGrid fails", h.getGrid() );
        assertNull("putSelfInGrid fails", h.getLocation() );
        assertEquals("putSelfInGrid fails", grid2, r.getGrid() );
        assertEquals("putSelfInGrid fails", new Location(0, 2), r.getLocation() );
        assertEquals("putSelfInGrid fails", r, grid2.get(new Location(0, 2)) );
        System.out.println("  passed :)");

        //Level 8
        System.out.println("Level 8:  moving to an empty location");
        r.moveTo(new Location(0, 4));
        display.showBlocks();
        assertNull("moveTo fails", grid2.get(new Location(0, 2)) );
        assertEquals("moveTo fails", new Location(0, 4), r.getLocation());
        assertEquals("moveTo fails", r, grid2.get(new Location(0, 4)) );
        System.out.println("  passed :)");

        //Level 9
        System.out.println("Level 9:  moving to an occupied location");
        h.putSelfInGrid(grid2, new Location(0, 1));
        display.showBlocks();
        r.moveTo(new Location(0, 1));
        display.showBlocks();
        assertNull("moveTo fails", grid2.get(new Location(0, 4) ) );
        assertNull("moveTo fails", h.getGrid());
        assertNull("moveTo fails", h.getLocation() );
        assertEquals("moveTo fails", grid2, r.getGrid() );
        assertEquals("moveTo fails", new Location(0, 1), r.getLocation() );
        assertEquals("moveTo fails", r, grid2.get(new Location(0, 1)) );
        System.out.println("  passed :)");

        //Level 10
        System.out.println("Level 10: moving to its own location");
        r.moveTo(new Location(0, 1));
        display.showBlocks();
        assertEquals("moveTo fails", grid2, r.getGrid() );
        assertEquals("moveTo fails", new Location(0, 1), r.getLocation() );
        assertEquals("moveTo fails", r, grid2.get(new Location(0, 1)) );

        Block block = new Block();
        block.putSelfInGrid(grid2, new Location(0, 3));
        block = new Block();
        block.setColor(Color.RED);
        block.putSelfInGrid(grid2, new Location(2, 0));
        block = new Block();
        block.setColor(Color.RED);
        block.putSelfInGrid(grid2, new Location(2, 4));
        block = new Block();
        block.setColor(Color.RED);
        block.putSelfInGrid(grid2, new Location(3, 1));
        block = new Block();
        block.setColor(Color.RED);
        block.putSelfInGrid(grid2, new Location(3, 2));
        block = new Block();
        block.setColor(Color.RED);
        block.putSelfInGrid(grid2, new Location(3, 3));
        display.showBlocks();

        System.out.println("  passed all tests :) ");
    }
}




