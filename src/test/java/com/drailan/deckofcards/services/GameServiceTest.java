package com.drailan.deckofcards.services;

import com.drailan.deckofcards.entities.Card;
import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.services.contracts.IDeckService;
import com.drailan.deckofcards.services.contracts.IGameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.UUID;

class GameServiceTest {
    private IGameService gameService;
    private IDeckService deckService;

    @BeforeEach
    void setUp() {
        deckService = new DeckService();
        gameService = new GameService(deckService);
    }

    @Test
    void when_createGame_ThenSuccess() {
        var newGameId = gameService.createGame();
        Assertions.assertNotNull(newGameId);
    }

    @Test
    void when_getGames_ThenSuccess() {
        gameService.createGame();
        gameService.createGame();

        var games = gameService.getGames();
        Assertions.assertEquals(2, games.size());
    }

    @Test
    void deleteGame() {
    }

    @Test
    void when_getGame_andExists_ThenSuccess() {
        var firstGameId = gameService.createGame();
        var otherGameId = gameService.createGame();

        var success = gameService.deleteGame(firstGameId);
        var games = gameService.getGames();

        Assertions.assertTrue(success);
        Assertions.assertEquals(1, games.size());
        Assertions.assertEquals(otherGameId, games.get(0).getId());
    }

    @Test
    void when_getGame_andDoesNotExist_ThenNotFound() {
        var firstGameId = gameService.createGame();
        var otherGameId = gameService.createGame();

        Assertions.assertThrows(EntityNotFoundException.class, () -> gameService.deleteGame(UUID.randomUUID()));
    }

    @Test
    void when_addDeck_andExists_ThenSuccess() {
        var newDeck = deckService.createDeck();
        var newGame = gameService.createGame();

        gameService.addDeck(newGame, newDeck);

        var game = gameService.getGame(newGame);
        Assertions.assertEquals(52, game.getDeck().size());
        Assertions.assertEquals(newDeck, game.getDeck().get(0).getDeckId());
    }

    @Test
    void when_addDeck_andDoesNotExist_ThenNotFound() {
        var newDeck = deckService.createDeck();
        var newGame = gameService.createGame();

        Assertions.assertThrows(EntityNotFoundException.class, () -> gameService.addDeck(newGame, UUID.randomUUID()));
    }

    @Test
    void when_addDeck_andDuplicate_ThenException() {
        var newDeck = deckService.createDeck();
        var newGame = gameService.createGame();

        gameService.addDeck(newGame, newDeck);
        Assertions.assertThrows(IllegalStateException.class, () -> gameService.addDeck(newGame, newDeck));
    }

    @Test
    void when_shuffleDeck_ThenSuccess() {
        var newDeck = deckService.createDeck();
        var newGame = gameService.createGame();

        gameService.addDeck(newGame, newDeck);

        var currentDeck = Arrays.copyOf(gameService.getGame(newGame).getDeck().toArray(new Card[0]), 52);

        gameService.shuffleDeck(newGame);
        var resultingDeck = gameService.getGame(newGame).getDeck().toArray(new Card[0]);

        // not sure how to assert not equals properly
        Assertions.assertThrows(AssertionFailedError.class, () -> Assertions.assertArrayEquals(currentDeck, resultingDeck));
    }
}