import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The FindAuthor class parses a document with the Document class and calculates
 * the average word length, type-token ratio, hapax legomana ratio, average number of words
 * per sentence, and sentence complexity.
 * @author Nelson Gou
 * @version 5/16/22
 */
public class FindAuthor
{
    /**
     * Calculates the RMSE (Root Mean Square Error) between two feature ratios.
     * Adds weights by multiplying them to the squared difference.
     * @param text1 feature ratio 1
     * @param text2 feature ratio 2
     * @return the RMSE of the two feature ratios
     */
    private static double rmse(double[] text1, double[] text2)
    {
        final double[] weights = {0, 11, 33, 50, 0.4, 4};
        double sum = 0;
        for (int i=0; i<5; i++)
            sum += weights[i] * Math.pow(text1[i]-text2[i], 2);
        return Math.sqrt(sum/5);
    }

    /**
     * Calculates the ratios for a text file.
     * @param file the text file
     * @throws FileNotFoundException throws if file is not found
     */
    private static double[] calculateRatios(String file) throws FileNotFoundException
    {
        Reader reader = new BufferedReader(new FileReader(file));
        Scanner scanner = new Scanner(reader);

        Document document = new Document(scanner);
        document.parseDocument();

        // calculating ratios
        Map<String, Integer> wordFreq = new HashMap<>();
        double characters = 0, words = 0, oneWords = 0, sentences = 0, phrases = 0;

        for (Sentence s: document.getSentences())
        {
            for (Phrase p: s.getPhrases())
            {
                for (Token t: p.getTokens())
                {
                    if (t.getType().equals(Scanner.TOKEN_TYPE.WORD))
                    {
                        String str = t.getValue();

                        // avg word length
                        characters += str.length();

                        // type-token and hapax legomana ratio
                        wordFreq.put(str, wordFreq.getOrDefault(str, 0)+1);

                        // average word length, type-token, hapax legomana,
                        // and average words in sentence
                        words++;
                    }
                }

                // sentence complexity
                phrases++;
            }

            // average words per sentence and sentence complexity
            sentences++;
        }

        // find words that only appear once (hapax legomana ratio)
        for (var e: wordFreq.entrySet())
            if (e.getValue() == 1)
                oneWords++;

        double avgWordLength = characters / words;
        double typeTokenRatio = wordFreq.size() / words;
        double hapaxLegomanaRatio = oneWords / words;
        double avgWordsSentences = words / sentences;
        double sentenceComplexity = phrases / sentences;

        return new double[] {avgWordLength, typeTokenRatio, hapaxLegomanaRatio,
                avgWordsSentences, sentenceComplexity};
    }

    /**
     * Main method.
     * Reads all given stats, then all mystery texts. For each mystery text,
     * finds the stats which matches the ratios the closest and outputs its author.
     * @param args arguments from the command line
     * @throws IOException throws if file is not found
     */
    public static void main(String[] args) throws IOException
    {
        String[] stats = new File("SignatureFiles").list();
        assert stats != null;

        String[] names = new String[stats.length];
        double[][] ratios = new double[stats.length][5];

        // read all given stats
        for (int i=0; i<stats.length; i++)
        {
            BufferedReader br = new BufferedReader(new FileReader("SignatureFiles/" + stats[i]));
            names[i] = br.readLine();
            for (int j=0; j<5; j++)
                ratios[i][j] = Double.parseDouble(br.readLine());
        }

        for (int i=1; i<=5; i++)
        {
            double[] curr = calculateRatios("MysteryText/mystery" + i + ".txt");
            double min = Double.MAX_VALUE;
            int index = -1;

            // compare RMSE with all given texts and find text with smallest RMSE
            for (int j=1; j<ratios.length; j++)
            {
                double rmse = rmse(curr, ratios[j]);
                if (rmse < min)
                {
                    min = rmse;
                    index = j;
                }
            }

            System.out.println("Mystery Text " + i + "'s author is " + names[index]);
        }
    }
}
