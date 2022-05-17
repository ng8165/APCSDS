import java.util.*;

/**
 * A tester for the MyLinkedList<E> class, focusing on iterators and various
 * add() and remove() methods.
 * 
 * @author Sumer Kohli
 * @version 1.0.0r
 */
public class MyLinkedList_IteratorTester
{
    /**
     * This is the main method of the tester. Prepare for exceptions. I'm extremely mean on this one.
     */
    public static void main(String[] args)
    {
        MyLinkedList<Integer> list = new MyLinkedList<Integer>();
        LinkedList<Integer> orig = new LinkedList<Integer>();
        final int NUM_TEST_TYPES = 7;
        for(int i = 0; i < 500; i++){
            int div = i % NUM_TEST_TYPES;
            if(div == 0 || div == 1){
                Integer[] to_add = new Integer[5];
                for(int j = 0; j < 5; j++) to_add[j] = new Integer((i*(int)(Math.sin(i*i+j)*1000)*i+i*i)/((int)Math.cos(i*i*i-j*i)*10000+1+i));
                if(div == 0){
                    for(int j = 0; j < 5; j++){
                        System.out.println("Adding " + to_add[j] + " to beginning with addFirst().");
                        list.addFirst(to_add[j]);
                        orig.addFirst(to_add[j]);
                    }
                } else {
                    for(int j = 0; j < 5; j++){
                        System.out.println("Adding " + to_add[j] + " to end with addLast().");
                        list.addLast(to_add[j]);
                        orig.addLast(to_add[j]);
                    }
                }
            } else if((div == 2) || (div == 3)){
                if(div == 2)
                {
                    System.out.println("Removing the last element of the list with removeLast().");
                    list.removeLast();
                    orig.removeLast();
                } else {
                    System.out.println("Removing the first element of the list with removeFirst().");
                    list.removeFirst();
                    orig.removeFirst();
                }
            } else if(div == 4){ 
                System.out.println("Testing the remove() function by itself.");
                int origSize = orig.size();
                for(int j = origSize - 1; j > (origSize / 2); j--){
                    System.out.println("Remove(" + j + ")");
                    list.remove(j);
                    orig.remove(j);
                    System.out.println("Orig: " + orig.toString());
                    System.out.println("Your List: " + list.toString());
                    if(!list.toString().equals(orig.toString()))
                        throw new RuntimeException("Remove() function failed at index " + j);
                    if(list.size() != orig.size())
                        throw new RuntimeException("Your list was the wrong size after removing node at index " + j);
                }
            } else if(div == 5){
                int indToAdd = (int)(Math.random() * 100 * i) % orig.size(), toAdd = (int)(Math.random() * 100);
                System.out.println("Testing arbitrary add() method at index " + indToAdd);
                list.add(indToAdd, toAdd);
                orig.add(indToAdd, toAdd);
            } else if(div == (NUM_TEST_TYPES - 1)){
                System.out.println("Testing the remove() function in conjunction with iterators.");
                Iterator<Integer> it = list.iterator();
                for(int j = orig.size() - 1; j > 0; j--){
                    System.out.println("Iterator.remove(" + j + ")");
                    it.next();
                    it.remove();
                    orig.removeFirst();
                    System.out.println("Orig: " + orig.toString());
                    System.out.println("Your List: " + list.toString());
                    if(!list.toString().equals(orig.toString()))
                        throw new RuntimeException("Iterator.remove() function failed at index " + j);
                    if(list.size() != orig.size())
                        throw new RuntimeException("Your list was the wrong size after removing node at index " + j);
                }
            }
            System.out.println("Orig: " + orig.toString());
            System.out.println("Your List: " + list.toString());
            int listSum = 0, origSum = 0;
            if(list.size() != orig.size())
                throw new RuntimeException("Your list is the wrong size (should have been " + orig.size() + ").");
            for(Iterator<Integer> it = list.iterator(); it.hasNext();)
                listSum += it.next();
            for(Integer j : orig)
                origSum += j;
            if(listSum != origSum || !list.toString().equals(orig.toString()))
                throw new RuntimeException("Check your iterator and linked list implementation, and also your add*() and remove*() methods.");
        }
        System.out.println("\n\nCONGRATULATIONS! Your LinkedList implementation's iterators and add and remove methods work (or at least, most of them).");
    }
}
