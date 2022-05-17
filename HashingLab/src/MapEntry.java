import java.util.Map.Entry;

/**
 * The MapEntry class implements Java's Map.Entry interface.
 * It is used in Maps to link keys and values together.
 * @author joelmanning
 * @author Nelson Gou - added documentation
 * @version 1/19/2022
 * @param <K> the type of key to hold
 * @param <V> the type of value to hold
 */
public class MapEntry<K, V> implements Entry<K, V>
{
    private K key;
    private V value;

    /**
     * Constructs a MapEntry by initializing the key and value.
     * @param key the initial key for the entry
     * @param value the initial value for the entry
     */
    public MapEntry(K key, V value)
    {
        super();
        this.key = key;
        this.value = value;
    }

    /**
     * Getter for the private instance field key.
     * @return the key in the entry
     */
    @Override
    public K getKey()
    {
        return key;
    }

    /**
     * Setter for the private instance field key.
     * @param key the key to set this entry's key to
     */
    public void setKey(K key)
    {
        this.key = key;
    }

    /**
     * Getter for the private instance field value.
     * @return the value of the entry
     */
    @Override
    public V getValue()
    {
        return value;
    }

    /**
     * Setter for the private instance field value.
     * Returns the value previously associated with the key.
     * @return the previous value in the entry
     * @param val the value to set this entry's value to
     */
    @Override
    public V setValue(V val)
    {
        V past = value;
        value = val;
        return past;
    }

    /**
     * Determines if me is equal in value to this MapEntry.
     * If me is not a MapEntry, returns false. If it is,
     * checks if its key and value are the same.
     * @param me the Object to check if this is equal to
     * @return true if me is equal to this MapEntry; otherwise false
     */
    @Override
    public boolean equals(Object me)
    {
        if (!(me instanceof MapEntry))
            return false;

        MapEntry other = (MapEntry) me;
        return key.equals(other.getKey());
    }

    /**
     * Returns a String version of this MapEntry.
     * @return a String version of this MapEntry
     */
    @Override
    public String toString()
    {
        return key + "=" + value;
    }
}
