package Tests;

import static org.junit.Assert.*;

import Game.Core.GameState;
import Game.Core.Rules;
import Game.Core.UnoGame;
import Game.Items.Cards.*;
import Game.Items.Cards.Number;
import Game.Items.Deck;
import Game.Items.UnoDeck;
import Network.ClientConnector;
import Server.Server;
import Server.remotePlayer;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * TestRequirements is the test class for this unoGame, it handles all tests to meet the set requirements.
 *
 * @author vikfll-0
 * @version 1
 *
 */

public class TestRequirements
{
    private Rules rules;
    private remotePlayer player;
    private Deck deck;


    /**
     * Before every test a new set of rules, a new player and a new Unodeck is created.
     *
     */
    @Before
    public void newRules()
    {
        this.rules = new Rules();
        this.player = new remotePlayer(new ClientConnector("localhost", 23), 1);
        this.deck = new UnoDeck();
    }

    /**
     * Test to see that the amount of cards that is dealt to players equal 7.
     *
     */

    @Test
    public void StartingCardAmount()
    {
        int startingCards = rules.getStartingCardAmount();
        assertEquals(7, startingCards);
    }

    /**
     * Test to see that you can say uno with 1 card in hand.
     */

    @Test
    public void canSayUnoWith1Card()
    {
        player.addToHand(deck.getNextCard());
        boolean canSayUno = rules.canSayUno(this.player);
        assertEquals(true, canSayUno);
    }

    /**
     * Test to see that you can not say uno with 2 cards in hand.
     */

    @Test
    public void canSayUnoWith2Cards()
    {
        player.addToHand(deck.getNextCard());
        player.addToHand(deck.getNextCard());
        boolean canSayUno = rules.canSayUno(this.player);
        assertEquals(false, canSayUno);
    }

    /**
     * Test to see that you win the game by having 0 cards and having said uno
     */

    @Test
    public void checkWinnerWith0CardsAndIsUno()
    {
        player.setUno();
        boolean isWinner = rules.checkWinner(this.player);
        assertEquals(true, isWinner);
    }

    /**
     * Test to see that you do not win with 1 card in hand with uno.
     */

    @Test
    public void checkWinnerWith1CardsAndIsUno()
    {
        player.setUno();
        player.addToHand(deck.getNextCard());
        boolean isWinner = rules.checkWinner(this.player);
        assertEquals(false, isWinner);
    }

    /**
     * Test to see that you do not win with 0 cards without having said uno.
     */

    @Test
    public void checkWinnerWith0CardsAndIsNotUno()
    {
        player.addToHand(deck.getNextCard());
        boolean isWinner = rules.checkWinner(this.player);
        assertEquals(false, isWinner);
    }

    /**
     * Test if player can play the same card as has been played with numbered card.
     */

