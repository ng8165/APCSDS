import java.util.Map;
import java.util.TreeMap;

public class TreeMapTester
{
    private static final boolean DEBUG = true;
    private static final int MAX_VALUE = 100;
    private static final int NUMBER_OF_ELEMENTS = 50;

    public static void main(String[] args)
    {
        Map<Integer, Integer> real = new TreeMap<Integer, Integer>();
        MyTreeMap<Integer, Integer> fake = new MyTreeMap<Integer, Integer>();

        while (real.size() < NUMBER_OF_ELEMENTS)
        {
            debug("real:  " + real);
            debug("fake:  " + fake);

            Integer key = random(MAX_VALUE);
            Integer value = random(MAX_VALUE);

            // containsKey
            boolean realBool = real.containsKey(key);
            boolean fakeBool = fake.containsKey(key);
            if (fakeBool != realBool)
                throw new RuntimeException("containsKey(" + key + ") returned " + fakeBool +
                        " and should return " + realBool);

            // put
            debug("put(" + key + ", " + value + ")");
            Integer realVal = real.put(key, value);
            Integer fakeVal = fake.put(key, value);
            if ((realVal == null ^ fakeVal == null) ||
                    (realVal != null && !realVal.equals(fakeVal)))
                throw new RuntimeException("put(" + key + ", " + value + ") returned " + fakeVal +
                        " and should return " + realVal);

            int realInt = real.size();
            int fakeInt = fake.size();
            if (realInt != fakeInt)
                throw new RuntimeException("size() returned " + fakeInt + " and should return " +
                        realInt);
        }

        while(real.size() > 0)
        {
            debug("real:  " + real);
            debug("fake:  " + fake);

            Integer key = random(MAX_VALUE);

            // containsKey
            boolean realBool = real.containsKey(key);
            boolean fakeBool = fake.containsKey(key);
            if (fakeBool != realBool)
                throw new RuntimeException("containsKey(" + key + ") returned " + fakeBool +
                        " and should return " + realBool);

            // remove
            debug("remove(" + key + ")");
            Integer realVal = real.remove(key);
            Integer fakeVal = fake.remove(key);
            if (!realVal.equals(fakeVal))
                throw new RuntimeException("remove(" + key + ") returned " + fakeVal +
                        " and should return " + realVal);

            int realInt = real.size();
            int fakeInt = fake.size();
            if (realInt != fakeInt)
                throw new RuntimeException("size() returned " + fakeInt + " and should return " +
                        realInt);
        }

        System.out.println("MyTreeSet works!");
    }

    private static void debug(String s)
    {
        if (DEBUG)
            System.out.println(s);
    }

    private static int random(int n)
    {
        return (int)(Math.random() * n);
    }
}