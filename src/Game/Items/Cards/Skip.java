package Game.Items.Cards;

import Game.Core.GameStateInterface;

public class Skip extends Colored {

    public Skip(){
        this.value = "[(X)]";
    }
    public Skip(String color)
    {
        this.color = color;
        this.value = "[(X)]";
    }

    @Override
    public String formatToSend() {
        return "CARD:"+Skip.class.getTypeName()+";"+this.getValue()+";"+this.getColor();
    }

    /**
     * Alter the gamestate by adding a skip that should be handled when determening the next players turn.
     * @param gameState The gamestate that the card should alter
     */
    @Override
    public void play(GameStateInterface gameState) {
        gameState.addSkip();
        gameState.updateActiveCard(this);
    }
}
