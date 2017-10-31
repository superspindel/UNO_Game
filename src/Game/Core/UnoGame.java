package Game.Core;

import Game.Items.Cards.Card;
import Game.Items.Cards.CardFactory;
import Server.remotePlayer;
import Server.AbstractServer;

import java.util.ArrayList;

/**
 * UnoGame implements the logic to playing Uno, by using the Rules class and the GameState class a game of Uno
 * Can be played. Handles reading input and the logic with dealing cards and switching turns.
 *
 * @author vikfll-0
 * @version 1
 */

public class UnoGame extends AbstractGame
{
    private GameStateInterface gameState;
    private RulesInterface rules;

    /**
     * Tells the gamestate to change player for next turn and then calls the yourturn method to add to that players
     * transmit to inform of turn to play.
     *
     */
    private void nextPlayer()
    {
        gameState.toNextTurn();
        YourTurn();
    }

    /**
     * Fetch the starting cards amount and Deal that amount of cards,
     * then update the gamestates active card using the rules.
     */
    @Override
    public void startGame() {
        setActive(true);
        DealCards(rules.getStartingCardAmount());
        gameState.updateActiveCard(rules.drawValidStartingCard(gameState.getDeck()));
        YourTurn();
    }

    public UnoGame(AbstractServer server)
    {
        super(server);
    }

    @Override
    public void setGameState(GameStateInterface gameState)
    {
        this.gameState = gameState;
    }

    @Override
    public void setRules(RulesInterface rules)
    {
        this.rules = rules;
    }

    /**
     * Handles input from user, splits it up and determines what to do.
     * @param input     String that should be handled.
     */
    @Override
    public void handleInput(String input)
    {
        if(input.equals("NOCONNECTION"))
            setActive(false);
        else if(isActive())
        {
            String[] inputSplit = input.split("=");
            switch (inputSplit[0])
            {
                case "PLAY":
                    cardsPlayed(inputSplit[1]);
                    break;
                default:
                    declinePlay();
            }
        }
    }

    @Override
    public remotePlayer getActivePlayer() {
        return gameState.getActivePlayer();
    }

    /**
     * Handles the logic with the cards played
     * Builds the card using the CardFactory and then uses the rules to check if cards can be played
     * If they can then it calls method to process the cards.
     * @param input         The input from the user
     */
    private void cardsPlayed(String input)
    {
        ArrayList<Card> cardWantToPlay = new ArrayList<>();
        for (String s:input.split("CARD:"))
        {
            if(!s.equals(""))
            {
                Card newCard = CardFactory.buildCard(s.split(";"));
                if(newCard != null)
                    cardWantToPlay.add(newCard);
            }
        }
        if(canPlayCards(cardWantToPlay))
        {
            processCards(cardWantToPlay);
            if(!checkWinner())
            {
                checkUno(input);
                nextPlayer();
            }
        }
        else
        {
            declinePlay();
        }
    }

    /**
     * Check if the activePlayer is the winner.
     * @return      true if winner, false if not
     */
    private boolean checkWinner()
    {
        remotePlayer activePlayer = gameState.getActivePlayer();
        if(rules.checkWinner(activePlayer))
        {
            gameState.addWinner(activePlayer);
            addGameEndTransmit();
            return true;
        }
        return (gameState.getWinner() != null);
    }

    /**
     * Deal cards to the players, add internally to hand and add externally to the players transmit
     * @param amountOfCards     The amount of cards to deal
     */
    private void DealCards(int amountOfCards)
    {
        ArrayList<remotePlayer> players = gameState.getPlayers();
        for(int dealtCards=0; dealtCards<amountOfCards; dealtCards++)
        {
            for (remotePlayer player:players)
            {
                gameState.addDrawCard(player);
                if(dealtCards == amountOfCards-1)
                    player.addToTransmit("\n");
            }
        }
        gameState.setActivePlayer(rules.getStartingPlayer(gameState.getPlayers()));
    }

    /**
     *  If game ends the transmit is added to the players to notify of winner and then closes the game.
     */
    private void addGameEndTransmit()
    {
        for (remotePlayer player:gameState.getPlayers())
        {
            player.clearTransmit();
            player.addToTransmit("GAMEEND=");
            player.addToTransmit(Integer.toString(gameState.getWinner().getId())+"\n");
        }
        server.send();
        setActive(false);
    }

    /**
     * Transmits that a player has said uno to the remaining players.
     */
    private void transmitUno()
    {
        for(remotePlayer player: gameState.getPlayers())
        {
            if(!player.equals(gameState.getActivePlayer()))
                player.addToTransmit("UNOCALLED="+gameState.getActivePlayer().getId()+"\n");
        }
    }

    /**
     *
     * Transmits to the player that it is the players turn,
     * Also checks if the player can play on the upcoming turn otherwhise draws card for player until play can be done,
     * Also draws card if need to penalize player exist.
     */
    private void YourTurn()
    {
        remotePlayer activePlayer = gameState.getActivePlayer();
        while(!(rules.playerCanPlay(activePlayer, gameState.getActiveCard())) || rules.penalizePlayer(gameState.getActivePlayer()))
        {
            gameState.addDrawCard(gameState.getActivePlayer());
            gameState.getActivePlayer().addToTransmit("\n");
        }
        gameState.getActivePlayer().addToTransmit("YOURTURN\n");
        server.send();
    }

    /**
     * Transmit to player that the play was declined.
     */
    private void declinePlay()
    {
        gameState.getActivePlayer().addToTransmit("DECLINED\n");
        server.send();
    }

    /**
     *  Check rules if player can play the cards
     * @param cardWantToPlay        Cards player want to play
     * @return                      true if possible, false if not
     */
    private boolean canPlayCards(ArrayList<Card> cardWantToPlay)
    {
        return rules.playerCanPlayCards(cardWantToPlay, gameState.getActiveCard(), gameState.getActivePlayer());
    }

    /**
     * Goes through the cards and processes them, tells player that play has been accepted.
     * @param cardsWantToPlay       Cards to be processed
     */
    public void processCards(ArrayList<Card> cardsWantToPlay)
    {
        gameState.getActivePlayer().addToTransmit("ACCEPTED\n");
        gameState.playCards(cardsWantToPlay);
        ArrayList<Card> cardsToProcess = rules.cardsToProcess(cardsWantToPlay);

        for (Card card:cardsToProcess) {
            card.play(this.gameState);
        }
    }

    /**
     * Check if uno was said, if it was said then check if player can say uno and react accordingly.
     * @param input         input from user
     */
    public void checkUno(String input)
    {
        remotePlayer activePlayer = gameState.getActivePlayer();
        String[] splitInput = input.split(";");
        String saidUno = splitInput[splitInput.length-1];
        if (saidUno.toUpperCase().equals("UNO"))
        {
            if(rules.canSayUno(gameState.getActivePlayer()))
            {
                gameState.getActivePlayer().addToTransmit("UNOACCEPT\n");
                activePlayer.setUno();
                transmitUno();
            }
            else
            {
                activePlayer.addToTransmit("UNODECLINE\n");
            }

        }
    }
}
