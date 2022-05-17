import java.util.*;
public class IteratorTester {
    private static final boolean DEBUG = true;
    public static void main(String args[]) {
        System.out.println("Ensure that the HashSetTester runs correctly before using this.");
        Set<Integer> real = new HashSet<Integer>();
        MyHashSet<Integer> fake = new MyHashSet<Integer>();

        Iterator realIt;
        Iterator fakeIt;

        for (int i = 0; i < 1000; i++) {
            int value = random(100);
            if(real.contains(value) || fake.contains(value))
            {
                real.remove(value);
                fake.remove(value);
            }
            real.add(value);
            fake.add(value);

            debug("Real: " + real);
            debug("Fake: " + fake);

            realIt = real.iterator();
            fakeIt = fake.iterator();

            int n = random(3);
            if (n == 0) { //hasNext() only
                try {
                    while (realIt.hasNext()) {
                        realIt.next();
                        fakeIt.next();
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException("There's something wrong with "
                        + "hasNext(). FIX IT.");
                }
            }
            else if (n == 1) { //next() and hasNext()
                Set<Integer> realSet = new HashSet<Integer>();
                while (realIt.hasNext())
                    realSet.add((Integer)realIt.next());
                Set<Integer> fakeSet = new HashSet<Integer>();
                while (fakeIt.hasNext())
                    fakeSet.add((Integer)fakeIt.next());

                debug("" + realSet);
                debug("" + fakeSet);

                if (!realSet.equals(fakeSet))
                    throw new RuntimeException("next() returns the wrong "
                        + "set of numbers");
            }
            else if (n == 3) { //remove() and hasNext()
                while (realIt.hasNext()) {
                    int r1 = (Integer)realIt.next();
                    int r2 = (Integer)fakeIt.next();

                    int remove = random(2);
                    if (remove == 0) {
                        debug("removing " + r1 + "/" + r2);
                        realIt.remove();
                        fakeIt.remove();
                        debug("Real: " + real);
                        debug("Fake: " + fake);
                    }
                }
            }

            int realSize = real.size();
            int fakeSize = fake.size();
            if (realSize != fakeSize)
                throw new RuntimeException("Size should be " + realSize
                    + " but returned " + fakeSize);

            debug("");
        }
        System.out.println("Congratulations! Your iterator works.");
    }

    private static void debug(String s) {
        if (DEBUG)
            System.out.println(s);
    }

    private static int random(int n) {
        return (int)(n * Math.random());
    }
}