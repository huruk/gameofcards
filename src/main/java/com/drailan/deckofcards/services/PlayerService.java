package com.drailan.deckofcards.services;

import com.drailan.deckofcards.entities.Card;
import com.drailan.deckofcards.entities.Player;
import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.services.contracts.IGameService;
import com.drailan.deckofcards.services.contracts.IPlayerService;
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
public class PlayerService implements IPlayerService {
    private final IGameService gameService;
    private final List<Player> players = new ArrayList<>();

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @SneakyThrows
    @Override
    public Player getPlayer(UUID playerId) {
        return players.stream().filter(g -> g.getId() == playerId).findFirst().orElseThrow(() -> new EntityNotFoundException("Player not found"));
    }

    @Override
    public UUID addPlayer(UUID gameId) {
        var game = gameService.getGame(gameId);
        var player = new Player();
        game.getPlayers().add(player);

        return player.getId();
    }

    @SneakyThrows
    @Override
    public boolean removePlayer(UUID gameId, UUID playerId) {
        var player = players.stream()
                .filter(g -> g.getId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        return players.remove(player);
    }

    @Override
    public Card dealCards(UUID gameId, UUID playerId, int numberOfCards) {
        var deck = gameService.getGame(gameId).getDeck();
        if(deck.isEmpty()) {
            return null;
        }

        var targetCard = gameService.getGame(gameId).getDeck().remove(0);

        var targetPlayer = getPlayer(playerId);
        targetPlayer.getCards().add(targetCard);

        return targetCard;
    }
}
