import java.util.*;
/**
 * Tester for the Hashing lab.
 * There are no user serviceable parts inside.  Changing anything will
 * void your warranty.
 */
public class HashSetTester
{
    private static final boolean DEBUG = true;

    public static void main(String[] args)
    {
        Set<Rectangle> real = new HashSet<Rectangle>();
        MyHashSet<Rectangle> fake = new MyHashSet<Rectangle>();
        for (int i = 0; i < 1000; i++)
        {
            debug("real:  " + real);
            debug("fake:  " + fake);

            int width = random(4) + 1;
            int height = random(3) + 1;

            Rectangle value = new Rectangle(width, height);

            Rectangle duplicate = new Rectangle(width, height);
            if (!value.equals(duplicate))
                throw new RuntimeException("Rectangles with the same dimensions should be equal to each other");
            if (value.hashCode() != duplicate.hashCode())
                throw new RuntimeException("Rectangles with the same dimensions should have the same hash code");


            boolean realBool = real.contains(value);
            boolean fakeBool = fake.contains(value);
            if (fakeBool != realBool)
                throw new RuntimeException("contains(" + value + ") returned " + fakeBool +
                        " and should return " + realBool);

            if (random(2) == 1)
            {
                //add
                debug("add(" + value + ")");
                realBool = real.add(value);
                fakeBool = fake.add(value);
                if (fakeBool != realBool)
                    throw new RuntimeException("add(" + value + ") returned " + fakeBool +
                            " and should return " + realBool);
            }
            else
            {
                //remove
                debug("remove(" + value + ")");
                realBool = real.remove(value);
                fakeBool = fake.remove(value);
                if (fakeBool != realBool)
                    throw new RuntimeException("remove(" + value + ") returned " + fakeBool +
                            " and should return " + realBool);
            }

            int realInt = real.size();
            int fakeInt = fake.size();
            if (realInt != fakeInt)
                throw new RuntimeException("size() returned " + fakeInt + " and should return " +
                        realInt);
        }

        System.out.println("MyHashSet works!");
    }

    private static void debug(String s)
    {
        if (DEBUG)
            System.out.println(s);
    }

    private static int random(int n)
    {
        return (int)(n * Math.random());
    }
}