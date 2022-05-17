import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Anu Datar
 *	Modifications made to fix bugs 
 * @author joelmanning
 * Tester for the Hashing lab. There are no user serviceable parts inside.
 * Changing anything will void your warranty.
 */
public class HashMapTester
{
    private static final boolean DEBUG = true;

    public static void main(String[] args)
    {
        Map<Integer, Rectangle> real = new HashMap<Integer, Rectangle>();
        Map<Integer, Rectangle> fake = new MyHashMap<Integer, Rectangle>();
        for (int i = 0; i < 1000; i++)
        {
            debug("real:  " + real);
            debug("fake:  " + fake);

            int width = random(4) + 1;
            int height = random(3) + 1;

            Rectangle value = new Rectangle(width, height);

            Rectangle duplicate = new Rectangle(width, height);
            if (!value.equals(duplicate))
                throw new RuntimeException(
                        "Rectangles with the same dimensions should be equal to each other");
            if (value.hashCode() != duplicate.hashCode())
                throw new RuntimeException(
                        "Rectangles with the same dimensions should have the same hash code");

            Integer integer = new Integer(random(5) + 1);
            boolean realBool = real.containsValue(value);
            boolean fakeBool = fake.containsValue(value);
            if (fakeBool != realBool)
                throw new RuntimeException("containsValue(" + value
                        + ") returned " + fakeBool + " and should return "
                        + realBool);

            boolean realBool2 = real.containsKey(integer);
            boolean fakeBool2 = fake.containsKey(integer);
            if (fakeBool2 != realBool2)
                throw new RuntimeException("containsKey(" + integer
                        + ") returned " + fakeBool2 + " and should return "
                        + realBool2);

            int type = random(3);
            if (type == 1)
            {
                // add
                debug("put(" + integer + ", " + value + ")");
                Rectangle realRect = real.put(integer, value);
                Rectangle fakeRect = fake.put(integer, value);
                if (fakeRect != realRect)
                    throw new RuntimeException("put(" + integer + ", " + value
                            + ") returned " + fakeRect + " and should return "
                            + realRect);
            }
            else if(type == 2)
            {
                // remove
                debug("remove(" + integer + ")");
                Rectangle realRect = real.remove(integer);
                Rectangle fakeRect = fake.remove(integer);
                if (fakeBool != realBool)
                    throw new RuntimeException("remove(" + integer
                            + ") returned " + fakeRect + " and should return "
                            + realRect);
            } else {
                debug("get(" + integer + ")");
                Rectangle realRect = real.get(integer);
                Rectangle fakeRect = real.get(integer);
                if (fakeRect != realRect)
                    throw new RuntimeException("get(" + integer + ") returned " + fakeRect + " and should return "
                            + realRect);
            }

            int realInt = real.size();
            int fakeInt = fake.size();
            if (realInt != fakeInt)
                throw new RuntimeException("size() returned " + fakeInt
                        + " and should return " + realInt);
        }

        System.out.println("MyHashMap works!");
    }

    private static void debug(String s)
    {
        if (DEBUG)
            System.out.println(s);
    }

    private static int random(int n)
    {
        return (int) (n * Math.random());
    }
}
