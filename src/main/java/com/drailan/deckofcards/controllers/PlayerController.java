package com.drailan.deckofcards.controllers;

import com.drailan.deckofcards.entities.Card;
import com.drailan.deckofcards.entities.Game;
import com.drailan.deckofcards.entities.Player;
import com.drailan.deckofcards.models.DealCardsRequest;
import com.drailan.deckofcards.models.PlayerInfoResponse;
import com.drailan.deckofcards.services.contracts.IPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/game/{gameId}/players")
public class PlayerController {
    private final IPlayerService playerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PlayerInfoResponse>> getPlayers(@PathVariable UUID gameId) {
        var players =  playerService.getPlayers(gameId);
        if(players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var sortedPlayers = players
                .stream()
                .sorted(Comparator.comparingInt(Player::getTotalCardValue))
                .map(player -> new PlayerInfoResponse(player.getId(), player.getTotalCardValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(sortedPlayers);
    }

    @GetMapping("{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> getPlayer(@PathVariable UUID gameId, @PathVariable UUID playerId) {
        var player = playerService.getPlayer(gameId, playerId);
        return ResponseEntity.ok(player);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> addPlayer(@PathVariable UUID gameId) {
        var newPlayer = playerService.addPlayer(gameId);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{playerId}")
                .buildAndExpand(newPlayer)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> deletePlayer(@PathVariable UUID gameId, @PathVariable UUID playerId) {
        playerService.removePlayer(gameId, playerId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{playerId}/deal")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity dealCardsToPlayer(@PathVariable UUID gameId, @PathVariable UUID playerId, @RequestBody @Valid DealCardsRequest req) {
        playerService.dealCards(gameId, playerId, req.getNumberOfCards());

        return ResponseEntity.ok().build();
    }
}
