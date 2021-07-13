package com.drailan.deckofcards.services.contracts;

import com.drailan.deckofcards.entities.Game;

import java.util.List;
import java.util.UUID;

public interface IGameService {
    UUID createGame();

    List<Game> getGames();

    boolean deleteGame(UUID gameId);

    Game getGame(UUID gameId);

    void addDeck(UUID gameId, UUID deckId);
}
