package Network;

import Server.remotePlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ServerConnector implements the ServerConnectorInterface using sockets
 *
 * @author vikfll-0
 * @version 1
 */

public class ServerConnector implements ServerConnectorInterface
{
    private ServerSocket socket;

    /**
     * Create a serverSocket on the port specified
     * @param port          The port for the server socket
     */
    public ServerConnector(int port)
    {
        try
        {
            this.socket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.out.println("Could not create a server socket on port: "+port);
        }
    }

    /**
     * Accept clients and add them to an arraylist of players, return that list once all connection has been accepted.
     * @param numberOfClients       The number of clients that can connect to the game
     * @return                      Arraylist with all the connected players.
     */
    @Override
    public ArrayList<remotePlayer> getConnectedPlayers(int numberOfClients)
    {
        ArrayList<remotePlayer> connectedPlayers = new ArrayList<>();
        try
        {
            for(int i = 0; i<numberOfClients; i++)
            {
                Socket clientSocket = this.socket.accept();
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                ClientConnector clientConnector = new ClientConnector(clientSocket, inputStream, outputStream);
                remotePlayer newPlayer = new remotePlayer(clientConnector, i);
                connectedPlayers.add(newPlayer);
            }
            return connectedPlayers;
        }
        catch (IOException e)
        {
            System.out.println("Could not accept connection from client");
            return null;
        }
    }

    /**
     * Send the data specified in the players toTransmit and send it through the players clientConnectorInterface
     * @param player        Player to transmit to, also contains the message to transmit.
     */
    @Override
    public void send(remotePlayer player) {
        if(!player.getToTransmit().equals(""))
        {
            player.getConnector().send(player.getToTransmit()+"\n");
            player.clearTransmit();
        }
    }

    /**
     * Fetch data from the players socket and return one line as a string
     * @param player        Player to get input from
     * @return              One line read from the player in the form of a string
     */
    @Override
    public String getUpdate(remotePlayer player) {
        return player.getConnector().getUpdate();
    }
}
