package Game.Player;

import Game.Items.Cards.Card;
import Game.Items.Hand;
import Game.Items.HandInterface;

/**
 * Contains vital information about the current state for the player.
 *
 * @author vikfll-0
 * @version 1
 */

class PlayerState
{
    private Card activeCard;
    private HandInterface hand;

    /**
     * Create a hand for the player
     */
    PlayerState()
    {
        this.hand = new Hand();
    }

    /**
     * return the current hand
     * @return          The players hand
     */
    HandInterface getHand() {
        return hand;
    }

    /**
     * Return the active card
     * @return          The last played card
     */
    Card getActiveCard() {
        return activeCard;
    }

    /**
     * Update the active card
     * @param activeCard        The last played card
     */
    void setActiveCard(Card activeCard) {
        this.activeCard = activeCard;
    }



}
