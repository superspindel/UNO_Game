package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * ClientConnector is the class that implements the peer 2 peer communication between server and client,
 * The server has one for each client, and the client has one for the server.
 */

public class ClientConnector implements ClientConnectorInterface
{
    private Socket socket;
    private BufferedReader inputStream;
    private DataOutputStream outputStream;
    private String ip;
    private int port;

    public ClientConnector(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Constructor used by the ServerConnector when setting up connections to different players
     * @param socket            The socket to the client
     * @param inputStream       The inputStream from the client
     * @param outputStream      The outputStream to the client
     */
    ClientConnector(Socket socket, BufferedReader inputStream, DataOutputStream outputStream)
    {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /**
     * Transmit the message to the connected socket.
     * @param message           Message  to transmit
     */
    @Override
    public void send(String message) {
        try
        {
            outputStream.writeBytes(message);
        }
        catch (IOException e)
        {
            System.out.println("Could not send data through socket");
        }
    }

    /**
     * Get a string message from the socket and return it
     * @return    the String line read from the socket.
     */
    @Override
    public String getUpdate() {
        try {
            return inputStream.readLine();
        }
        catch (IOException e)
        {
            System.out.println("Problem getting update from sockets BufferedReader"+e);
            return "NOCONNECTION";
        }
    }

    /**
     * Connect from the client to server.
     * Sets up the socket, outputstream and inputstream for the client to the server.
     */
    @Override
    public void connect() {
        boolean isConnected = false;
        while(!isConnected)
        {
            try
            {
                this.socket = new Socket(this.ip, this.port);
                this.outputStream = new DataOutputStream(this.socket.getOutputStream());
                this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                System.out.println("Connected to server.");
                isConnected = true;
            }
            catch (IOException ignored)
            {
                /*
                Will throw exception until it connects to server so no need to display;
                 */
            }

        }
    }
}
