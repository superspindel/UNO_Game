package Game.Player;

import Game.Items.Cards.Card;
import Game.Items.Cards.CardFactory;
import Game.Items.Cards.Special;
import Game.Items.UnoDeck;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Bot is a computer controlled player that plays with the logic given and is not controlled by an end user.
 *
 * @author vikfll-0
 * @version 1
 */


public class Bot extends AbstractPlayer
{

    /**
     * Display hand and play a card, then tell client to transmit the card to the server.
     */
    @Override
    public void playTurn() {
        System.out.println("ACTIVE CARD IS:    "+playerState.getActiveCard().toString());
        if(playerState.getActiveCard() instanceof Special)
            System.out.println("SELECTED COLOR IS: "+((Special) playerState.getActiveCard()).getColor());
        System.out.print("Your current hand:    ");
        playerState.getHand().display();
        System.out.print("\nSelect cards to play: ");
        for(int amountOfCards=0; amountOfCards<playerState.getHand().getSize(); amountOfCards++)
        {
            System.out.print("\t["+amountOfCards+"]");
        }
        System.out.println();
        Card cardToPlay = playCard();
        addToTransmit("PLAY="+playerState.getHand().playCard(cardToPlay).formatToSend());
        if(sayUno())
            addToTransmit(";UNO");
        client.send();
    }

    /**
     * Determine if a bot should say uno
     * @return      True if it should, false if not.
     */
    private boolean sayUno()
    {
        return playerState.getHand().getSize()-1 == 1;
    }

    /**
     * Use CardFactory to build card and then update the state with the newly played card.
     * @param input         Input that should update the state
     */
    @Override
    public void updateState(String input) {
        Card newCard = CardFactory.buildCard(input.split("CARD:")[1].split(";"));
        if(newCard != null)
            playerState.setActiveCard(newCard);
    }

    /**
     * For card in input, build the card and add it the the hand.
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

    /**
     * Set game as active and await input.
     */
    @Override
    public void startPlaying() {
        setActive(true);
        client.handleInput();
    }

    /**
     * Update hand with turn accepted
     */
    @Override
    public void acceptTurn() {
        playerState.getHand().acceptPlay();
    }

    /**
     * Update hand with turn declined and play again.
     */
    @Override
    public void declineTurn() {
        playerState.getHand().declinePlay();
        playTurn();
    }

    /**
     * Display if the bot is uno
     * @param state         boolean representing if you are uno or not
     */
    @Override
    public void isUno(boolean state) {
        if(state)
            System.out.println("YOU ARE UNO!");
        else
            System.out.println("YOU ARE NOT UNO!");
    }

    /**
     * Set the game as inactive and print out the winner.
     * @param endGameInfo           The info concearning winner
     */
    @Override
    public void endGame(String endGameInfo) {
        setActive(false);
        System.out.println("\nGAME HAS ENDED!");
        System.out.println("WINNER: "+endGameInfo);
    }

    @Override
    public void unoCalled(String playerId) {
        System.out.println("UNO HAS BEEN CALLED BY:"+playerId);
    }

    /**
     * Find the first card that can be played, if its a special card, then select a random color
     * @return      The first card that can be played.
     */
    private Card playCard()
    {
        Random rnd = ThreadLocalRandom.current();
        for (int cardNumber=0; cardNumber<playerState.getHand().getSize(); cardNumber++) {
            if(playerState.getHand().getCard(cardNumber).getValue().equals(playerState.getActiveCard().getValue()) || playerState.getActiveCard().getColor().equals(playerState.getHand().getCard(cardNumber).getColor()) || (playerState.getHand().getCard(cardNumber) instanceof Special))
            {
                Card cardToPlay = playerState.getHand().getCard(cardNumber);
                if(cardToPlay instanceof Special)
                {
                    cardToPlay.setColor(UnoDeck.getColorForInt((rnd.nextInt(4)+1)));
                }
                return cardToPlay;
            }
        }
        return null;
    }
}
