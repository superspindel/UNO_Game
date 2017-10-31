package Server;

import Game.Items.Cards.Card;
import Game.Items.Hand;
import Game.Items.HandInterface;
import Network.ClientConnectorInterface;
import java.util.ArrayList;

/**
 * remotePlayer is a internal state that contains the vital information about remote players.
 * Is utilized to store transmit data, the specific connection to that player, the players hand and an id.
 *
 * @author vikfll-0
 * @version 1
 */

public class remotePlayer
{
    private ClientConnectorInterface connector;
    private int id;
    private HandInterface hand;
    private String toTransmit;
    private boolean isUno;


    public remotePlayer(ClientConnectorInterface connector, int id)
    {
        this.connector = connector;
        this.id = id;
        this.hand = new Hand();
        this.toTransmit = "";
    }

    public boolean isUno() {
        return isUno;
    }

    public void setUno()
    {
        this.isUno = true;
    }

    public void removeUno()
    {
        this.isUno = false;
    }

    public ClientConnectorInterface getConnector() {
        return connector;
    }

    public void setConnector(ClientConnectorInterface connector) {
        this.connector = connector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Card getCardFromHand(int cardNumber) {
        return hand.getCard(cardNumber);
    }

    public void addToHand(Card card) {
        this.hand.addCard(card);
    }

    public String getToTransmit() {
        return toTransmit;
    }

    public void clearTransmit() {
        this.toTransmit = "";
    }

    public int getHandSize()
    {
        return hand.getSize();
    }

    public boolean handIsEmpty()
    {
        return hand.isEmpty();
    }

    public void addToTransmit(String message)
    {
        this.toTransmit += message;
    }

    public ArrayList<Card> playedCards(ArrayList<Card> cardsPlayed)
    {
        return this.hand.removeCards(cardsPlayed);
    }
}
