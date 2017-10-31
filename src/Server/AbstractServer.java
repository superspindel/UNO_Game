package Server;

import Game.Core.AbstractGame;
import Network.ServerConnectorInterface;

import java.util.ArrayList;

/**
 * AbstractServer defines the interface for the Server aswell as includes some specific methods that can be reused
 * when modifying the server design.
 *
 * @author vikfll-0
 * @version 1
 */

public abstract class AbstractServer
{
    ServerConnectorInterface connector;
    AbstractGame game;
    ArrayList<remotePlayer> players;

    /**
     * Start a connection with clients, on the specified port
     * @param port                  The port that the server should listen at
     * @param amountOfClients       The amount of clients that can connect
     */
    abstract void startConnection(int port, int amountOfClients);

    /**
     * Set the specific game that the server will feed input to
     * @param game          The AbstractGame implementation
     */
    abstract void installGame(AbstractGame game);

    /**
     * startGame should start the game.
     */
    abstract void startGame();

    /**
     * Gets input from the activePlayer and retransmits it to the the games handleInput method.
     */
    void getInput() {
        while(game.isActive())
            game.handleInput(connector.getUpdate(game.getActivePlayer()));
    }

    /**
     * Goes through all of the players and transmits the data.
     */
    public void send()
    {
        for (remotePlayer player:players)
        {
            connector.send(player);
        }
    }

    /**
     * Starts the server, sequence that boots up and starts the game
     * @param port              port to open at server
     * @param amountOfClients   Amount of clients to accept
     * @param game              The specific game to launch
     */
    void start(int port, int amountOfClients, AbstractGame game)
    {
        startConnection(port, amountOfClients);
        installGame(game);
        startGame();
    }
}
