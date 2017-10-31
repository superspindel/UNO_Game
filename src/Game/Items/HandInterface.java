package Game.Items;

import Game.Items.Cards.Card;

import java.util.ArrayList;

/**
 * Interface for a hand of cards
 *
 * @author vikfll-0
 * @version 1
 */

public interface HandInterface
{
    /**
     * Print out the cards in hand
     */
    void display();

    /**
     * Add card to hand
     * @param card      Card to be added to hand
     */
    void addCard(Card card);

    /**
     * Remove card from hand
     * @param card      Card to be removed
     */
    void removeCard(Card card);

    /**
     * Return card from hand with numer
     * @param cardNumber        Number of the card to get
     * @return                  Card at the specified number
     */
    Card getCard(int cardNumber);

    int getSize();

    /**
     * Sort the hand
     */
    void sort();

    /**
     * Set the cards as played
     * @param cardNumber        The cardnumber of the played card
     * @return                  The specific card
     */
    Card playCard(int cardNumber);

    /**
     * Remove saved played cards
     */
    void acceptPlay();

    /**
     * Return the cards played to hand
     */
    void declinePlay();

    /**
     * Remove multiple cards from hand
     * @param cards         cards to be removed
     * @return              The cards removed
     */
    ArrayList<Card> removeCards(ArrayList<Card> cards);
    boolean isEmpty();

    /**
     * Play a card
     * @param card      Card to be played
     * @return          The card that was played
     */
    Card playCard(Card card);
}
