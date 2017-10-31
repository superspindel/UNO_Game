package Game.Core;

import Game.Items.Cards.Card;
import Game.Items.Deck;
import Game.Items.DeckInterface;
import Server.remotePlayer;

import java.util.ArrayList;

/**
 * GameStateInterface is an interface for representing the state of a game of UNO.
 *
 * @author vikfll-0
 * @version 1
 */

public interface GameStateInterface
{
    /**
     * Returns the active player
     * @return     Active player
     */
    remotePlayer getActivePlayer();

    /**
     * Return the next player in line
     * @return      The player after the active player
     */
    remotePlayer getNextPlayer();

    /**
     * Set the current active player
     * @param player        The player that should be active
     */
    void setActivePlayer(remotePlayer player);

    /**
     * Return the next card in the deck
     * @return      The next card in the deck
     */
    Card getNextCard();

    /**
     * Return the last played card
     * @return      The last played card
     */
    Card getActiveCard();

    /**
     * Return the active players
     * @return      Arraylist of players
     */
    ArrayList<remotePlayer> getPlayers();

    /**
     * handle a skip card being played
     */
    void addSkip();

    /**
     * Handle a reverse card being played
     */
    void reverse();

    /**
     * Set the new active card and update players on the update
     * @param newActiveCard     The new card that has been played
     */
    void updateActiveCard(Card newActiveCard);

    /**
     * return a card to the deck
     * @param card      Card to be placed back in the deck
     */
    void returnCard(Card card);

    /**
     * Get the deck that is being used
     * @return  The Deck that is being used in the game
     */
    DeckInterface getDeck();

    /**
     * Set the active player to the next turn, handle skips and reverses
     */
    void toNextTurn();

    /**
     * Set the winner
     * @param player    The winner of the game
     */
    void addWinner(remotePlayer player);

    /**
     * Return the winner
     * @return      The player that won
     */
    remotePlayer getWinner();

    /**
     * Update gamestate with these cards being played
     * @param cardsWantToPlay       The cards that have been played
     */
    void playCards(ArrayList<Card> cardsWantToPlay);

    /**
     * Draw cards to a player, handle internal players hand and add transmit information to the specific player.
     *
     * @param player        The player that should draw a card
     */
    void addDrawCard(remotePlayer player);
}
