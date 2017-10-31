package Game.Items;

import Game.Items.Cards.Card;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Deck implements the DeckInterface and handles the basics of the deck.
 */

public class Deck implements DeckInterface
{
    private ArrayList<Card> deck;
    private ArrayList<Card> discardPile;

    public Deck()
    {
        this.deck = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    /**
     * Add card to arraylist
     * @param card      Card to be added
     */
    @Override
    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * Shuffle the cards
     */
    @Override
    public void shuffle()
    {
        Random rnd = ThreadLocalRandom.current();
        ArrayList<Card> newdeck = new ArrayList<>();
        while(deck.size()>0)
        {
            if((deck.size()-1) != 0)
                newdeck.add(deck.remove(rnd.nextInt(deck.size()-1)));
            else
                newdeck.add(deck.remove(0));
        }
        this.deck = newdeck;
    }

    /**
     * Return the next card in the deck, if its empty, shuffle the pile and make it the new deck
     * @return  next card in deck
     */
    @Override
    public Card getNextCard() {
        try
        {
            return deck.remove(0);
        }
        catch (IndexOutOfBoundsException e)
        {
            this.deck = discardPile;
            this.discardPile = new ArrayList<>();
            shuffle();
            return deck.remove(0);
        }
    }

    /**
     * Add the card to the last place in the deck
     * @param card      Card to be added to the bottom
     */
    @Override
    public void addToBottom(Card card) {
        deck.add(deck.size(), card);
    }

    /**
     * Add the list of cards to the discardPile arraylist
     * @param cards     Cards to be added to the pile
     */
    @Override
    public void cardsToPile(ArrayList<Card> cards) {
        discardPile.addAll(cards);
    }
}
