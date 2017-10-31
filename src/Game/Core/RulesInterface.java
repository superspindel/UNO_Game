package Game.Core;

import Game.Items.Cards.Card;
import Game.Items.Deck;
import Game.Items.DeckInterface;
import Server.remotePlayer;

import java.util.ArrayList;

/**
 * Interface that defines structure of the rulebook.
 *
 * @author vikfll-0
 * @version 1
 */

public interface RulesInterface
{
    /**
     * Checks if a player is allowed to say uno.
     *
     * @param player        Player that is attempting to say uno
     * @return      <code>true</code> if player has 1 card left, <code>false</code> if player does not have 1 card in hand
     */
   boolean canSayUno(remotePlayer player);

    /**
     * Check if the player has won, hand should be empty and the player should have said uno.
     * @param player        Player to check if winner
     * @return      <code>true</code> if players hand is empty and player has said uno, <code>false</code> otherwise.
     */
   boolean checkWinner(remotePlayer player);

    /**
     * Check if a player can play with its current hand.
     *
     * @param player            Player to check if play is available
     * @param activeCard        The current active card
     * @return                  True if possible, false if not
     */
   boolean playerCanPlay(remotePlayer player, Card activeCard);

    /**
     * Check if cards is a valid starting card.
     * @param card          Card to check
     * @return          return true if Number card with value 0-9
     */
   boolean validStartingCard(Card card);

    /**
     * Draw a startingCard from the deck.
     * @param deck          The deck to draw the startingCard from.
     * @return              The first card in the deck.
     */
   Card drawStartingCard(DeckInterface deck);

    /**
     * Draw a valid startingCard from the deck, should result in a valid card.
     * @param deck          Deck that card should be drawn from
     * @return              A valid card in accordance with <code>validStartingCard</code> method.
     */
   Card drawValidStartingCard(DeckInterface deck);

    /**
     * Check if a card can be played on the activeCard
     * @param cardToPlay        Card that should be checked if possible to play
     * @param activeCard        The current active card
     * @return                  <code>true</code> if possible, <code>false</code> otherwise.
     */
   boolean canPlayCard(Card cardToPlay, Card activeCard);

    /**
     * Check if a player can play multiple cards. Checks so that player does not finish the game by playing multiple
     * cards and checks so that the different cards adhere to the requirements set up in the description.
     *
     * @param cardWantToPlay        Arraylist of cards that wants to be played
     * @param activeCard            The active card
     * @param player                The player that wants to play the cards
     * @return                      True if possible, false otherwise.
     */
   boolean playerCanPlayCards(ArrayList<Card> cardWantToPlay, Card activeCard, remotePlayer player);

    /**
     * Defines the rules on if cards should be stackable
     *
     * @param cardsWantToPlay       Cards want to play
     * @return                      The cards that should be processed.
     */
   ArrayList<Card> cardsToProcess(ArrayList<Card> cardsWantToPlay);

    /**
     * Returns the startingPlayer, current implementation sets player 0 but could be changed.
     *
     * @param players           Arraylist of active players
     * @return                  The starting player
     */
   remotePlayer getStartingPlayer(ArrayList<remotePlayer> players);
    /**
     * Return the amount of cards that should be dealt to players at the start.
     * @return      The amount of cards to deal at the start.
     */
   int getStartingCardAmount();
    /**
     * Checks to see if player should be penalized for not saying uno
     * @param player            The player to check
     * @return                  True if player has 1 card and has not said uno, false otherwise.
     */
   boolean penalizePlayer(remotePlayer player);

    /**
     * The deck that should be used
     *
     * @return          A deck that the game can use.
     */
   Deck getDeck();
}
