package Game.Items.Cards;

/**
 * Colored card that starts of with a specific color
 * @author vikfll-0
 * @version 1
 */
public abstract class Colored extends Card
{
    /**
     * Return       the cards value
     * @return      String value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Return the cards color
     * @return      String of color
     */
    public String getColor()
    {
        return this.color;
    }

    /**
     * Set the cards color
     * @param color     String color to set to the card
     */
    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     * Return the string to be printed by the card
     * @return              Card formated for printout
     */
    public String toString() {
        return "\t"+unicodeColor+" " + getValue() + " "+colorUnicode.get("reset");
    }

    /**
     * Set unicode color to the unicode for the cards color
     */
    public void resetUnicode()
    {
        this.unicodeColor = colorUnicode.get(this.color);
    }

}
