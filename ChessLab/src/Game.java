import java.awt.Color;

/**
 * The Game class runs a Chess game.
 * @author Nelson Gou
 * @version 4/9/22
 */
public class Game
{
    /**
     * Main method.
     * Sets up the board by instantiating all Pieces, creates a BoardDisplay, and starts
     * the Game with a HumanPlayer and a SmartPlayer.
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        Board board = new Board();

        // board setup
        Piece blackRook1 = new Rook(Color.BLACK, "black_rook.gif");
        blackRook1.putSelfInGrid(board, new Location(0, 0));
        Piece blackKnight1 = new Knight(Color.BLACK, "black_knight.gif");
        blackKnight1.putSelfInGrid(board, new Location(0, 1));
        Piece blackBishop1 = new Bishop(Color.BLACK, "black_bishop.gif");
        blackBishop1.putSelfInGrid(board, new Location(0, 2));
        Piece blackQueen = new Queen(Color.BLACK, "black_queen.gif");
        blackQueen.putSelfInGrid(board, new Location(0, 3));
        Piece blackKing = new King(Color.BLACK, "black_king.gif");
        blackKing.putSelfInGrid(board, new Location(0, 4));
        Piece blackBishop2 = new Bishop(Color.BLACK, "black_bishop.gif");
        blackBishop2.putSelfInGrid(board, new Location(0, 5));
        Piece blackKnight2 = new Knight(Color.BLACK, "black_knight.gif");
        blackKnight2.putSelfInGrid(board, new Location(0, 6));
        Piece blackRook2 = new Rook(Color.BLACK, "black_rook.gif");
        blackRook2.putSelfInGrid(board, new Location(0, 7));
        Piece[] blackPawns = new Piece[8];
        for (int i=0; i<8; i++)
        {
            blackPawns[i] = new Pawn(Color.BLACK, "black_pawn.gif");
            blackPawns[i].putSelfInGrid(board, new Location(1, i));
        }

        Piece whiteRook1 = new Rook(Color.WHITE, "white_rook.gif");
        whiteRook1.putSelfInGrid(board, new Location(7, 0));
        Piece whiteKnight1 = new Knight(Color.WHITE, "white_knight.gif");
        whiteKnight1.putSelfInGrid(board, new Location(7, 1));
        Piece whiteBishop1 = new Bishop(Color.WHITE, "white_bishop.gif");
        whiteBishop1.putSelfInGrid(board, new Location(7, 2));
        Piece whiteQueen = new Queen(Color.WHITE, "white_queen.gif");
        whiteQueen.putSelfInGrid(board, new Location(7, 3));
        Piece whiteKing = new King(Color.WHITE, "white_king.gif");
        whiteKing.putSelfInGrid(board, new Location(7, 4));
        Piece whiteBishop2 = new Bishop(Color.WHITE, "white_bishop.gif");
        whiteBishop2.putSelfInGrid(board, new Location(7, 5));
        Piece whiteKnight2 = new Knight(Color.WHITE, "white_knight.gif");
        whiteKnight2.putSelfInGrid(board, new Location(7, 6));
        Piece whiteRook2 = new Rook(Color.WHITE, "white_rook.gif");
        whiteRook2.putSelfInGrid(board, new Location(7, 7));
        Piece[] whitePawns = new Piece[8];
        for (int i=0; i<8; i++)
        {
            whitePawns[i] = new Pawn(Color.WHITE, "white_pawn.gif");
            whitePawns[i].putSelfInGrid(board, new Location(6, i));
        }

        BoardDisplay display = new BoardDisplay(board);

        int opt = display.selectPlayer();
        Player other;
        if (opt == 0)
            other = new HumanPlayer(board, "Black", Color.BLACK, display);
        else if (opt == 1)
            other = new RandomPlayer(board, "Black", Color.BLACK);
        else
            other = new SmartPlayer(board, "Black", Color.BLACK);

        play(board, display, new HumanPlayer(board, "White", Color.WHITE, display), other);
    }

    /**
     * Executes the next move. Gets the move from the Player, executes it,
     * then sets the original and destination to a different color to indicate the move.
     * Waits 500 milliseconds.
     * @param board the Board where the game is run
     * @param display the BoardDisplay where the game is rendered
     * @param player the player to request a move from
     */
    private static void nextTurn(Board board, BoardDisplay display, Player player)
    {
        display.setTitle(player.getName());
        if (board.isCheck(player.getColor()))
            display.setTitle(player.getName() + " (Checked)");
        Move next = player.nextMove();
        board.executeMove(next);
        display.clearColors();
        display.setColor(next.getSource(), Color.RED);
        display.setColor(next.getDestination(), Color.GREEN);

        try
        {
            Thread.sleep(500);
        }
        catch(InterruptedException e)
        {
        }
    }

    /**
     * Plays a Chess game indefinitely.
     * @param board the Board where the game is run
     * @param display the BoardDisplay where the game is rendered
     * @param white the white Chess Player
     * @param black the black Chess Player
     */
    public static void play(Board board, BoardDisplay display, Player white, Player black)
    {
        while (!board.isGameOver())
        {
            nextTurn(board, display, white);
            if (!board.isGameOver())
                nextTurn(board, display, black);
        }

        display.setTitle("Game Over");
    }
}
