import java.util.*;

/**
 * The MyHashMap<E> class imitates Java's built-in HashMap class. It uses hashing
 * to create a map that de-dupes and runs all frequently used operations in O(1) time.
 * @author Nelson Gou
 * @version 1/19/2022
 * @param <K> the type of key
 * @param <V> the type of value
 */
public class MyHashMap<K, V> implements Map<K, V>
{
    private static final int NUM_BUCKETS = 5;
    private LinkedList<MapEntry<K, V>>[] buckets;
    private int size;

    /**
     * Constructs a MyHashMap.
     * Initializes all buckets to null. Sets size to 0.
     */
    public MyHashMap()
    {
        buckets = new LinkedList[NUM_BUCKETS];
        for (int i=0; i<NUM_BUCKETS; i++)
            buckets[i] = new LinkedList<MapEntry<K, V>>();

        size = 0;
    }

    /**
     * Helper function that determines the index of the bucket where obj should be found.
     * @param obj an Object to find the bucket for
     * @return index of the bucket where obj should be found
     */
    private int toBucketIndex(Object obj)
    {
        return Math.abs(obj.hashCode()) % NUM_BUCKETS;
    }

    /**
     * Returns the size of the MyHashMap (how many MapEntry objects are inside the all the buckets).
     * @return the size of the MyHashMap
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Determines if the MyHashMap is empty (if the size is 0).
     * @return true if the MyHashMap's size is 0; otherwise false
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Determines if the given key is contained inside one of the buckets of the MyHashMap.
     * @param key the Object to test if it is contained as a key
     * @return true if it is contained; otherwise false
     */
    @Override
    public boolean containsKey(Object key)
    {
        for (MapEntry<K, V> elem: buckets[toBucketIndex(key)])
        {
            if (elem.getKey().equals(key))
                return true;
        }

        return false;
    }

    /**
     * Determines if the given value is contained inside one of the buckets of the MyHashMap.
     * Runs in O(N) time, as it is necessary to traverse all buckets.
     * @param value the Object to test if it is contained as a value
     * @return true if it is contained; otherwise false
     */
    @Override
    public boolean containsValue(Object value)
    {
        for (int i=0; i<NUM_BUCKETS; i++)
        {
            for (MapEntry<K, V> entry: buckets[i])
            {
                if (entry.getValue().equals(value))
                    return true;
            }
        }

        return false;
    }

    /**
     * Returns the value that is mapped to the key, if the key exists inside the MyHashSet.
     * If key is not present, returns null;
     * @param key the key of the MapEntry whose value is being requested
     * @return the value that is mapped to key if it exists; if not present, returns null
     */
    @Override
    public V get(Object key)
    {
        for (MapEntry<K, V> entry: buckets[toBucketIndex(key)])
        {
            if (entry.getKey().equals(key))
                return entry.getValue();
        }

        return null;
    }

    /**
     * Inserts a key-value pair into the MyHashMap. If the key is already present, overrides
     * the existing value and returns it. If not present, creates a new MapEntry and returns null.
     * @param key the key to be inserted
     * @param value the value to be inserted
     * @return if the key is already present, returns the value originally mapped
     *         to it; otherwise null
     */
    @Override
    public V put(K key, V value)
    {
        for (MapEntry<K, V> entry: buckets[toBucketIndex(key)])
        {
            if (entry.getKey().equals(key))
            {
                V old = entry.getValue();
                entry.setValue(value);
                return old;
            }
        }

        buckets[toBucketIndex(key)].add(new MapEntry<K, V>(key, value));
        size++;
        return null;
    }

    /**
     * Removes a key-value pair from the MyHashMap. If the key is not present, returns null.
     * @param key the key to be removed
     * @return if the key is present, returns the value mapped to it; otherwise null
     */
    @Override
    public V remove(Object key)
    {
        ListIterator<MapEntry<K, V>> it = buckets[toBucketIndex(key)].listIterator();

        while (it.hasNext())
        {
            MapEntry<K, V> entry = it.next();

            if (entry.getKey().equals(key))
            {
                it.remove();
                size--;
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Inserts all values from the given map into this MyHashMap.
     * @param m the map to be copied over
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for (K key: m.keySet())
            put(key, m.get(key));
    }

    /**
     * Clears all values in this MyHashMap. Deletes all MapEntry objects.
     */
    @Override
    public void clear()
    {
        for (int i=0; i<NUM_BUCKETS; i++)
            buckets[i].clear();

        size = 0;
    }

    /**
     * Returns a Set containing the keys inside this MyHashMap.
     * @return a Set containing the keys inside this MyHashMap
     */
    @Override
    public Set<K> keySet()
    {
        Set<K> s = new HashSet<K>();

        for (int i=0; i<NUM_BUCKETS; i++)
        {
            for (MapEntry<K, V> entry: buckets[i])
                s.add(entry.getKey());
        }

        return s;
    }

    /**
     * Returns a Collection containing the values inside this MyHashMap.
     * @return a Collection containing the values inside this MyHashMap
     */
    @Override
    public Collection<V> values()
    {
        Collection<V> vals = new ArrayList<V>();

        for (int i=0; i<NUM_BUCKETS; i++)
        {
            for (MapEntry<K, V> entry: buckets[i])
                vals.add(entry.getValue());
        }

        return vals;
    }

    /**
     * Returns a Set containing the Map.Entry objects (link a key to a value)
     * inside this MyHashMap.
     * @return a Set containing the Map.Entry objects inside this MyHashMap
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        Set<Map.Entry<K, V>> entries = new HashSet<Map.Entry<K, V>>();

        for (int i=0; i<NUM_BUCKETS; i++)
        {
            for (Map.Entry<K, V> entry: buckets[i])
                entries.add(entry);
        }

        return entries;
    }

    /**
     * Returns a String version of the MyHashMap.
     * @return a String version of the MyHashMap
     */
    @Override
    public String toString()
    {
        String s = "";
        for (int i = 0; i < buckets.length; i++)
            if (buckets[i].size() > 0)
                s += i + ":" + buckets[i] + " ";
        return s;
    }
}
