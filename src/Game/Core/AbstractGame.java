package Game.Core;

import Server.remotePlayer;
import Server.AbstractServer;

/**
 * AbstractGame defines the methods to be implemented in a Game for the server implementation to work.
 * Abstract since it has the basic functionality of setting up a server that the game is hosted on, and checking if the
 * game is active or not.
 *
 * @author vikfll-0
 * @version 1
 */


public abstract class AbstractGame
{
    AbstractServer server;

    /**
     * Should implement the starting sequence of the game
     */
    abstract public void startGame();

    /**
     * Set the internal GameStateInterface to be used by the game.
     *
     * @param gameState     The internal gameStateInterface to be used by the game
     */
    abstract public void setGameState(GameStateInterface gameState);

    /**
     * Set the rules to be used when playing the game.
     *
     * @param rules     RulesInterface that should be used by the game to handle rules.
     */
    abstract public void setRules(RulesInterface rules);

    /**
     * Input that has been sent from the server, that has been collected from the remote clients will be sent to
     * this method. Should handle it as needed.
     *
     * @param input     String that should be handled.
     */
    abstract public void handleInput(String input);
    /**
     * Should return the active player that input is to be gathered from.
     * @return remotePlayer    the activePlayer
     */
    abstract public remotePlayer getActivePlayer();
    private boolean active;

    public AbstractGame(AbstractServer server)
    {
        this.server = server;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
