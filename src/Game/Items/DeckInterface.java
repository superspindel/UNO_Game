package Game.Items;

import Game.Items.Cards.Card;

import java.util.ArrayList;

/**
 * Deck interface defines the methods that should be implemented when constructing a deck.
 */

public interface DeckInterface
{
    /**
     * Add a card to the deck
     * @param card      Card to be added
     */
    void addCard(Card card);

    /**
     * Shuffle the deck
     */
    void shuffle();

    /**
     * Return the next card in the deck
     * @return      Return the top card
     */
    Card getNextCard();

    /**
     * Add card to bottom of deck
     * @param card      Card to be added to the bottom
     */
    void addToBottom(Card card);

    /**
     * Add cards to the pile
     * @param cards     Cards to be added to the pile
     */
    void cardsToPile(ArrayList<Card> cards);


}
