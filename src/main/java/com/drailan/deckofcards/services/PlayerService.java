package com.drailan.deckofcards.services;

import com.drailan.deckofcards.entities.Player;
import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.services.contracts.IGameService;
import com.drailan.deckofcards.services.contracts.IPlayerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class PlayerService implements IPlayerService {
    private final IGameService gameService;

    @Override
    public List<Player> getPlayers(UUID gameId) {
        return gameService.getGame(gameId).getPlayers();
    }

    @SneakyThrows
    @Override
    public Player getPlayer(UUID gameId, UUID playerId) {
        return gameService.getGame(gameId)
                .getPlayers()
                .stream()
                .filter(g -> g.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }

    @Override
    public UUID addPlayer(UUID gameId) {
        var game = gameService.getGame(gameId);
        var player = new Player();
        game.getPlayers().add(player);

        log.debug("Added player {} to game {}", player.getId(), gameId);
        return player.getId();
    }

    @SneakyThrows
    @Override
    public boolean removePlayer(UUID gameId, UUID playerId) {
        var player = getPlayer(gameId, playerId);

        log.debug("Removing player {} from game {}", playerId, gameId);
        return gameService.getGame(gameId).getPlayers().remove(player);
    }

    @Override
    public void dealCards(UUID gameId, UUID playerId, int numberOfCards) {
        var deck = gameService.getGame(gameId).getDeck();
        if (deck.isEmpty()) {
            log.warn("Deck of game {} is empty", gameId);
            return;
        }

        var cardsToRemove = Math.min(numberOfCards, deck.size());
        var targetCards = deck.subList(0, cardsToRemove);

        var targetPlayer = getPlayer(gameId, playerId);
        targetPlayer.getCards().addAll(targetCards);
        targetCards.clear();

        log.debug("Retrieved {} cards and added them to player {}", cardsToRemove, targetPlayer.getId());
    }
}
