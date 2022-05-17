/**
 * The Rooster class is a Chicken that says "cock-a-doodle-do".
 * @author Nelson Gou
 * @author Thomas Liu
 * @version 10/11/21
 */
public class Rooster extends Chicken
{
    /**
     * Constructs a Rooster with chickenType of "rooster".
     */
    public Rooster()
    {
        super("rooster");
    }

    /**
     * Speaks by saying "cock-a-doodle-do".
     * @return "cock-a-doodle-do"
     */
    @Override
    public String speak()
    {
        return "cock-a-doodle-do";
    }
}
