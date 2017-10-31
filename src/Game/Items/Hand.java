package Game.Items;

import Game.Items.Cards.Card;
import Game.Items.Cards.Special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the handinterface
 *
 * @author vikfll-0
 * @version 1
 */

public class Hand implements HandInterface {

    ArrayList<Card> cards;
    ArrayList<Card> playedCards;

    private static Map<String, Integer> colorSortValue = new HashMap<String, Integer>()
    {{
        put("RED", 1);
        put("GREEN", 2);
        put("YELLOW", 3);
        put("BLUE", 4);
        put("GREY", 5);
    }};
    private static Map<String, Integer> valueMap = new HashMap<String, Integer>()
    {{
        put("0", 0);
        put("1", 1);
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("[+2]", 10);
        put("[</>]", 11);
        put("[(X)]", 12);
        put("[(?)]", 13);
        put("[+4]", 14);
    }};

    public Hand()
    {
        this.cards = new ArrayList<>();
        this.playedCards = new ArrayList<>();
    }

    /**
     * Sort the hand and then print cards out
     */
    @Override
    public void display()
    {
        sort();
        for (Card card:cards)
        {
            System.out.print(card);
        }
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public void removeCard(Card card) {
        cards.remove(card);
    }

    /**
     * Cards that have been played and therefor set to the pile
     * @param cards         cards to be removed
     * @return              The cards specified
     */
    @Override
    public ArrayList<Card> removeCards(ArrayList<Card> cards)
    {
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        for(Card removeCard: cards)
        {
            for(Card handCard: this.cards)
            {
                if(handCard.getValue().equals(removeCard.getValue()) && handCard.getColor().equals(removeCard.getColor()) || (handCard instanceof Special) && (removeCard instanceof Special) && (handCard.getValue().equals(removeCard.getValue())))
                {
                    cardsPlayed.add(handCard);
                    this.cards.remove(handCard);
                    break;
                }
            }
        }
        return cardsPlayed;
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public Card getCard(int cardNumber) {
        return cards.get(cardNumber);
    }

    @Override
    public int getSize() {
        return cards.size();
    }

    /**
     * Sort the hand depending on the value and color of the card.
     */
    @Override
    public void sort() {
        ArrayList<Card> newHand = new ArrayList<>();
        while(!cards.isEmpty())
        {
            Card bestcard = cards.get(0);
            int bestcardpos = 0;
            for(int i = 0; i<cards.size(); i++)
            {
                if (bestcard == null)
                {
                    bestcard = cards.get(i);
                    bestcardpos = i;
                }
                if(compareColors(bestcard.getColor(), cards.get(i).getColor()))
                {
                    bestcard = cards.get(i);
                    bestcardpos = i;
                }
                if(getValue(bestcard.getValue()) < getValue(cards.get(i).getValue()) && bestcard.getColor().equals(cards.get(i).getColor()))
                {
                    bestcard = cards.get(i);
                    bestcardpos = i;
                }
            }
            newHand.add(cards.remove(bestcardpos));
        }
        this.cards = newHand;
    }

    @Override
    public Card playCard(int cardNumber) {
        Card playedCard = cards.get(cardNumber);
        playedCards.add(playedCard);
        return playedCard;
    }

    @Override
    public Card playCard(Card card) {
        int cardNumber = cards.indexOf(card);
        return playCard(cardNumber);
    }

    /**
     * Remove the cards stored in the playedCards list from the main cards list.
     */
    @Override
    public void acceptPlay()
    {
        cards.removeAll(playedCards);
    }

    @Override
    public void declinePlay()
    {
        playedCards = new ArrayList<>();
    }

    /**
     * Used in the sorting of cards, returns a int for the value of the card
     * @param value         String value to be checked
     * @return              int for that value
     */
    private int getValue(String value)
    {
        return valueMap.get(value);
    }

    /**
     * Check colors
     * @param color1            Color of first card
     * @param color2            Color of second card
     * @return                  Return if color1 is bigger then color 2
     */
    private boolean compareColors(String color1, String color2)
    {
        return colorSortValue.get(color1.toUpperCase()) > colorSortValue.get(color2.toUpperCase());
    }
}
