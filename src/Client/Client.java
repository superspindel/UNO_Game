package Client;

import Game.Player.Bot;
import Game.Player.AbstractPlayer;
import Game.Player.User;
import Network.ClientConnector;

/**
 * Client implements a ClientConnector for its connection and starting of the game is implemented.
 * It extends AbstractClient and therefore gets the functionality to send and fetch input from that abstract class.
 *
 * @author vikfll-0
 * @version 1
 */

public class Client extends AbstractClient
{
    /**
     * Set the connector to a new ClientConnector using the specified ip and port.
     *
     * @param ip        The ip of the remote server
     * @param port      The port of the remote server
     */

    @Override
    public void connect(String ip, int port) {
        connector = new ClientConnector(ip, port);
        connector.connect();
    }

    /**
     *
     * @param player    The abstractPlayer that is to be sent the input from the connector.
     */

    @Override
    public void installPlayer(AbstractPlayer player) {
            this.player = player;
    }

    /**
     * Setup so that the player can communicate with the client and then call the players startPlaying method.
     */

    @Override
    public void startGame()
    {
        player.setClient(this);
        player.startPlaying();
    }

    /**
     * Handles starting of the client, specify a argument of BOT or USER when starting to either have the client be
     * played by a user or a bot.
     *
     * @param args      1 argument, BOT or USER to set up the player as a bot or user.
     */

    public static void main(String[] args)
    {
        AbstractPlayer clientPlayer;
        if(args.length != 1)
        {
            System.out.println("To start client, please input 1 argument");
            System.out.println("accepted inputs: BOT, USER");
        }
        else
        {
            switch (args[0]) {
                case "BOT":
                    clientPlayer = new Bot();
                    break;
                case "USER":
                    clientPlayer = new User();
                    break;
                default:
                    System.out.println("To start client, please input 1 argument");
                    System.out.println("accepted inputs: BOT, USER");
                    return;
            }
            Client client = new Client();
            client.start("localhost", 8887, clientPlayer);
        }
    }

}
