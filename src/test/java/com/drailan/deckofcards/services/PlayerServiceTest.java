package com.drailan.deckofcards.services;

import com.drailan.deckofcards.entities.Deck;
import com.drailan.deckofcards.entities.Game;
import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.services.contracts.IGameService;
import com.drailan.deckofcards.services.contracts.IPlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerServiceTest {
    private IGameService gameService;
    private IPlayerService playerService;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        playerService = new PlayerService(gameService);

        var game = new Game();
        var deck = new Deck();
        game.getDeck().addAll(deck.getCards());

        when(gameService.getGame(any())).thenReturn(game);
    }

    @Test
    void when_addPlayer_AndGameExists_ThenSuccess() {
        var gameId = UUID.randomUUID();
        var newPlayer = playerService.addPlayer(gameId);

        Assertions.assertEquals(newPlayer, gameService.getGame(gameId).getPlayers().get(0).getId());
    }

    @Test
    void when_removePlayer_AndExists_ThenSuccess() {
        var gameId = UUID.randomUUID();
        var firstPlayer = playerService.addPlayer(gameId);
        var secondPlayer = playerService.addPlayer(gameId);

        playerService.removePlayer(gameId, firstPlayer);

        Assertions.assertEquals(secondPlayer, gameService.getGame(gameId).getPlayers().get(0).getId());
    }

    @Test
    void when_removePlayer_AndDoesNotExist_ThenException() {
        var gameId = UUID.randomUUID();
        playerService.addPlayer(gameId);
        playerService.addPlayer(gameId);

        Assertions.assertThrows(EntityNotFoundException.class, () -> playerService.removePlayer(gameId, UUID.randomUUID()));
    }

    @Test
    void dealCards() {
        var gameId = UUID.randomUUID();

        var game = gameService.getGame(gameId);
        var firstPlayerId=  playerService.addPlayer(gameId);
        var secondPlayerId=  playerService.addPlayer(gameId);

        playerService.dealCards(gameId, firstPlayerId, 2);
        Assertions.assertEquals(50, game.getDeck().size());

        playerService.dealCards(gameId, secondPlayerId, 3);
        Assertions.assertEquals(47, game.getDeck().size());

        playerService.dealCards(gameId, firstPlayerId, 100);
        Assertions.assertEquals(0, game.getDeck().size());
        Assertions.assertEquals(49, playerService.getPlayer(gameId, firstPlayerId).getCards().size());
    }
}