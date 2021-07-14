package com.drailan.deckofcards.services;

import com.drailan.deckofcards.entities.Game;
import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.services.contracts.IDeckService;
import com.drailan.deckofcards.services.contracts.IGameService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class GameService implements IGameService {
    private final IDeckService deckService;
    private final List<Game> games = new ArrayList<>();

    @Override
    public UUID createGame() {
        var game = new Game();
        games.add(game);

        log.debug("Created game with {}", game.getId());
        return game.getId();
    }

    @Override
    public List<Game> getGames() {
        return games;
    }

    @Override
    public boolean deleteGame(UUID gameId) {
        var game = getGame(gameId);

        log.debug("Deleting game {}", gameId);
        return games.remove(game);
    }

    @SneakyThrows
    @Override
    public Game getGame(UUID gameId) {
        return games.stream()
                .filter(g -> g.getId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
    }

    @Override
    public void addDeck(UUID gameId, UUID deckId) {
        var deck = deckService.getDeck(deckId);
        var game = getGame(gameId);

        var gameDeck = game.getDeck();
        if (gameDeck.stream().anyMatch(card -> card.getDeckId().equals(deckId))) {
            throw new IllegalStateException(String.format("Cannot add cards of a deck %s that has already been added to the game", deckId));
        }

        log.debug("Adding deck {} contents to game {}", deckId, gameId);
        game.getDeck().addAll(deck.getCards());
    }
}
