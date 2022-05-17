import java.util.*;

public class MyArrayList_Tester
{
    //determines whether it prints each operation
    private static final boolean DEBUG = true;

    public static void main(String[] args)
    {
        MyArrayList<Integer> your = new MyArrayList<Integer>();
        ArrayList<Integer> real = new ArrayList<Integer>();
        int capacity = 1;
        for (int i = 0; i < 1000; i++)
        {
            debug("your:  " + your);
            debug("real:  " + real);

            if (!your.toString().equals(real.toString()))
                throw new RuntimeException("toString doesn't match");
            if (your.getCapacity() != capacity)
                throw new RuntimeException("Capacity is " + your.getCapacity() +
                    " and should be " + capacity);
            if (your.size() != real.size())
                throw new RuntimeException("Size is " + your.size() + " and should be " + real.size());

            for (int index = 0; index < real.size(); index++)
                if (your.get(index) != real.get(index))
                    throw new RuntimeException("get(" + index + ") returned " + your.get(index) +
                        " and should return " + real.get(index));

            int op = random(3);
            if (op == 0 && real.size() < 10)
            {
                Integer value = new Integer(random(100));
                debug("add(" + value + ")");
                real.add(value);
                if (!your.add(value))
                    throw new RuntimeException("add(" + value +
                        ") returned false and should return true");
                if (real.size() > capacity)
                    capacity *= 2;
            }
            else if (op == 1 && real.size() > 0)
            {
                int index = random(real.size());
                debug("remove(" + index + ")");
                Integer realValue = real.remove(index);
                Integer yourValue = (Integer)your.remove(index);
                if (realValue != yourValue)
                    throw new RuntimeException("remove(" + index + ") returned " + yourValue +
                        " and should return " + realValue);
            }
            else if (real.size() > 0)
            {
                Integer value = new Integer(random(100));
                int index = random(real.size());
                debug("set(" + index + ", " + value + ")")  ;
                Integer realOld = real.set(index, value);
                Integer yourOld = (Integer)your.set(index, value);
                if (realOld != yourOld)
                    throw new RuntimeException("set(" + index + ", " + value + ") returned " +
                        yourOld + " and should return " + realOld);
            }
        }

        System.out.println("You win!");

        System.out.println("Okay! Now lets have a look at your iterator :");
        
        MyArrayList<Integer> yourit = new MyArrayList<Integer>();
        ArrayList<Integer> realit = new ArrayList<Integer>();
    
        for (int i=10; i<100; i+=10)
        {
            yourit.add(i);
            realit.add(i);
        }
        
        debug("your:  " + yourit);
        debug("real:  " + realit);
        
        Iterator<Integer> your_it = yourit.iterator();
        Integer yourVal;
        Integer value;
        int index=0;
        while (your_it.hasNext())
        {
            yourVal = your_it.next();
            value = realit.get(index);
            if (!(yourVal == value))
            {
                debug("Whoops ... something's wrong");
                throw new RuntimeException("it.next( ) returned " +
                     yourVal + " and should return " + value);
           }
         debug("it.next() returned " +
                     yourVal + " and matches get(" + index +") which returned " + value);
         index++;   
        }
        
        your_it = yourit.iterator();
        while (your_it.hasNext())
        {
            yourVal = your_it.next();
            if (yourVal == 20)
            {
                debug("Calling remove() removed  " + yourVal);
                your_it.remove();                               
            }
        }        

        debug("your:  " + yourit);
        //debug("real:  " + realit);
        
        System.out.println("Now you truly win!!!");
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