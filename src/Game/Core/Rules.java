package Game.Core;

import Game.Items.Cards.Card;
import Game.Items.Cards.Number;
import Game.Items.Cards.Special;
import Game.Items.Deck;
import Game.Items.DeckInterface;
import Game.Items.UnoDeck;
import Server.remotePlayer;
import java.util.ArrayList;

/**
 * Rules is the class where the game rules are defined, this includes what cards are allowed, if a player can say uno,
 * if a player should be penalized, when a player have won etc.
 *
 * @author vikfll-0
 * @version 1
 */

public class Rules implements RulesInterface
{
    private int startingCardsAmount = 7;

    /**
     * Allowed values of the starting cards, used to only allow numbered cards between 0-9.
     */
    private ArrayList<String> allowedStartingCardValues = new ArrayList<String>()
    {{
        add("0");
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
        add("6");
        add("7");
        add("8");
        add("9");
    }};

    /**
     * Checks if a player is allowed to say uno.
     *
     * @param player        Player that is attempting to say uno
     * @return      <code>true</code> if player has 1 card left, <code>false</code> if player does not have 1 card in hand
     */
    public boolean canSayUno(remotePlayer player)
    {
        return player.getHandSize() == 1;
    }

    /**
     * Check if the player has won, hand should be empty and the player should have said uno.
     * @param player        Player to check if winner
     * @return      <code>true</code> if players hand is empty and player has said uno, <code>false</code> otherwise.
     */
    public boolean checkWinner(remotePlayer player)
    {
        return player.handIsEmpty() && player.isUno();
    }

    /**
     * Check if a player can play with its current hand.
     *
     * @param player            Player to check if play is available
     * @param activeCard        The current active card
     * @return                  True if possible, false if not
     */
    public boolean playerCanPlay(remotePlayer player, Card activeCard)
    {
        for (int cardChecked = 0; cardChecked<player.getHandSize(); cardChecked++)
        {
            if(canPlayCard(player.getCardFromHand(cardChecked),activeCard))
                return true;
        }
        return false;
    }

    /**
     * Check if cards is a valid starting card.
     * @param card          Card to check
     * @return          return true if Number card with value 0-9
     */
    public boolean validStartingCard(Card card)
    {
        return (card instanceof Number && allowedStartingCardValues.contains(card.getValue()));
    }

    /**
     * Draw a startingCard from the deck.
     * @param deck          The deck to draw the startingCard from.
     * @return              The first card in the deck.
     */
    public Card drawStartingCard(DeckInterface deck)
    {
        return deck.getNextCard();
    }

    /**
     * Draw a valid startingCard from the deck, should result in a valid card.
     * @param deck          Deck that card should be drawn from
     * @return              A valid card in accordance with <code>validStartingCard</code> method.
     */
    public Card drawValidStartingCard(DeckInterface deck)
    {
        Card firstCard = drawStartingCard(deck);
        while(!allowedStartingCardValues.contains(firstCard.getValue()))
        {
            deck.addToBottom(firstCard);
            firstCard = drawStartingCard(deck);
        }
        return firstCard;
    }

    /**
     * Check if a card can be played on the activeCard
     * @param cardToPlay        Card that should be checked if possible to play
     * @param activeCard        The current active card
     * @return                  <code>true</code> if possible, <code>false</code> otherwise.
     */
    public boolean canPlayCard(Card cardToPlay, Card activeCard)
    {
        if(cardToPlay instanceof Special)
            return true;
        else if(cardToPlay.getColor().equals(activeCard.getColor()))
            return true;
        else if(cardToPlay.getValue().equals(activeCard.getValue()))
            return true;
        return false;
    }

    /**
     * Check if a player can play multiple cards. Checks so that player does not finish the game by playing multiple
     * cards and checks so that the different cards adhere to the requirements set up in the description.
     *
     * @param cardWantToPlay        Arraylist of cards that wants to be played
     * @param activeCard            The active card
     * @param player                The player that wants to play the cards
     * @return                      True if possible, false otherwise.
     */
    public boolean playerCanPlayCards(ArrayList<Card> cardWantToPlay, Card activeCard, remotePlayer player)
    {
        if(cardWantToPlay.size()>1 && player.getHandSize()-cardWantToPlay.size() == 0)
        {
            return false;
        }
        Card firstCard = cardWantToPlay.get(0);
        if(canPlayCard(firstCard, activeCard))
        {
            for (Card card:cardWantToPlay)
            {
                if(!(card.getValue().equals(firstCard.getValue())))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Defines the rules on if cards should be stackable
     *
     * @param cardsWantToPlay       Cards want to play
     * @return                      The cards that should be processed.
     */
    public ArrayList<Card> cardsToProcess(ArrayList<Card> cardsWantToPlay)
    {
        return cardsWantToPlay;
    }

    /**
     * Returns the startingPlayer, current implementation sets player 0 but could be changed.
     *
     * @param players           Arraylist of active players
     * @return                  The starting player
     */
    public remotePlayer getStartingPlayer(ArrayList<remotePlayer> players)
    {
        return players.get(0);
    }

    /**
     * Return the amount of cards that should be dealt to players at the start.
     * @return      The amount of cards to deal at the start.
     */
    public int getStartingCardAmount() {
        return startingCardsAmount;
    }

    /**
     * Checks to see if player should be penalized for not saying uno
     * @param player            The player to check
     * @return                  True if player has 1 card and has not said uno, false otherwise.
     */
    public boolean penalizePlayer(remotePlayer player)
    {
        return player.getHandSize() == 1 && !player.isUno();
    }

    /**
     * The deck that should be used
     *
     * @return          A deck that the game can use.
     */
    public Deck getDeck()
    {
        return new UnoDeck();
    }
}
