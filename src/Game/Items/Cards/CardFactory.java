package Game.Items.Cards;

/**
 * Cardfactory builds card from different values, returns the card specified.
 *
 * @author vikfll-0
 * @version 1
 */

public class CardFactory
{
    /**
     * Returns a card from a StringArray, used when transmitting data.
     * @param cardValues        ["type", "value", "color"] String array
     * @return                  A card of the specified type with the specified value and color
     */
    public static Card buildCard(String[] cardValues)
    {
        try
        {
            Card card = (Card) Class.forName(cardValues[0]).newInstance();
            card.setValue(cardValues[1]);
            card.setColor(cardValues[2]);
            card.resetUnicode();
            return card;
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a card with the given values
     * @param typeName          Type of card to be created
     * @param value             Value of the card
     * @param color             Color of the card
     * @return                  Card with specified value, color and of specified type
     */
    public static Card buildCard(String typeName, String value, String color)
    {
        try
        {
            Card card = (Card) Class.forName(typeName).newInstance();
            card.setValue(value);
            card.setColor(color);
            card.resetUnicode();
            return card;
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a card with the given values
     * @param typeName              Type of card to be created
     * @param color                 Color of the card
     * @return                      Card with color and of specified type
     */
    public static Card buildCard(String typeName, String color)
    {
        try
        {
            Card card = (Card) Class.forName(typeName).newInstance();
            card.setColor(color);
            card.resetUnicode();
            return card;
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a card with the given values
     * @param typeName              Type of card to be created
     * @return                      Return a card of the specific type
     */
    public static Card buildCard(String typeName)
    {
        try
        {
            Card card = (Card) Class.forName(typeName).newInstance();
            card.resetUnicode();
            return card;
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




}
