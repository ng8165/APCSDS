/**
 * The Pig class is an Animal that says "oink".
 * @author Nelson Gou
 * @author Thomas Liu
 * @version 10/11/2021
 */
public class Pig extends Animal
{
    /**
     * Constructs a Pig with latin name as "Sus" and common name as "pig".
     */
    public Pig()
    {
        super("Sus", "pig");
    }

    /**
     * Speaks by saying "oink".
     * @return "oink"
     */
    public String speak()
    {
        return "oink";
    }
}
