/**
 * The Chick class is a Chicken that says "peep".
 * @author Nelson Gou
 * @author Thomas Liu
 * @version 10/11/21
 */
public class Chick extends Chicken
{
    /**
     * Constructs a Chick with chickenType set to "chick".
     */
    public Chick()
    {
        super("chick");
    }

    /**
     * Speaks by saying "peep".
     * @return "peep"
     */
    @Override
    public String speak()
    {
        return "peep";
    }
}
