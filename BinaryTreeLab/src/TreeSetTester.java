import java.util.*;

public class TreeSetTester
{
    private static final boolean DEBUG = true;
    private static final int MAX_VALUE = 100;
    private static final int NUMBER_OF_ELEMENTS = 50;

    public static void main(String[] args)
    {
        Set<Integer> real = new TreeSet<Integer>();
        MyTreeSet<Integer> fake = new MyTreeSet<Integer>();
        while (real.size() < NUMBER_OF_ELEMENTS)
        {
            debug("real:  " + real);
            debug("fake:  " + fake);

            Integer value = new Integer(random(MAX_VALUE));

            boolean realBool = real.contains(value);
            boolean fakeBool = fake.contains(value);
            if (fakeBool != realBool)
                throw new RuntimeException("contains(" + value + ") returned " + fakeBool +
                        " and should return " + realBool);

            //add
            debug("add(" + value + ")");
            realBool = real.add(value);
            fakeBool = fake.add(value);
            if (fakeBool != realBool)
                throw new RuntimeException("add(" + value + ") returned " + fakeBool +
                        " and should return " + realBool);

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

            Integer value = new Integer(random(MAX_VALUE));

            boolean realBool = real.contains(value);
            boolean fakeBool = fake.contains(value);
            if (fakeBool != realBool)
                throw new RuntimeException("contains(" + value + ") returned " + fakeBool +
                        " and should return " + realBool);

            //remove
            debug("remove(" + value + ")");
            realBool = real.remove(value);
            fakeBool = fake.remove(value);
            if (fakeBool != realBool)
                throw new RuntimeException("remove(" + value + ") returned " + fakeBool +
                        " and should return " + realBool);

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