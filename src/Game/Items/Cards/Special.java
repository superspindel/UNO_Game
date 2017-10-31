package Game.Items.Cards;

import Game.Core.GameStateInterface;

/**
 * Special Card that is extended by the Wild and Plus4 card
 *
 * @author vikfll-0
 * @version 1
 */

public abstract class Special extends Card
{
    String selectedColor;

    abstract public String formatToSend();

    /**
     * Alter the gamestate depending on the cards functionality
     * @param gameState The gamestate that the card should alter
     */
    abstract public void play(GameStateInterface gameState);

    public String getValue()
    {
        return this.value;
    }

    /**
     * Return the selected color of the card instead of its original
     * @return          Returns the selected color of the card
     */
    public String getColor()
    {
        return this.selectedColor;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Set the selected color of the card, when transmitting data this method will be used by the cardfactory
     * @param color             The color that has been selected for the card
     */
    public void setColor(String color)
    {
        this.selectedColor = color;
    }

    public void resetUnicode()
    {
        this.unicodeColor = colorUnicode.get("grey");
    }

    public String toString() {
        return "\t"+unicodeColor+" " + getValue() + " "+colorUnicode.get("reset");
    }

}
