import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
/**
* CAUTION:  NO USER SERVICEABLE COMPONENTS INSIDE.  DO NOT MODIFY
*/
public class SortDisplay extends JButton implements ActionListener
{
	private static Random random = new Random();

	private static final int FAST = 0;
	private static final int SLOW = 1;
	private static final int STEP = 2;

	private Comparable[] array;  //array modified by the user
	private int[] oldPointers;  //where each arrow used to point
	private int[] pointers;  //where each arrow points
	private Object[] oldObjects;  //old arrangements of objects
	private Object[] objects;  //objects ordered by position on screen
	private boolean clicked;
	private int mode = SLOW;
	private Sorter sorter;

	public SortDisplay(Sorter sorter)
	{
		this.sorter = sorter;

		createArray();

		JFrame frame = new JFrame();
		frame.setTitle("Sort Display");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		String[] choices = {"Run", "Step"};
		JComboBox box = new JComboBox(choices);
		box.setMaximumSize(new Dimension(200, 200));
		box.setActionCommand("mode");
		box.addActionListener(this);
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.getContentPane().add(box);

		JButton button = new JButton("Selection Sort");
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.setMnemonic(KeyEvent.VK_S);
		button.setActionCommand("selection");
		button.addActionListener(this);
		frame.getContentPane().add(button);

		button = new JButton("Insertion Sort");
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.setMnemonic(KeyEvent.VK_I);
		button.setActionCommand("insertion");
		button.addActionListener(this);
		frame.getContentPane().add(button);

		button = new JButton("Mergesort");
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.setMnemonic(KeyEvent.VK_M);
		button.setActionCommand("merge");
		button.addActionListener(this);
		frame.getContentPane().add(button);

		button = new JButton("Quicksort");
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.setMnemonic(KeyEvent.VK_Q);
		button.setActionCommand("quick");
		button.addActionListener(this);
		frame.getContentPane().add(button);

		setAlignmentX(Component.LEFT_ALIGNMENT);
		setMinimumSize(new Dimension(200, 200));
		setPreferredSize(new Dimension(400, 200));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		setActionCommand("step");
		addActionListener(this);
		frame.getContentPane().add(this);

		frame.pack();
		frame.setVisible(true);
	}

	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		g2.setColor(Color.BLACK);
		int unit = getWidth() / pointers.length;
		FontMetrics metrics = g2.getFontMetrics();
		for (int i = 0; i < array.length; i++)
		{
			g2.setColor(Color.BLACK);
			g2.draw(new Rectangle2D.Double(i * unit, 0, unit, unit));
			if (pointers[i] == oldPointers[i])
				g2.setColor(Color.BLUE);
			else
				g2.setColor(Color.RED);
			g2.draw(new Line2D.Double(i * unit + unit / 2, unit / 2,
										pointers[i] * unit + unit / 2, 2 * unit));
			if (objects[i] == oldObjects[i])
				g2.setColor(Color.BLUE);
			else
				g2.setColor(Color.RED);
			String text = objects[i].toString();
			g2.drawString(text, i * unit + unit / 2 - metrics.stringWidth(text) / 2,
								2 * unit + metrics.getAscent());
		}
	}

	private int indexOf(Object x)
	{
		for (int i = 0; i < objects.length; i++)
			if (objects[i] == x)
				return i;
		return -1;
	}

	public void update()
	{
		//check if anything changed
		boolean changed = false;
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] != objects[pointers[i]])
			{
				changed = true;
				break;
			}
		}
		if (!changed)
			return;

		//save old positions
		for (int i = 0; i < array.length; i++)
		{
			oldPointers[i] = pointers[i];
			oldObjects[i] = objects[i];
		}

		//find new positions from array
		for (int i = 0; i < array.length; i++)
			pointers[i] = indexOf(array[i]);

		redraw();

		//save old positions
		for (int i = 0; i < array.length; i++)
		{
			oldPointers[i] = pointers[i];
			oldObjects[i] = objects[i];
		}

		//find ways to reduce arrow length by swapping objects.
		changed = false;
		for (int i = 0; i < array.length; i++)
			for (int j = i + 1; j < array.length; j++)
			{
				int iIndex = pointers[i];
				int jIndex = pointers[j];

				//find total length of all arrows, before and after
				int oldLength = 0;
				for (int k = 0; k < array.length; k++)
					oldLength += Math.abs(k - pointers[k]);
				int newLength = 0;
				for (int k = 0; k < array.length; k++)
				{
					int target = pointers[k];
					if (target == iIndex)
						target = jIndex;
					else if (target == jIndex)
						target = iIndex;
					newLength += Math.abs(k - target);
				}

				if (newLength < oldLength)
				{
					changed = true;

					//should change ANY pointer that points to either of these two objects
					for (int k = 0; k < array.length; k++)
					{
						if (pointers[k] == iIndex)
							pointers[k] = jIndex;
						else if (pointers[k] == jIndex)
							pointers[k] = iIndex;
					}

					Object object = objects[iIndex];
					objects[iIndex] = objects[jIndex];
					objects[jIndex] = object;
				}
			}

		if (!changed)
			return;

		redraw();
	}

	private void redraw()
	{
		if (mode == FAST)
			return;

		repaint();

		if (mode == SLOW)
			try { Thread.sleep(500); } catch(InterruptedException e) {}
		else //(mode == STEPPING)
		{
			clicked = false;
			while (!clicked)
				try { Thread.sleep(100); } catch(InterruptedException e) {}
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		final String action = e.getActionCommand();
		if (action.equals("step"))
		{
			clicked = true;
			return;
		}
		if (action.equals("mode"))
		{
			JComboBox box =(JComboBox) e.getSource();
			String item = (String)box.getSelectedItem();
			if (item.equals("Step"))
				mode = STEP;
			else if (item.equals("Run"))
				mode = SLOW;
			clicked = true;
			return;
		}

		Thread thread = new Thread()
		{
			public void run()
			{
				//finish current sort
				final int oldMode = mode;
				mode = FAST;
				clicked = true;
				try {Thread.sleep(100);} catch(InterruptedException e) {}
				mode = oldMode;

				createArray();

				repaint();

				if (action.equals("selection"))
					sorter.selectionSort(array);
				else if (action.equals("insertion"))
					sorter.insertionSort(array);
				else if (action.equals("merge"))
					sorter.mergesort(array);
				else if (action.equals("quick"))
					sorter.quicksort(array);
				else
					throw new UnsupportedOperationException(action);
			}
		};
		thread.start();
	}

	private void createArray()
	{
		String[] strings = {"alpha", "bravo", "charlie", "delta", "echo",
							"foxtrot", "golf", "hotel", "india", "juliet",
							"kilo", "lima"};
		Integer[] integers = new Integer[12];
		for (int i = 0; i < integers.length; i++)
			integers[i] = new Integer(i + 1);
		Double[] doubles = {new Double(1), new Double(1.059), new Double(1.122), new Double(1.189),
							new Double(1.260), new Double(1.335), new Double(1.414),
							new Double(1.498), new Double(1.587), new Double(1.682),
							new Double(1.782), new Double(1.888), new Double(2)};
		MyLocation[] locs = {new MyLocation(1, 1), new MyLocation(1, 2), new MyLocation(2, 1),
							new MyLocation(2, 2), new MyLocation(1, 3), new MyLocation(2, 3),
							new MyLocation(3, 1), new MyLocation(3, 2), new MyLocation(3, 3),
							new MyLocation(1, 4), new MyLocation(2, 4), new MyLocation(3, 4)};
		int choice = random.nextInt(4);
		Comparable[] copyFrom;
		if (choice == 0)
			copyFrom = strings;
		else if (choice == 1)
			copyFrom = integers;
		else if (choice == 2)
			copyFrom = doubles;
		else
			copyFrom = locs;
		array = new Comparable[random.nextInt(5) + 8];
		for (int i = 0; i < array.length; i++)
			array[i] = copyFrom[i];
		for (int i = 0; i < array.length; i++)
		{
			int j = random.nextInt(array.length - i) + i;
			Comparable temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		pointers = new int[array.length];
		oldPointers = new int[array.length];
		objects = new Object[array.length];
		oldObjects = new Object[array.length];
		for (int i = 0; i < array.length; i++)
		{
			pointers[i] = i;
			oldPointers[i] = i;
			objects[i] = array[i];
			oldObjects[i] = array[i];
		}
		redraw();
	}
}