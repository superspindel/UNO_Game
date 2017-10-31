package Game.Player;

import Client.AbstractClient;

/**
 * AbstractPlayer is the player that determines what to play in the game.
 * The abstractplayer class defines the basic functionality with abstract methods to be implemented
 * in the different extensions of this class.
 * It contains a:
 * client: that it communicates with to transmit data
 * playerState: that contains the vital information concearning the current state
 * active: boolean representing if the player is active
 * toTransmit: a string that is to be transmitted by the client
 */

public abstract class AbstractPlayer
{
    AbstractClient client;
    PlayerState playerState;
    private boolean active;
    private String toTransmit = "";

    /**
     * Take a turn in the game
     */
    abstract public void playTurn();

    /**
     * Update the playerState given the input
     * @param input         Input that should update the state
     */
    abstract public void updateState(String input);

    /**
     * Add the card to the state given the input parameters
     * @param input         The card that has been drawn
     */
    abstract public void drawCard(String input);

    /**
     * Perform startup and then fetch input from the client
     */
    abstract public void startPlaying();

    /**
     * Your turn has been accepted, update state.
     */
    abstract public void acceptTurn();

    /**
     * Your turn has been declined, deal with it.
     */
    abstract public void declineTurn();

    /**
     * Update from server on if you are uno or not.
     * @param state         boolean representing if you are uno or not
     */
    abstract public void isUno(boolean state);

    /**
     * Endgameinfo from server transmitted to client, display on screen
     * @param endGameInfo           The info concearning winner
     */
    abstract public void endGame(String endGameInfo);

    /**
     * Uno has been called by player
     * @param playerId      PlayerId of player who called uno
     */
    public abstract void unoCalled(String playerId);

    /**
     * Return what to transmit
     * @return      Player transmit string
     */
    public String getToTransmit() {
        return toTransmit;
    }

    /**
     * Clear transmit string
     */
    public void clearTransmit() {
        this.toTransmit = "";
    }

    /**
     * Add the message to the transmit string
     * @param message       Message to be added
     */
    void addToTransmit(String message)
    {
        this.toTransmit += message;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    AbstractPlayer()
    {
        this.playerState = new PlayerState();
    }

    /**
     * Server has sent out that a rule violation has taken place, displayed on screen.
     * @param violation             The violation in question.
     */
    public void ruleViolation(String violation)
    {
        System.out.println("\nRULE VIOLATION:\n"+violation+"\n");
    }

    /**
     * Set the active client of the Abstract player
     * @param client        the client that is used.
     */
    public void setClient(AbstractClient client)
    {
        this.client = client;
    }
}
