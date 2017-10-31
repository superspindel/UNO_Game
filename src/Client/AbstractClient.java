package Client;

import Game.Player.AbstractPlayer;
import Network.ClientConnectorInterface;

/**
 * AbstractClient handles most of the core functionality of a client. It handles communication with the AbstractPlayer
 * where the view of the game is presented and defines methods to be used in setting up a Client.
 *
 *
 * @author vikfll-0
 * @version 1
 */

public abstract class AbstractClient
{
    ClientConnectorInterface connector;
    AbstractPlayer player;

    /**
     * connect should set up the AbstractClients connector to a object that can handle transmitting the communication.
     * After connect has been called a valid connection to the server should be installed.
     *
     * @param ip        The ip of the remote server
     * @param port      The port of the remote server
     */
    abstract void connect(String ip, int port);

    /**
     * installPlayer sets up the AbstractPlayer object that the client communicates with and sends the gathered input
     * to.
     *
     * @param player    The abstractPlayer that is to be sent the input from the connector.
     */
    abstract void installPlayer(AbstractPlayer player);

    /**
     * startGame should perform the steps needed by the player in order for the game to start.
     */
    abstract void startGame();

    /**
     * start performs the steps of connecting to the remote server, installation of the new player and then starts the
     * game.
     *
     * @param ip        The ip of the remote server
     * @param port      The port of the remote server
     * @param newPlayer The player that should handle the input and play the game.
     */

    void start(String ip, int port, AbstractPlayer newPlayer)
    {
        connect(ip, port);
        installPlayer(newPlayer);
        startGame();
    }

    /**
     * send will transmit the data that has been stored on the AbstractPlayers transmit string.
     * Then it will clear the transmit.
     */
    public void send() {
        connector.send(player.getToTransmit()+"\n");
        player.clearTransmit();
    }

    /**
     * handleInput will fetch input from the ClientConnectorInterface's getUpdate and call specific methods in the
     * Abstract player depending on the input. It handles closing the game in case of a lost connection and depends on
     * the strict communication protocol that has been constructed.
     */

    public void handleInput()
    {
        while(player.isActive()) {
            String input = connector.getUpdate();
            if (input.equals("NOCONNECTION")) {
                System.out.println("GAME ENDED DUE TO CONNECTION");
                player.setActive(false);
            } else if (player.isActive()) {
                switch (input) {
                    case "YOURTURN":
                        player.playTurn();
                        break;
                    case "DECLINED":
                        player.declineTurn();
                        break;
                    case "ACCEPTED":
                        player.acceptTurn();
                        break;
                    case "UNOACCEPT":
                        player.isUno(true);
                        break;
                    case "UNODECLINE":
                        player.isUno(false);
                        break;
                    default:
                        String[] inputSplit = input.split("=");
                        switch (inputSplit[0]) {
                            case "UPDATE":
                                try {
                                    player.updateState(inputSplit[1]);
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Could not read data to update state");
                                }
                                break;
                            case "DRAW":
                                try {
                                    player.drawCard(inputSplit[1]);
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Could not read card to draw");
                                }
                                break;
                            case "GAMEEND":
                                try {
                                    player.endGame(inputSplit[1]);
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Could not get endgame information");
                                }
                                break;
                            case "UNOCALLED":
                                try
                                {
                                    player.unoCalled(inputSplit[1]);
                                }catch (IndexOutOfBoundsException e)
                                {
                                    System.out.println("Could not read information about uno");
                                }
                            case "RULES":
                                try
                                {
                                    player.ruleViolation(inputSplit[1]);
                                }
                                catch (IndexOutOfBoundsException e)
                                {
                                    System.out.println("Could not read rule violation");
                                }
                        }
                        break;
                }
            }
        }
    }
}
