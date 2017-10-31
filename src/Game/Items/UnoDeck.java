package Game.Items;

import Game.Items.Cards.*;
import Game.Items.Cards.Number;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vikfll-0
 * @version 1
 */

public class UnoDeck extends Deck
{
    private static String[] colors = {"red", "green", "yellow", "blue"};

    private final String[] numbers =
            {"0", "1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8", "9", "9"};

    private static Map<Integer, String> randomToColor = new HashMap<Integer, String>()
    {{
        put(1, "red");
        put(2, "green");
        put(3, "blue");
        put(4, "yellow");
    }};



    public static String getColorForInt(int number)
    {
        return randomToColor.get(number);
    }

    /**
     * Creates a deck with the cards that represent a unogame.
     */
    public UnoDeck()
    {
        for (String color:colors)
        {
            for(String value: numbers)
            {
                addCard(CardFactory.buildCard(Number.class.getTypeName(), value, color));
            }
            for(int amountOfColoredFunctionCards = 0; amountOfColoredFunctionCards<2; amountOfColoredFunctionCards++)
            {
                addCard(CardFactory.buildCard(Plus2.class.getTypeName(), color));
                addCard(CardFactory.buildCard(Skip.class.getTypeName(), color));
                addCard(CardFactory.buildCard(Reverse.class.getTypeName(), color));
            }
        }
        for(int amountOfSpecialCards = 0; amountOfSpecialCards<4; amountOfSpecialCards++)
        {
            addCard(CardFactory.buildCard(Plus4.class.getTypeName()));
            addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        }
        shuffle();
    }
}
