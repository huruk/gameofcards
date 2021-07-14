package com.drailan.deckofcards.services.contracts;

import com.drailan.deckofcards.entities.Player;

import java.util.List;
import java.util.UUID;

public interface IPlayerService {
    List<Player> getPlayers(UUID gameId);

    Player getPlayer(UUID gameId, UUID playerId);

    UUID addPlayer(UUID gameId);

    boolean removePlayer(UUID gameId, UUID playerId);

    void dealCards(UUID gameId, UUID playerId, int numberOfCards);
}