    @Test
    public void checkPlayerCanPlayWithSameNumberedCard()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playerCard));
    }

    /**
     * Test if player can play the same card as has been played with reverse card.
     */

    @Test
    public void checkPlayerCanPlayWithSameReverseCard()
    {
        Card playerCard = CardFactory.buildCard(Reverse.class.getTypeName(), "yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playerCard));
    }

    /**
     * Test if player can play the same card as has been played with skip card.
     */

    @Test
    public void checkPlayerCanPlayWithSameSkipCard()
    {
        Card playerCard = CardFactory.buildCard(Skip.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playerCard));
    }

    /**
     * Test if player can play the same card as has been played with plus2 card.
     */

    @Test
    public void checkPlayerCanPlayWithSamePlus2Card()
    {
        Card playerCard = CardFactory.buildCard(Plus2.class.getTypeName(), "yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playerCard));
    }

    /**
     * Test if player can play the equal card that as has been played with plus2 card.
     */

    @Test
    public void checkPlayerCanPlayWithAnotherPlus2Card()
    {
        Card playerCard = CardFactory.buildCard(Plus2.class.getTypeName(), "yellow");
        Card playedCard = CardFactory.buildCard(Plus2.class.getTypeName(), "yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play the equal card that as has been played with skip card.
     */

    @Test
    public void checkPlayerCanPlayWithAnotherSkipCard()
    {
        Card playerCard = CardFactory.buildCard(Skip.class.getTypeName(), "yellow");
        Card playedCard = CardFactory.buildCard(Skip.class.getTypeName(), "yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play the equal card that as has been played with reverse card.
     */

    @Test
    public void checkPlayerCanPlayWithAnotherReverseCard()
    {
        Card playerCard = CardFactory.buildCard(Reverse.class.getTypeName(), "yellow");
        Card playedCard = CardFactory.buildCard(Reverse.class.getTypeName(), "yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play the equal card that as has been played with number card.
     */

    @Test
    public void checkPlayerCanPlayWithAnotherNumberCard()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "yellow");
        Card playedCard = CardFactory.buildCard(Number.class.getTypeName(), "1","yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play the equal card that as has been played with wild card.
     */

    @Test
    public void checkPlayerCanPlayWithAnotherWildCard()
    {
        Card playerCard = CardFactory.buildCard(Wild.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName());
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play the equal card that as has been played with plus4 card.
     */

    @Test
    public void checkPlayerCanPlayWithAnotherPlus4Card()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Plus4.class.getTypeName());
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play the same card with wild card.
     */

    @Test
    public void checkPlayerCanPlayWithSameWildCard()
    {
        Card playerCard = CardFactory.buildCard(Wild.class.getTypeName());
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playerCard));
    }

    /**
     * Test if player can play the same card with plus4 card.
     */

    @Test
    public void checkPlayerCanPlayWithSamePlus4Card()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playerCard));
    }

    /**
     * Test if player can play numbered card, same value, different color.
     */

    @Test
    public void playSameValueNumbered()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "blue");
        Card playedCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play skip card, different color.
     */

    @Test
    public void playSameValueSkip()
    {
        Card playerCard = CardFactory.buildCard(Skip.class.getTypeName(),"blue");
        Card playedCard = CardFactory.buildCard(Skip.class.getTypeName(),"yellow");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play reverse card, different color.
     */

    @Test
    public void playSameValueReverse()
    {
        Card playerCard = CardFactory.buildCard(Reverse.class.getTypeName(),"gren");
        Card playedCard = CardFactory.buildCard(Reverse.class.getTypeName(),"red");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus2 card, different color.
     */

    @Test
    public void playSameValuePlus2()
    {
        Card playerCard = CardFactory.buildCard(Plus2.class.getTypeName(),"red");
        Card playedCard = CardFactory.buildCard(Plus2.class.getTypeName(),"blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus4 on random numbered card.
     */

    @Test
    public void playPlus4OnNumbered()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Number.class.getTypeName(),"7","blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus4 on random skip card.
     */

    @Test
    public void playPlus4OnSkip()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Skip.class.getTypeName(),"blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus4 on random reverse card.
     */

    @Test
    public void playPlus4OnReverse()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Reverse.class.getTypeName(),"blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus4 on random plus2 card.
     */

    @Test
    public void playPlus4OnPlus2()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Plus2.class.getTypeName(),"blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus4 on wild with selected color.
     */

    @Test
    public void playPlus4OnWildWithSelectedColor()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play plus4 on plus4 with selected color.
     */

    @Test
    public void playPlus4OnPlus4WithSelectedColor()
    {
        Card playerCard = CardFactory.buildCard(Plus4.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Plus4.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play wild on plus4 with selected color.
     */


    @Test
    public void playWildOnWildWithSelectedColor()
    {
        Card playerCard = CardFactory.buildCard(Wild.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play wild on plus4 with selected color.
     */

    @Test
    public void playWildOnPlus4WithSelectedColor()
    {
        Card playerCard = CardFactory.buildCard(Wild.class.getTypeName());
        Card playedCard = CardFactory.buildCard(Plus4.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(true, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play numbered card on different numbered card of other color.
     */

    @Test
    public void redNumberOnBlueDifferentNumber()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        Card playedCard = CardFactory.buildCard(Number.class.getTypeName(), "3", "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play numbered card on skip card of other color.
     */

    @Test
    public void redNumberOnBlueSkip()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        Card playedCard = CardFactory.buildCard(Skip.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play numbered card on reverse card of other color.
     */

    @Test
    public void redNumberOnBlueReverse()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        Card playedCard = CardFactory.buildCard(Reverse.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play numbered card on plus2 card of other color.
     */

    @Test
    public void redNumberOnBluePlus2()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        Card playedCard = CardFactory.buildCard(Plus2.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play numbered card on wild with other selected color.
     */

    @Test
    public void redNumberOnBlueSelectedWild()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play numbered card on plus4 with other selected color.
     */

    @Test
    public void redNumberOnBlueSelectedPlus4()
    {
        Card playerCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play green skip on blue selected plus4.
     */

    @Test
    public void greenSkipOnBlueSelectedPlus4()
    {
        Card playerCard = CardFactory.buildCard(Skip.class.getTypeName(), "green");
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play yellow reverse on blue selected plus4.
     */

    @Test
    public void yellowReverseOnBlueSelectedPlus4()
    {
        Card playerCard = CardFactory.buildCard(Reverse.class.getTypeName(), "yellow");
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if player can play green plus2 on blue selected plus4.
     */

    @Test
    public void greenPlus2OnBlueSelectedPlus4()
    {
        Card playerCard = CardFactory.buildCard(Plus2.class.getTypeName(), "green");
        Card playedCard = CardFactory.buildCard(Wild.class.getTypeName(), "blue");
        player.addToHand(playerCard);
        assertEquals(false, rules.playerCanPlay(player, playedCard));
    }

    /**
     * Test if wild is valid starting card.
     */

    @Test
    public void WildStartingCard()
    {
        Card startingCard = CardFactory.buildCard(Wild.class.getTypeName());
        assertEquals(false, rules.validStartingCard(startingCard));
    }

    /**
     * Test if plus4 is valid starting card.
     */

    @Test
    public void Plus4StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Plus4.class.getTypeName());
        assertEquals(false, rules.validStartingCard(startingCard));
    }

    /**
     * Test if reverse is valid starting card.
     */

    @Test
    public void ReverseStartingCard()
    {
        Card startingCard = CardFactory.buildCard(Reverse.class.getTypeName(), "red");
        assertEquals(false, rules.validStartingCard(startingCard));
    }

    /**
     * Test if skip is valid starting card.
     */

    @Test
    public void SkipStartingCard()
    {
        Card startingCard = CardFactory.buildCard(Skip.class.getTypeName(), "red");
        assertEquals(false, rules.validStartingCard(startingCard));
    }

    /**
     * Test if plus2 is valid starting card.
     */

    @Test
    public void Plus2StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Plus2.class.getTypeName(), "red");
        assertEquals(false, rules.validStartingCard(startingCard));
    }

    /**
     * Test if number 0 is valid starting card.
     */

    @Test
    public void Number0StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Number.class.getTypeName(), "0", "red");
        assertEquals(true, rules.validStartingCard(startingCard));
    }

    /**
     * Test if number 3 is valid starting card.
     */

    @Test
    public void Number3StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Number.class.getTypeName(), "3", "green");
        assertEquals(true, rules.validStartingCard(startingCard));
    }

    /**
     * Test if number 6 is valid starting card.
     */

    @Test
    public void Number6StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Number.class.getTypeName(), "6", "yellow");
        assertEquals(true, rules.validStartingCard(startingCard));
    }

    /**
     * Test if number 9 is valid starting card.
     */

    @Test
    public void Number9StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Number.class.getTypeName(), "9", "blue");
        assertEquals(true, rules.validStartingCard(startingCard));
    }

    /**
     * Test if number 15 is valid starting card.
     */

    @Test
    public void Number15StartingCard()
    {
        Card startingCard = CardFactory.buildCard(Number.class.getTypeName(), "15", "blue");
        assertEquals(false, rules.validStartingCard(startingCard));
    }

    /**
     * Test that startingcard is drawn from deck.
     */

    @Test
    public void cardFromDeck()
    {
        Card startingCard = CardFactory.buildCard(Number.class.getTypeName(), "9", "blue");
        Deck deckwith1card = new Deck();
        deckwith1card.addCard(startingCard);
        assertEquals(startingCard, rules.drawStartingCard(deckwith1card));
    }

    /**
     * Test that startingcard is the top card from deck.
     */

    @Test
    public void cardFromTopTest()
    {
        Deck specificDeck = new Deck();
        Card firstCard = CardFactory.buildCard(Number.class.getTypeName(), "0", "blue");
        specificDeck.addCard(firstCard);
        for(int numberOfCardsInDeck = 1; numberOfCardsInDeck<9; numberOfCardsInDeck++)
        {
            Card otherCard = CardFactory.buildCard(Number.class.getTypeName(), Integer.toString(numberOfCardsInDeck), "blue");
            specificDeck.addCard(otherCard);
        }
        assertEquals(firstCard, rules.drawStartingCard(specificDeck));
    }

    /**
     * Test to play 2 cards with only 2 cards in hand.
     * First card is same color as played card, other card is same value as first played card.
     */

    @Test
    public void play2numberedSameColorPlayerHasMoreThen2InHand()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Number.class.getTypeName(), "2", "green");
        Card secondCard = CardFactory.buildCard(Number.class.getTypeName(), "2", "red");

        player.addToHand(firstCard);
        player.addToHand(secondCard);
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(true, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    /**
     * Test to play 2 skip cards with more then 2 cards in hand.
     * First card is same color, other cards is same value.
     */

    @Test
    public void play2SkipSameColorPlayerHasMoreThen2InHand()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Skip.class.getTypeName(),"green");
        Card secondCard = CardFactory.buildCard(Skip.class.getTypeName(), "red");

        player.addToHand(firstCard);
        player.addToHand(secondCard);
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(true, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    /**
     * Test to play 2 numbered cards with more then 2 cards in hand.
     * Cards are same value as played card and same value as each other.
     */

    @Test
    public void play2NumberDifferentColorPlayerHasMoreThen2InHand()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Number.class.getTypeName(),"1","blue");
        Card secondCard = CardFactory.buildCard(Number.class.getTypeName(),"1", "red");

        player.addToHand(firstCard);
        player.addToHand(secondCard);
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(true, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    /**
     * Test to play 2 Wild cards with more then 2 cards in hand.
     *
     */

    @Test
    public void play2WildPlayerHasMoreThen2InHand()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Wild.class.getTypeName());
        Card secondCard = CardFactory.buildCard(Wild.class.getTypeName());

        player.addToHand(firstCard);
        player.addToHand(secondCard);
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(true, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    /**
     * Test to play 1 numbered cards of same value, and a wild card.
     *
     */

    @Test
    public void playWildAndNumberPlayerHasMoreThen2InHand()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Number.class.getTypeName(),"1","blue");
        Card secondCard = CardFactory.buildCard(Wild.class.getTypeName());

        player.addToHand(firstCard);
        player.addToHand(secondCard);
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(false, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    @Test
    public void playWrongAndWrongCardPlayerHasMoreThen2InHand()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Number.class.getTypeName(),"4","blue");
        Card secondCard = CardFactory.buildCard(Plus2.class.getTypeName(), "yellow");

        player.addToHand(firstCard);
        player.addToHand(secondCard);
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(false, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    /**
     *
     * Test if player can end by playing more then 1 card.
     */

    @Test
    public void PlayerEndByPlayingMoreThen1Card()
    {
        Card activeCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "green");
        Card firstCard = CardFactory.buildCard(Number.class.getTypeName(),"1","blue");
        Card secondCard = CardFactory.buildCard(Number.class.getTypeName(), "1", "red");

        player.addToHand(firstCard);
        player.addToHand(secondCard);

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(false, rules.playerCanPlayCards(cardsToPlay, activeCard, player));
    }

    /**
     *
     * Test with rules if multiple plus2 cards should stack.
     */

    @Test
    public void testCardsStack()
    {
        Card firstCard = CardFactory.buildCard(Plus2.class.getTypeName(),"blue");
        Card secondCard = CardFactory.buildCard(Plus2.class.getTypeName(),  "red");

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(firstCard);
        cardsToPlay.add(secondCard);

        assertEquals(cardsToPlay, rules.cardsToProcess(cardsToPlay));
    }

    /**
     * Test if player should be penalized with 1 card and having not said uno.
     */

    @Test
    public void penalizePlayer1CardNotUno()
    {
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));
        assertEquals(true, rules.penalizePlayer(player));
    }

    /**
     * Test if player should be penalized with 1 card and having said uno.
     */

    @Test
    public void penalizePlayer1CardUno()
    {
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));
        player.setUno();
        assertEquals(false, rules.penalizePlayer(player));
    }

    /**
     * Test if player should be penalized with 2 cardc and having not said uno.
     */

    @Test
    public void penalizePlayer2CardNotUno()
    {
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));
        player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));
        assertEquals(false, rules.penalizePlayer(player));
    }

    /**
     * Test if the gamestate correctly alters the player when having said uno.
     */


    @Test
    public void SayUno()
    {
        this.player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));
        ArrayList<remotePlayer> players = new ArrayList<>();
        players.add(this.player);
        GameState gameState = new GameState(new UnoDeck(), players);
        gameState.setActivePlayer(player);
        Server server = new Server();
        UnoGame unoGame = new UnoGame(server);
        unoGame.setGameState(gameState);
        unoGame.setRules(new Rules());
        unoGame.checkUno("TYPE;VALUE;COLOR;UNO");
        assertEquals(true, player.isUno());
    }

    /**
     * Test if the gamestate incorrectly alters player when having said something else then uno.
     */

    @Test
    public void SaySomethingElse()
    {
        this.player.addToHand(CardFactory.buildCard(Wild.class.getTypeName()));
        ArrayList<remotePlayer> players = new ArrayList<>();
        players.add(this.player);
        GameState gameState = new GameState(new UnoDeck(), players);
        gameState.setActivePlayer(player);
        Server server = new Server();
        UnoGame unoGame = new UnoGame(server);
        unoGame.setGameState(gameState);
        unoGame.setRules(new Rules());
        unoGame.checkUno("TYPE;VALUE;COLOR;Fisk");
        assertEquals(false, player.isUno());
    }

    /**
     * Test if the deck correctly takes cards from the pile and adds to deck after depleating.
     */

    @Test
    public void cardsFromPile()
    {
        Deck emptyDeck = new Deck();
        ArrayList<Card> cardPile = new ArrayList<>();
        Card pileCard = CardFactory.buildCard(Wild.class.getTypeName());
        cardPile.add(pileCard);
        emptyDeck.cardsToPile(cardPile);
        assertEquals(pileCard, emptyDeck.getNextCard());
    }

    /**
     * Test if the game performs multiple skips if a player plays multiple skipcards.
     */

    @Test
    public void MultipleSkips()
    {
        ArrayList<remotePlayer> players = new ArrayList<>();
        remotePlayer player1 = new remotePlayer(new ClientConnector("localhost", 1234), 0);
        remotePlayer player2 = new remotePlayer(new ClientConnector("localhost", 1234), 1);
        remotePlayer player3 = new remotePlayer(new ClientConnector("localhost", 1234), 2);
        remotePlayer player4 = new remotePlayer(new ClientConnector("localhost", 1234), 3);
        remotePlayer player5 = new remotePlayer(new ClientConnector("localhost", 1234), 4);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        GameState gameState = new GameState(new UnoDeck(), players);
        gameState.setActivePlayer(player1);
        Server server = new Server();
        UnoGame unoGame = new UnoGame(server);
        unoGame.setGameState(gameState);
        unoGame.setRules(new Rules());

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(CardFactory.buildCard(Skip.class.getTypeName(), "green"));
        cardsToPlay.add(CardFactory.buildCard(Skip.class.getTypeName(), "red"));
        cardsToPlay.add(CardFactory.buildCard(Skip.class.getTypeName(), "blue"));
        unoGame.processCards(cardsToPlay);
        gameState.toNextTurn();

        assertEquals(player5, gameState.getActivePlayer());
    }

    /**
     * Test if the game performs multiple draw 4 cards when a player plays multiple plus4 cards.
     * This checks internal state of player.
     */

    @Test
    public void MultiplePlus4()
    {
        ArrayList<remotePlayer> players = new ArrayList<>();
        remotePlayer player1 = new remotePlayer(new ClientConnector("localhost", 1234), 0);
        remotePlayer player2 = new remotePlayer(new ClientConnector("localhost", 1234), 1);
        remotePlayer player3 = new remotePlayer(new ClientConnector("localhost", 1234), 2);
        remotePlayer player4 = new remotePlayer(new ClientConnector("localhost", 1234), 3);
        remotePlayer player5 = new remotePlayer(new ClientConnector("localhost", 1234), 4);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Deck testDeck = new Deck();
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "red"));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "green"));
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "red"));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "green"));


        GameState gameState = new GameState(testDeck, players);
        gameState.setActivePlayer(player1);
        Server server = new Server();
        UnoGame unoGame = new UnoGame(server);
        unoGame.setGameState(gameState);
        unoGame.setRules(new Rules());

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(CardFactory.buildCard(Plus4.class.getTypeName(), "green"));
        cardsToPlay.add(CardFactory.buildCard(Plus4.class.getTypeName(), "red"));
        unoGame.processCards(cardsToPlay);
        gameState.toNextTurn();

        assertEquals(8, gameState.getActivePlayer().getHandSize());
    }

    /**
     * Test if the game performs multiple draw 4 cards when a player plays multiple plus4 cards.
     * This checks outgoing transmit to player updating of its state.
     */
    @Test
    public void MultiplePlus4Transmit()
    {
        ArrayList<remotePlayer> players = new ArrayList<>();
        remotePlayer player1 = new remotePlayer(new ClientConnector("localhost", 1234), 0);
        remotePlayer player2 = new remotePlayer(new ClientConnector("localhost", 1234), 1);
        remotePlayer player3 = new remotePlayer(new ClientConnector("localhost", 1234), 2);
        remotePlayer player4 = new remotePlayer(new ClientConnector("localhost", 1234), 3);
        remotePlayer player5 = new remotePlayer(new ClientConnector("localhost", 1234), 4);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Deck testDeck = new Deck();
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "red"));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "green"));
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Wild.class.getTypeName()));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "red"));
        testDeck.addCard(CardFactory.buildCard(Plus2.class.getTypeName(), "green"));


        GameState gameState = new GameState(testDeck, players);
        gameState.setActivePlayer(player1);
        Server server = new Server();
        UnoGame unoGame = new UnoGame(server);
        unoGame.setGameState(gameState);
        unoGame.setRules(new Rules());

        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(CardFactory.buildCard(Plus4.class.getTypeName(), "green"));
        cardsToPlay.add(CardFactory.buildCard(Plus4.class.getTypeName(), "red"));
        unoGame.processCards(cardsToPlay);
        gameState.toNextTurn();

        assertEquals("DRAW=CARD:Game.Items.Cards.Wild;[(?)];grey\n" +
                "DRAW=CARD:Game.Items.Cards.Wild;[(?)];grey\n" +
                "DRAW=CARD:Game.Items.Cards.Plus2;[+2];red\n" +
                "DRAW=CARD:Game.Items.Cards.Plus2;[+2];green\n" +
                "UPDATE=CARD:Game.Items.Cards.Plus4;[+4];green\n" +
                "DRAW=CARD:Game.Items.Cards.Wild;[(?)];grey\n" +
                "DRAW=CARD:Game.Items.Cards.Wild;[(?)];grey\n" +
                "DRAW=CARD:Game.Items.Cards.Plus2;[+2];red\n" +
                "DRAW=CARD:Game.Items.Cards.Plus2;[+2];green\n" +
                "UPDATE=CARD:Game.Items.Cards.Plus4;[+4];red\n", gameState.getActivePlayer().getToTransmit());
    }




}
