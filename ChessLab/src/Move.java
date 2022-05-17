/**
 * The Move class represents a single move, in which a piece moves to a destination location.
 * Since a move can be undone, the source location and any captured victims are also recorded.
 * @author Nelson Gou
 * @version 3/28/22
 */
public class Move
{
    private Piece piece;
    private Location source;
    private Location destination;
    private Piece victim;

    /**
     * Constructs a new move for moving the given piece to the given destination.
     * @param piece the piece being moved
     * @param destination the destination of the piece
     * @throws IllegalArgumentException if the source and destination are the same Location
     */
    public Move(Piece piece, Location destination)
    {
        this.piece = piece;
        this.source = piece.getLocation();
        this.destination = destination;
        this.victim = piece.getBoard().get(destination);

        if (source.equals(destination))
            throw new IllegalArgumentException("Both source and dest are " + source);
    }

    /**
     * Getter for the piece being moved.
     * @return the piece
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * Getter for the source location.
     * @return the source location
     */
    public Location getSource()
    {
        return source;
    }

    /**
     * Getter for the destination.
     * @return the destination
     */
    public Location getDestination()
    {
        return destination;
    }

    /**
     * Getter for the victim, if any.
     * @return the victim Piece
     */
    public Piece getVictim()
    {
        return victim;
    }

    /**
     * Returns a String description of the move.
     * @return a String description of the move
     */
    public String toString()
    {
        return piece + " from " + source + " to " + destination + " containing " + victim;
    }

    /**
     * Determines if a Move is equivalent to this one.
     * @param x another Object to compare with
     * @return true if equal; false if not
     */
    public boolean equals(Object x)
    {
        Move other = (Move)x;
        return piece == other.getPiece() && source.equals(other.getSource()) &&
                destination.equals(other.getDestination()) && victim == other.getVictim();
    }

    /**
     * Returns a hash code for this move.
     * All equivalent moves have the same hash code.
     * @return a hash code for this move
     */
    public int hashCode()
    {
        return piece.hashCode() + source.hashCode() + destination.hashCode();
    }
}