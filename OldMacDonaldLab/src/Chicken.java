/**
 * The Chicken class is an Animal that says "bawk".
 * @author Nelson Gou
 * @author Thomas Liu
 * @version 10/11/2021
 */
public class Chicken extends Animal
{
    /**
     * Constructs a Chicken with its type set to "chicken".
     */
    public Chicken()
    {
        this("chicken");
    }

    /**
     * Constructs a Chicken with a type.
     * @param chickenType given type of the Chicken
     */
    public Chicken(String chickenType)
    {
        super("Gallus Gallus domesticus", chickenType);
    }

    /**
     * Speaks by saying "bawk".
     * @return "bawk"
     */
    public String speak()
    {
        return "bawk";
    }
}
