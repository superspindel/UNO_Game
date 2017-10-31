package Game.Items.Cards;

import Game.Core.GameStateInterface;

/**
 * Plus 2 card
 *
 * @author vikfll-0
 * @version 1
 */

public class Plus2 extends Colored
{
    /**
     * Set the value to always be [+2]
     */
    public Plus2(){
        this.value = "[+2]";
    }
    public Plus2(String color)
    {
        this.color = color;
        this.value = "[+2]";
    }

    @Override
    public String formatToSend() {
        return "CARD:"+Plus2.class.getTypeName()+";"+this.getValue()+";"+this.getColor();
    }

    /**
     * Alter the gamestate by getting the next player in line and adding draw events to that player
     * Also update the active card.
     * @param gameState The gamestate that the card should alter
     */
    @Override
    public void play(GameStateInterface gameState) {
        for(int cardsToDraw = 0; cardsToDraw<2; cardsToDraw++)
            gameState.addDrawCard(gameState.getNextPlayer());
        gameState.updateActiveCard(this);
    }
}
