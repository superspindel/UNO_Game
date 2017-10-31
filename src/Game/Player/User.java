package Game.Player;

import Game.Items.Cards.Card;
import Game.Items.Cards.CardFactory;
import Game.Items.Cards.Special;
import Game.Items.UnoDeck;
import java.util.Scanner;

/**
 * User is the extension of AbstractPlayer that has an end user controlling what cards to play.
 */

public class User extends AbstractPlayer
{
    /**
     * Display cards in hand and the active card, then wait for user input.
     */
    @Override
    public void playTurn()
    {
        displayState();
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] inputSplit = userInput.split(",");
        handleUserInput(inputSplit);
    }

    /**
     * Displays the active card, the active cards selected color if its a Special card,
     * Also the players hand and what numbers are available when picking cards.
     */
    private void displayState()
    {
        System.out.println("ACTIVE CARD IS:    "+playerState.getActiveCard().toString());
        if(playerState.getActiveCard() instanceof Special)
            System.out.println("SELECTED COLOR IS: "+(playerState.getActiveCard()).getColor());
        System.out.print("Your current hand:    ");
        playerState.getHand().display();
        System.out.print("\nSelect cards to play: ");
        for(int amountOfCards=0; amountOfCards<playerState.getHand().getSize(); amountOfCards++)
        {
            System.out.print("\t["+amountOfCards+"]");
        }
        System.out.println();
    }

    /**
     * Handle the users input, alter the players state depending on the card being played and check if uno has been
     * said. Add information to transmit and send, if invalid input then replay turn.
     *
     * @param inputSplit            The inputSplit that is to be handled.
     */
    private void handleUserInput(String[] inputSplit)
    {
        addToTransmit("PLAY=");
        if(inputSplit.length == 0)
        {
            declineTurn();
        }
        for(String move: inputSplit)
        {
            try
            {
                Card playedCard = playerState.getHand().getCard(Integer.parseInt(move));
                if(playedCard instanceof Special)
                {
                    System.out.println("CHOSE COLOR: \t 1=red \t 2=green \t 3=blue \t 4=yellow");
                    Scanner scanner = new Scanner(System.in);
                    String inputColor = scanner.nextLine();
                    playedCard.setColor(UnoDeck.getColorForInt(Integer.parseInt(inputColor)));
                }
                addToTransmit(playerState.getHand().playCard(Integer.parseInt(move)).formatToSend());
            }
            catch (NumberFormatException  e)
            {
                if(move.equals("UNO"))
                    addToTransmit(";UNO");
                else
                {
                    System.out.println("INVALID INPUT, TRY AGAIN!");
                    declineTurn();
                }
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("Select a valid number from your available cards");
                declineTurn();
            }
        }
        client.send();
    }

    /**
     * Update the state with the new card that was played
     * @param input         Input that should update the state
     */
    @Override
    public void updateState(String input) {
        Card newCard = CardFactory.buildCard(input.split("CARD:")[1].split(";"));
        System.out.println("CARD: "+newCard+ " WAS PLAYED");
        if(newCard != null)
            playerState.setActiveCard(newCard);
    }

    /**
     * Add card to hand by building it from the input
     * @param input         The card that has been drawn
     */
    @Override
    public void drawCard(String input) {
        for (String s:input.split("CARD:"))
        {
            if(!s.equals(""))
            {
                Card newCard = CardFactory.buildCard(s.split(";"));
                if(newCard != null)
                    playerState.getHand().addCard(newCard);
            }
        }
    }


    @Override
    public void startPlaying() {
        this.setActive(true);
        System.out.println("GAME HAS STARTED!");
        client.handleInput();
    }

    @Override
    public void acceptTurn() {
        playerState.getHand().acceptPlay();
    }

    @Override
    public void declineTurn() {
        clearTransmit();
        playerState.getHand().declinePlay();
        playTurn();
    }

    @Override
    public void isUno(boolean state) {
        if(state)
            System.out.println("\n YOU ARE UNO!");
        else
            System.out.println("\n YOU ARE NOT UNO!");
    }

    @Override
    public void endGame(String endGameInfo) {
        setActive(false);
        System.out.println("GAME HAS ENDED!");
        System.out.println("WINNER: "+endGameInfo);
    }

    @Override
    public void unoCalled(String playerId) {
        System.out.println("UNO HAS BEEN CALLED BY:"+playerId);
    }
}
