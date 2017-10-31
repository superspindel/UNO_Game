package Game.Items.Cards;

import Game.Core.GameStateInterface;
import Server.remotePlayer;

/**
 * The plus4 card extends the Special card
 */

public class Plus4 extends Special
{
    public Plus4()
    {
        this.value = "[+4]";
        this.color = "grey";
        this.selectedColor = "grey";
    }
    @Override
    public String formatToSend() {
        return "CARD:"+Plus4.class.getTypeName()+";"+this.getValue()+";"+this.getColor();
    }

    /**
     * Alter the gamestate by getting the next player in line and adding 4 drawcard events to the player, also update
     * the active card.
     * @param gameState     The gameState to be altered.
     */
    @Override
    public void play(GameStateInterface gameState) {
        remotePlayer nextPlayer = gameState.getNextPlayer();
        for(int cardsToDraw = 0; cardsToDraw<4; cardsToDraw++)
            gameState.addDrawCard(nextPlayer);
        gameState.updateActiveCard(this);
    }
}
