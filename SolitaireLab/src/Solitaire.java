import java.util.*;

/**
 * The Solitaire manages a Solitaire game by using stacks for the stock,
 * waste, piles, and foundations.
 * @author Nelson Gou
 * @version 11/4/2021
 */
public class Solitaire
{
    /**
     * Main method.
     * @param args arguments from the command line.
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;

    private long startTime;
    private int dealAmt;

    /**
     * Constructs a Solitaire Game with a stock, waste, 4 foundations,
     * 7 piles, and a display. Shuffles the stock.
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        for (int i = 0; i < 4; i++)
        {
            foundations[i] = new Stack<Card>();
        }

        piles = new Stack[7];
        for (int i = 0; i < 7; i++)
        {
            piles[i] = new Stack<Card>();
        }
        
        stock = new Stack<Card>();
        createStock();
        deal();

        waste = new Stack<Card>();

        display = new SolitaireDisplay(this);

        dealAmt = display.getDealAmt();

        startTime = System.currentTimeMillis();
    }

    /**
     * Returns the card on top of the stock.
     * @return reference to the card on the top of the stock, or null if the stock is empty.
     */
    public Card getStockCard()
    {
        if (stock.isEmpty())
        {
            return null;
        }

        return stock.peek();
    }

    /**
     * Returns the card on top of the waste.
     * @return reference to the card on the top of the waste, or null if the waste is empty.
     */
    public Card getWasteCard()
    {
        if (waste.isEmpty())
        {
            return null;
        }

        return waste.peek();
    }

    /**
     * Returns the card on top of the foundation at the specified index.
     * @param index index of the foundation to peek at
     * @return the card on top of the given foundation,
     *         or null if the foundation is empty
     * @precondition 0 <= index < 4
     */
    public Card getFoundationCard(int index)
    {
        if (foundations[index].isEmpty())
        {
            return null;
        }

        return foundations[index].peek();
    }

    /**
     * Returns a reference to the pile at the specified index.
     * @param index the index of the pile to return
     * @return a reference to the given pile
     * @precondition 0 <= index < 7
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * Called when the stock is clicked. Resets the stock or deals card(s).
     */
    public void stockClicked()
    {
        if (display.isNothingSelected())
        {
            if (stock.isEmpty())
            {
                resetStock();
            }
            else
            {
                dealCards(dealAmt);
            }
        }
    }

    /**
     * Called when the waste is clicked. Selects and unselects the waste.
     */
    public void wasteClicked()
    {
        if (!waste.isEmpty() && display.isNothingSelected())
        {
            display.selectWaste();
        }
        else if (display.isWasteSelected())
        {
            display.unselect();
        }
    }

    /**
     * Called when given foundation is clicked. Moves a card from the selected pile
     * (waste or a pile) to the foundation if legal and can move a card from a foundation to a pile.
     * @param index index of the foundation
     * @precondition 0 <= index < 4
     * @postcondition may change the display, foundations, and pile contents
     */
    public void foundationClicked(int index)
    {
        // select foundation if clicked and nothing else is selected
        if (!foundations[index].isEmpty() && display.isNothingSelected())
        {
            display.selectFoundation(index);
        }

        // unselect foundation if clicked and already selected
        else if (display.isFoundationSelected())
        {
            display.unselect();
        }

        // move card to foundation if pile or waste is selected
        else if (!display.isNothingSelected())
        {
            Stack<Card> pile = waste;
            if (!display.isWasteSelected())
            {
                pile = piles[display.selectedPile()];
            }

            if (canAddToFoundation(pile.peek(), index))
            {
                foundations[index].push(pile.pop());
                display.unselect();
            }

            checkGameFinished();
        }
    }

    /**
     * Called when given pile is clicked. Performs the actions: moving cards from waste,
     * moving cards from foundation, selecting the pile with a yellow border, unselecting the pile,
     * moving all face up cards from a pile to another, and turns top face down card up.
     * @param index the index of the pile that was clicked
     * @precondition 0 <= index < 7
     * @postcondition may modify display or stock, pile, foundation, or waste contents
     */
    public void pileClicked(int index)
    {
        Stack<Card> pile = piles[index];

        // move card from selected foundation to pile if legal
        if (display.isFoundationSelected())
        {
            Stack<Card> foundation = foundations[display.selectedFoundation()];

            if (canAddToPile(foundation.peek(), index))
            {
                pile.push(foundation.pop());
                display.unselect();
            }
        }

        // move card from waste to the selected pile if legal
        else if (display.isWasteSelected() && canAddToPile(waste.peek(), index))
        {
            pile.push(waste.pop());
            display.unselect();
        }

        // select pile if nothing is selected and the top card is shown
        else if (display.isNothingSelected() && !pile.isEmpty() && pile.peek().isFaceUp())
        {
            display.selectPile(index);
        }

        // unselect pile if already selected and was clicked
        else if (display.selectedPile() == index)
        {
            display.unselect();
        }

        // move all faced up cards from selected pile to clicked pile
        else if (display.isPileSelected())
        {
            Stack<Card> remove = removeFaceUpCards(display.selectedPile());

            if (canAddToPile(remove.peek(), index))
            {
                addToPile(remove, index);
                display.unselect();
            }
            else
            {
                addToPile(remove, display.selectedPile());
            }
        }

        // turns faced down card on a pile
        else if (!pile.isEmpty() && !pile.peek().isFaceUp())
        {
            pile.peek().turnUp();
        }
    }

