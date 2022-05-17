import java.util.ArrayList;
import java.util.Iterator;

/**
 * The WordGame class plays a variety of word games, including echo and jotto.
 * @author Nelson Gou
 * @version 1/24/2022
 */
public class WordGame
{
    private WordGameDisplay display;
    private ArrayList<String> dictionary;

    /**
     * Constructs a WordGame. Creates a new WordGameDisplay and reads in
     * dictionary.txt into an ArrayList.
     */
    public WordGame()
    {
        display = new WordGameDisplay();
        dictionary = new ArrayList<String>();

        Iterator<String> it = display.loadWords("words.txt");

        while (it.hasNext())
            dictionary.add(it.next());
    }

    /**
     * Sets title of the display to "The Echo Game".
     * Prompts the user for a word, then searches for it in the dictionary.
     * If the word is found, gives the word number in the dictionary.
     * Continues forever.
     */
    public void echo()
    {
        display.setTitle("The Echo Game");
        display.setText("Enter a word.");

        while (true)
        {
            String guess = display.getGuess();

            String text = "\"" + guess + "\" is ";

            int index = dictionaryIndex(guess);

            if (index == -1)
                text += "not a word";
            else
                text += "word #" + index;

            text += ".\nEnter another word.";

            display.setText(text);
        }
    }

    /**
     * Uses binary search to find a word within a sorted dictionary.
     * @param word the word to search for
     * @return index of the word in the dictionary
     */
    public int dictionaryIndex(String word)
    {
        int left = 0, right = dictionary.size()-1;

        while (left <= right)
        {
            int mid = (left + right)/2;

            if (dictionary.get(mid).compareTo(word) < 0)
                left = mid+1;
            else if (dictionary.get(mid).compareTo(word) > 0)
                right = mid-1;
            else
                return mid;
        }

        return -1;
    }

    /**
     * Gets a random word from the dictionary.
     * @return a random word from the dictionary
     */
    public String getRandomWord()
    {
        return dictionary.get((int) (Math.random() * dictionary.size()));
    }

    /**
     * Gets a random word from the dictionary of a specific length.
     * @param length length of the word
     * @return a word from the dictionary that has the specified length
     */
    public String getRandomWord(int length)
    {
        while (true)
        {
            String randomWord = getRandomWord();

            if (randomWord.length() == length)
                return randomWord;
        }
    }

    /**
     * Returns the number of common letters in two given Strings.
     * Uses String indexOf to see if the first letter of w1 is present in w2.
     * If present, removes that letter from both Strings and recursively calls on the new Strings.
     * If not present, removes that letter from w1 and recursively calls on the new Strings.
     * Stops if any of the Strings have length 0.
     * @param w1 word 1
     * @param w2 word 2
     * @precondition w1 and w2 have letters from 'a' to 'z'
     * @return number of common letters in w1 and w2
     */
    public int commonLetters(String w1, String w2)
    {
        if (w1.length() == 0 || w2.length() == 0)
            return 0;

        int indexOf = w2.indexOf(w1.substring(0, 1));
        w1 = w1.substring(1);

        if (indexOf == -1)
            return commonLetters(w1, w2);
        else
            return 1+commonLetters(w1, w2.substring(0, indexOf)+w2.substring(indexOf+1));
    }

    /**
     * Plays a game of Jotto.
     * Computer selects a random 2-letter word, then asks the user to guess.
     * Tells the user if any letters are in common. If user guesses the correct word,
     * the computer selects a random 3-letter word, and continues. The user can type "pass"
     * to skip, but still stays on the n-letter word level.
     */
    public void jotto()
    {
        int guessLength = 2;
        String text = "";

        while (true)
        {
            display.setTitle("Jotto (" + guessLength + " letters)");
            text += "Guess my " + guessLength + "-letter word.\n";
            display.setText(text);

            String randomWord = getRandomWord(guessLength);
            // System.out.println("randomWord: " + randomWord);
            int guessCnt = 1;
            boolean shouldContinue = true;

            while (shouldContinue)
            {
                String guess = display.getGuess();

                if (guess.equals("pass"))
                {
                    text += "It was \"" + randomWord + "\". Play again!\n";
                    shouldContinue = false;
                }
                else
                {
                    text += guessCnt + ".\t" + guess + "\t";
                    guessCnt++;

                    int index = dictionaryIndex(guess);

                    if (randomWord.equals(guess))
                    {
                        text += "That's it!\n";
                        shouldContinue = false;
                        guessLength++;
                    }
                    else if (guess.length() != guessLength)
                        text += "must be " + guessLength + " letters\n";
                    else if (index == -1)
                        text += "not a word\n";
                    else
                        text += commonLetters(randomWord, guess) + "\n";
                }

                display.setText(text);
            }
        }
    }

    /**
     * Sets a menu to select a game to play.
     * Prompts user to enter "echo" or "jotto". If user does not enter one of the two options,
     * prompts user again.
     */
    public void menu()
    {
        display.setTitle("Shall we play a game?");
        display.setText("echo\njotto");

        boolean shouldContinue = true;

        while (shouldContinue)
        {
            String game = display.getGuess();
            shouldContinue = false;

            if (game.equals("echo"))
                echo();
            else if (game.equals("jotto"))
                jotto();
            else
            {
                display.setText("echo\njotto\n\nInvalid input. Try again!");
                shouldContinue = true;
            }
        }
    }
}