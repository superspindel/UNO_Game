package Game.Items.Cards;

import Game.Core.GameStateInterface;

public class Wild extends Special{

    public Wild()
    {
        this.color = "grey";
        this.value = "[(?)]";
        this.selectedColor = "grey";
    }

    @Override
    public String formatToSend() {
        return "CARD:"+Wild.class.getTypeName()+";"+this.getValue()+";"+this.getColor();
    }

    /**
     * Add this card to the activeCard of the gameState
     * @param gameState     The gamestate that the card should alter
     */
    @Override
    public void play(GameStateInterface gameState) {
            gameState.updateActiveCard(this);
    }
}
