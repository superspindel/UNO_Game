package Network;

import Server.remotePlayer;
import java.util.ArrayList;

/**
 * Interface deining the methods that should be implemented for setting up communication on the server side
 */
public interface ServerConnectorInterface
{
    /**
     * Send message to a specific player
     * @param player        Player to transmit to, also contains the message to transmit.
     */
    void send(remotePlayer player);

    /**
     * Get update from the selected played
     * @param player        Player to get input from
     * @return              Return one line read from the player
     */
    String getUpdate(remotePlayer player);

    /**
     * Return the players connected
     * @param numberOfClients       The number of clients that can connect to the game
     * @return                      ArrayList of remotePlayers that have connected.
     */
    ArrayList<remotePlayer> getConnectedPlayers(int numberOfClients);
}
