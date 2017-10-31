package Network;

/**
 * Interface for setting up a client connection
 */
public interface ClientConnectorInterface
{
    /**
     * Send the message to the connected host
     * @param message           message to transmit
     */
    void send(String message);

    /**
     * Read data from the host
     * @return      read one line and return from the host
     */
    String getUpdate();

    /**
     * Connect to the host
     */
    void connect();

}
