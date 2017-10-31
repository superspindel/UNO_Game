package Game.Core;

import Game.Items.Cards.Card;
import Game.Items.Deck;
import Game.Items.DeckInterface;
import Server.remotePlayer;
import java.util.ArrayList;

/**
 * GameState holds the current state of the game, the game and the cards being played will alter the state of the game.
 *
 *
 * @author vikfll-0
 * @version 1
 */

public class GameState implements GameStateInterface
{
    private DeckInterface deck;
    private Card activeCard;
    private ArrayList<remotePlayer> activePlayers;
    private remotePlayer activePlayer;
    private int playDirection;
    private int numberOfSkips = 0;
    private remotePlayer winner;

    /**
     * Returns the current player
     * @return current active player
     */
    @Override
    public remotePlayer getActivePlayer() {
        return this.activePlayer;
    }

    /**
     * Adds a skip, is used by the skip card to skip players when calculating the next players turn.
     */
    public void addSkip()
    {
        this.numberOfSkips += 1;
    }

    /**
     * Reverse the direction of the gamestate.
     */
    @Override
    public void reverse()
    {
        if (this.playDirection == 1)
            this.playDirection = -1;
        else
            this.playDirection = 1;
    }
    /**
     * Return the current deck
     */
    @Override
    public DeckInterface getDeck() {
        return deck;
    }

    /**
     * Will calculate the nextplayer, depending on the direction. Is used to add drawcards when Plus2 and Plus4 cards
     * are being played.
     * @return      The next player in line
     */
    @Override
    public remotePlayer getNextPlayer() {
        int nextPlayerId = this.activePlayer.getId()+playDirection;
        try
        {
            return this.activePlayers.get(nextPlayerId);
        }
        catch (IndexOutOfBoundsException e)
        {
            if(nextPlayerId < 0)
                return this.activePlayers.get(this.activePlayers.size()-1);
            else
                return this.activePlayers.get(0);
        }
    }

    /**
     * Sets the active player to this player
     * @param player        The player to set as the active player
     */

    @Override
    public void setActivePlayer(remotePlayer player) {
        this.activePlayer = player;
    }

    /**
     * Get next card from current deck
     * @return          the next card in the deck
     */

    @Override
    public Card getNextCard() {
        return this.deck.getNextCard();
    }

    /**
     * Returns the current active card, the last played card.
     * @return      last played card
     */
    @Override
    public Card getActiveCard() {
        return this.activeCard;
    }

    /**
     * Returns the players that are playing
     * @return      Arraylist of players that are playing
     */
    @Override
    public ArrayList<remotePlayer> getPlayers() {
        return this.activePlayers;
    }

    /**
     * Sets the activeCard to this card, also updates the players on this new card being played and adds it to the
     * players transmitlog.
     * @param newActiveCard     The new last played card
     */

    public void updateActiveCard(Card newActiveCard)
    {
        this.activeCard = newActiveCard;
        for (remotePlayer player: this.getPlayers())
        {
            player.addToTransmit("UPDATE="+activeCard.formatToSend()+"\n");
        }
    }

    /**
     * Return a card to the bottom of the deck.
     * @param card      Card to be placed back in the deck.
     */

    @Override
    public void returnCard(Card card)
    {
        this.deck.addToBottom(card);
    }

    /**
     * Calculates, using the amount of skips and the direction, the next player in line to get to play.
     */
    @Override
    public void toNextTurn()
    {
        int currentActivePlayerIndex = activePlayers.indexOf(getActivePlayer());
        int newActivePlayedIndex = currentActivePlayerIndex+(playDirection+playDirection*numberOfSkips);
        while(newActivePlayedIndex<0)
        {
            newActivePlayedIndex = newActivePlayedIndex+activePlayers.size();
        }
        while(newActivePlayedIndex >= activePlayers.size())
            newActivePlayedIndex = newActivePlayedIndex-activePlayers.size();
        activePlayer = activePlayers.get(newActivePlayedIndex);
        this.numberOfSkips = 0;
    }

    /**
     * Add a player as a winner to the gameState
     *
     * @param player           The winning player
     */

    @Override
    public void addWinner(remotePlayer player) {
        this.winner = player;
    }

    /**
     * Returns the winner of the game
     * @return      The winner of the game.
     */

    @Override
    public remotePlayer getWinner() {
        return this.winner;
    }

    /**
     * Cards that have been played during the last turn, gets added to the pile.
     * @param cardsWantToPlay       Cards played during last turn
     */

    @Override
    public void playCards(ArrayList<Card> cardsWantToPlay) {
        deck.cardsToPile(getActivePlayer().playedCards(cardsWantToPlay));
    }

    /**
     * Create a new GameState from the given parameters.
     * ActiveCard, Winner is set to null, playDirection is currently set to +1 as standard.
     *
     * @param deck              The deck that the game should use.
     * @param activePlayers     The arraylist of players to participate in the game.
     */

    public GameState(Deck deck, ArrayList<remotePlayer> activePlayers)
    {
        this.deck = deck;
        this.activePlayers = activePlayers;
        this.activeCard = null;
        this.playDirection = +1;
        this.winner = null;
    }

    /**
     * Draws a card for a player, adds it to the internal players hand and to the transmit log for the server to send
     * to the client. Cards is drawn from the gameStates internal deck.
     *
     * @param player        Player that should draw a card from the deck.
     */

    public void addDrawCard(remotePlayer player)
    {
        player.addToTransmit("DRAW=");
        Card playersCard = getNextCard();
        player.addToTransmit(playersCard.formatToSend());
        player.addToHand(playersCard);
        player.addToTransmit("\n");
        player.removeUno();
    }
}
