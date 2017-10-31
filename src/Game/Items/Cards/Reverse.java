package Game.Items.Cards;

import Game.Core.GameStateInterface;

/**
 * Reverse card
 */

public class Reverse extends Colored {

    public Reverse(){
        this.value = "[</>]";
    }
    public Reverse(String color)
    {
        this.color = color;
        this.value = "[</>]";
    }

    @Override
    public String formatToSend() {
        return "CARD:"+Reverse.class.getTypeName()+";"+this.getValue()+";"+this.getColor();
    }

    /**
     * Alter the gamestate by calling the reverse method and changing the direction.
     * @param gameState The gamestate that the card should alter
     */
    @Override
    public void play(GameStateInterface gameState) {
        gameState.reverse();
        gameState.updateActiveCard(this);
    }
}
