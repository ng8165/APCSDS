/**
 * The Animal Class has a latinName, commonName, and can speak.
 * @author Thomas Liu
 * @author Nelson Gou
 * @version 10/11/2021
*/
abstract class Animal implements Comparable
{
    private String latinName;
    private String commonName;

    /**
     * Constructs the Animal class and sets its latin name and common name.
     * @param latin the given latin name
     * @param common the given common name
     * @postcondition latinName and commonName are initialized
     */
    public Animal(String latin, String common)
    {
        latinName = latin;
        commonName = common;
    }

    /**
     * Speaks.
     * @return the word that will be spoken
     */
    public abstract String speak();

    /**
     * Returns the latin name of the animal.
     * @return latinName
     */
    public String getLatinName()
    {
        return latinName;
    }

    /**
     * Sets the animal's latin name.
     * @param name the name to set latinName to
     */
    public void setLatinName(String name)
    {
        latinName = name;
    }

    /**
     * Returns the common name of the animal.
     * @return commonNAme
     */
    public String getCommonName()
    {
        return commonName;
    }

    /**
     * Sets the animal's common name.
     * @param name the name to set commonName to
     */
    public void setCommonName(String name)
    {
        commonName = name;
    }

    /**
     * Compares this animal to another.
     * @param other the other animal to compare with
     * @return 0 if the animals are equal; less than 0 if this animal is less than the other animal;
     *           greater than 0 if this animal is greater than the other animal
     * @throws IllegalArgumentException Expected Animal but received something else
     */
    public int compareTo(Object other)
    {
        if (other instanceof Animal)
        {
            return latinName.compareTo(((Animal) other).getLatinName());
        }

        throw new IllegalArgumentException("Expected Animal but received something else");
    }
}