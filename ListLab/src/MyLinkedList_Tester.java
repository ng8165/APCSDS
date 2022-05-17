import java.util.*;

public class MyLinkedList_Tester
{
	//determines whether it prints each operation
	private static final boolean DEBUG = true;

	public static void main(String[] args)
	{
		MyLinkedList<Integer> your = new MyLinkedList<Integer>();
		LinkedList<Integer> real = new LinkedList<Integer>();
		int capacity = 1;
		for (int i = 0; i < 1000; i++)
		{
			debug("your:  " + your);
			debug("real:  " + real);

			if (!your.toString().equals(real.toString()))
				throw new RuntimeException("toString doesn't match");
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
				debug("set(" + index + ", " + value + ")");
				Integer realOld = real.set(index, value);
				Integer yourOld = (Integer)your.set(index, value);
				if (realOld != yourOld)
					throw new RuntimeException("set(" + index + ", " + value + ") returned " +
						yourOld + " and should return " + realOld);
			}
		}

		System.out.println("You win!");
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