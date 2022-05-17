import java.util.ArrayList;

/**
 * Tester for Old MacDonald's Farm by asking his animals to sing a good song.
 * @author Nelson Gou
 * @author Thomas Liu
 * @version 10/10/21
 */
public class OldMacDonaldsFarm
{
    private String farmerName;
    private ArrayList<Animal> farmAnimals;

    /**
     * Constructs OldMacDonaldsFarm with farmer name as "Old MacDonald" and a list of his animals.
     */
    public OldMacDonaldsFarm()
    {
        farmerName = "Old MacDonald";
        farmAnimals = new ArrayList<Animal>( );
    }

    /**
     * Enslaves Old MacDonald's animals to sing a verse of his excellent song.
     */
    public void singVerse()
    {
        String phrase1 = farmerName + " had a farm," ;
        String ei = " E-I-E-I-O";

        System.out.println(phrase1 + ei + " and on his farm he had some " +
                           farmAnimals.get(farmAnimals.size()-1).getCommonName() + "s," + ei + ".");

        for (int i=farmAnimals.size()-1; i>=0; i--)
        {
            Animal a = farmAnimals.get(i);
            String s = a.speak();
            System.out.println("With a " + s + "-" + s + " here, and a " + s + "-" + s +
                               " there,\nHere a " + s + ", there a " + s + ", every where a " +
                               s + "-" + s + ",");
        }

        System.out.println(phrase1 + ei + ".\n");
    }

    /**
     * Main method.
     * @param args arguments from the command line
     */
    public static void main (String[] args)
    {
        OldMacDonaldsFarm singer = new OldMacDonaldsFarm( );
        singer.farmAnimals.add(new Chicken( ));
        singer.singVerse( );
        singer.farmAnimals.add(new Chick());
        singer.singVerse( );
        singer.farmAnimals.add(new Rooster( ));
        singer.singVerse( );
        singer.farmAnimals.add(new Pig( ));
        singer.singVerse( );
    }
}