    /**
     * Creates the stock by placing all cards in an ArrayList, then randomly removes
     * and pushes them into the stock.
     * @precondition stock must not be null
     */
    private void createStock()
    {
        ArrayList<Card> shuffle = new ArrayList<Card>();
        for (int i=0; i<4; i++)
        {
            for (int j=1; j<=13; j++)
            {
                shuffle.add(new Card(j, Card.SUITS[i]));
            }
        }

        while (!shuffle.isEmpty())
        {
            stock.push(shuffle.remove((int) (Math.random() * shuffle.size())));
        }
    }

    /**
     * Initial deal of 1 card in the first pile, 2 in the second, ..., 7 in the 7th.
     * Turns the top card of each pile up.
     * @precondition the stock must be shuffled with 52 cards, all piles[i] must not be null
     */
    private void deal()
    {
        for (int i=0; i<7; i++)
        {
            for (int j=0; j<=i; j++)
            {
                piles[i].push(stock.pop());
            }

            piles[i].peek().turnUp();
        }
    }

    /**
     * Deals three cards from the stock to the waste and turns all three up.
     * If less than three cards, deals the whole stock.
     */
    private void dealThreeCards()
    {
        dealCards(3);
    }

    /**
     * Deals given number of card(s) from the stock to the waste and turns it up.
     * @param cardsDeal amount of cards to deal
     * @precondition the stock must not be empty
     */
    private void dealCards(int cardsDeal)
    {
        while (cardsDeal > 0 && !stock.isEmpty())
        {
            waste.push(stock.pop());
            waste.peek().turnUp();
            cardsDeal--;
        }
    }

    /**
     * Resets the stock when empty by filling it with the waste.
     */
    private void resetStock()
    {
        while (!waste.isEmpty())
        {
            waste.peek().turnDown();
            stock.push(waste.pop());
        }
    }

    /**
     * Determines if a card can be added to a pile.
     * @param card card to be added to the pile
     * @param index the index of the pile to be added to
     * @return true if the given card can be legally moved to the top of the given pile
     * @precondition 0 <= index < 7
     */
    private boolean canAddToPile(Card card, int index)
    {
        if (piles[index].isEmpty())
        {
            return card.getRank() == 13;
        }

        Card top = piles[index].peek();

        if (!top.isFaceUp())
        {
            return false;
        }

        return card.isRed() != top.isRed() && card.getRank()+1 == top.getRank();
    }

    /**
     * Removes all face-up cards on the top of the given pile; returns a stack
     * @param index the index of the pile to remove
     * @return a stack containing the cards on the top of the given pile
     * @precondition 0 <= index < 7
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> pile = piles[index];
        Stack<Card> faceUp = new Stack<>();

        while (!pile.isEmpty() && pile.peek().isFaceUp())
        {
            faceUp.push(pile.pop());
        }

        return faceUp;
    }

    /**
     * Removes elements from cards, and adds them to the given pile.
     * @param cards stack of cards to add to the pile
     * @param index the index of the pile where the cards will be added
     * @precondition 0 <= index < 7
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while (!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }

    /**
     * Determines if a card can be added to a foundation.
     * @param card card to be added
     * @param index index of the foundation
     * @return true if the given card can be legally moved to the top of the given foundation
     * @precondition 0 <= index < 4
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        Stack<Card> foundation = foundations[index];

        if (foundation.isEmpty())
        {
            return card.getRank() == 1;
        }
        else
        {
            return foundation.peek().getRank()+1 == card.getRank() &&
                    foundation.peek().getSuit().equals(card.getSuit());
        }
    }

    /**
     * Checks if the game is finished. If it is, alerts the user with a celebration
     * and a game duration.
     */
    private void checkGameFinished()
    {
        int kings = 0;

        for (int i=0; i<4; i++)
        {
            if (!foundations[i].isEmpty() && foundations[i].peek().getRank() == 13)
            {
                kings++;
            }
        }

        if (true)
        {
            display.celebrate("Congratulations! You won Solitaire in " +
                    convertToTimeUnit(System.currentTimeMillis()-startTime) + "!");
        }
    }

    /**
     * Converts duration of game in milliseconds to formatted time unit.
     * @param duration duration of game in milliseconds
     * @return formatted time unit in hours, minutes, and seconds
     */
    private String convertToTimeUnit(long duration)
    {
        duration /= 1000;

        String timeUnit = "";
        final int hrs = 60*60;
        final int min = 60;

        if (duration >= hrs)
        {
            timeUnit += ((duration/hrs) + " hours ");
            duration %= hrs;
        }

        if (duration >= min)
        {
            timeUnit += ((duration/min) + " minutes ");
            duration %= min;
        }

        if (duration > 0)
        {
            timeUnit += (duration + " seconds ");
        }

        return timeUnit.substring(0, timeUnit.length()-1);
    }
}