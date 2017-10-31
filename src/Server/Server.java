package Server;

import Game.Core.*;
import Network.ServerConnector;

/**
 * Server is extension of the AbstractServer and implements the abstract methods required to handle
 * setting up the server with the game and setting up players.
 *
 * @author vikfll-0
 * @version 1
 */

public class Server extends AbstractServer {

    /**
     * Creates the server connection and then accepts and sets the players.
     * @param port                  The port that the server should listen at
     * @param amountOfClients       The amount of clients that can connect
     */
    @Override
    public void startConnection(int port, int amountOfClients) {
        this.connector = new ServerConnector(port);
        this.players = connector.getConnectedPlayers(amountOfClients);
    }

    /**
     * Installs the game specified, Creates a new rules object and sets the rules on the game,
     * creates a gamestate and sends that to the game aswell.
     * @param game          The AbstractGame implementation
     */
    @Override
    public void installGame(AbstractGame game) {
        this.game = game;
        RulesInterface rules = new Rules();
        this.game.setRules(rules);
        GameState gameState = new GameState(rules.getDeck(), this.players);
        this.game.setGameState(gameState);
    }

    /**
     * Run through startup sequence on game and then wait for input.
     */
    @Override
    public void startGame() {
        game.startGame();
        getInput();
    }

    /**
     * Create and run a server, input an integer that is the amount of clients for the game.
     * @param args          one integer, amount of clients.
     */
    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.out.println("To start server please input number of clients to allow");
            System.out.println("accepted inputs: a positive integer");
        }
        else
        {
            try
            {
                int amountOfClients = Integer.parseInt(args[0]);
                if(amountOfClients < 0)
                {
                    throw new NumberFormatException();
                }
                Server server = new Server();
                UnoGame game = new UnoGame(server);
                server.start(8887, amountOfClients, game);

            }
            catch (NumberFormatException e)
            {
                System.out.println("Please input valid positive integer \n EXAMPLE: 4");
            }
        }
    }

}
