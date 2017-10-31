package Game.Items.Cards;

import Game.Core.GameStateInterface;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic Card structure, abstract methods for the card to be implemented in implemented cards,
 * Contains the methods to set a value used by the factory when creating cards from transmitted text and the
 * map for color to unicode of color.
 *
 * @author vikfll-0
 * @version 1
 */

public abstract class Card {

    String value;
    String color;
    String unicodeColor;
    final Map<String, String> colorUnicode = new HashMap<String, String>() {{
        put("red", "\u001B[101m\033[30m\u001B[1m");
        put("green", "\u001B[102m\033[30m\u001B[1m");
        put("yellow", "\u001B[103m\033[30m\u001B[1m");
        put("blue", "\u001B[106m\033[30m\u001B[1m");
        put("grey", "\u001B[47m\033[30m\u001B[1m");
        put("reset", "\u001B[0m");
    }};


    /**
     * Return the value of the card
     *
     * @return String for value
     */
    abstract public String getValue();

    /**
     * Return the color of the card
     *
     * @return String for color
     */
    abstract public String getColor();

    /**
     * Return the card formatted to the specified structure:
     * "CARD:typename;value;color"
     *
     * @return Card formatted to : "CARD:typename;value;color"
     */
    abstract public String formatToSend();

    /**
     * Format the card for output on screen
     *
     * @return The string output to be printed on screen
     */
    abstract public String toString();

    /**
     * Play method is a form of command pattern, where when processing cards the card itself will alter the gamestate
     * depending on its functionality.
     *
     * @param gameState The gamestate that the card should alter
     */
    abstract public void play(GameStateInterface gameState);

    /**
     * Set the color of card
     *
     * @param value The String color
     */
    abstract public void setColor(String value);

    /**
     * When sending card, we will not transmit the unicode data, resetUnicode will fetch and reset the unicode information
     * on the card from the selected color
     */
    abstract public void resetUnicode();

    /**
     * Set the value of the card
     *
     * @param value Value of card
     */
    public void setValue(String value) {
        this.value = value;
    }


}

