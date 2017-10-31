package Game.Items.Cards;

import Game.Core.GameStateInterface;

public class Number extends Colored {

    public Number(){}

    public Number(String value, String color)
    {
        this.color = color;
        this.value = value;
    }

    @Override
    public String formatToSend() {
        return "CARD:"+Number.class.getTypeName()+";"+this.getValue()+";"+this.getColor();
    }

    /**
     * Alter the active card to this card.
     * @param gameState The gamestate that the card should alter
     */
    @Override
    public void play(GameStateInterface gameState) {
        gameState.updateActiveCard(this);
    }
}
