package com.drailan.deckofcards.controllers;

import com.drailan.deckofcards.entities.Deck;
import com.drailan.deckofcards.entities.Game;
import com.drailan.deckofcards.entities.Player;
import com.drailan.deckofcards.models.AddDeckRequest;
import com.drailan.deckofcards.models.DealCardsRequest;
import com.drailan.deckofcards.models.DeckResponse;
import com.drailan.deckofcards.services.contracts.IGameService;
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
@RequestMapping("api/v1/game")
public class GameController {
    private final IGameService gameService;
    private final IPlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createGame() {
        var newGameId = gameService.createGame();

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{gameId}")
                .buildAndExpand(newGameId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> getGame(@PathVariable UUID gameId) {
        var game = gameService.getGame(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("{gameId}/players")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Player>> getPlayers(@PathVariable UUID gameId) {
        var players =  playerService.getPlayers(gameId);
        if(players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var sortedPlayers = players
                .stream()
                .sorted(Comparator.comparingInt(Player::getTotalCardValue))
                .collect(Collectors.toList());

        return ResponseEntity.ok(sortedPlayers);
    }

    @GetMapping("{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> getPlayer(@PathVariable UUID gameId, @PathVariable UUID playerId) {
        var player = playerService.getPlayer(gameId, playerId);
        return ResponseEntity.ok(player);
    }

    @PostMapping("{gameId}/players")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> addPlayer(@PathVariable UUID gameId) {
        playerService.addPlayer(gameId);

        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @DeleteMapping("{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> deletePlayer(@PathVariable UUID gameId, @PathVariable UUID playerId) {
        playerService.removePlayer(gameId, playerId);

        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @PostMapping("{gameId}/deck")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> addDeckToGame(@PathVariable UUID gameId, @RequestBody @Valid AddDeckRequest req) {
        gameService.addDeck(gameId, req.getDeckId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("{gameId}/deck")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DeckResponse> getDeck(@PathVariable UUID gameId) {
        var cards = gameService.getGame(gameId).getDeck();

        return ResponseEntity.ok(new DeckResponse(cards));
    }

    @DeleteMapping("{gameId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteGame(@PathVariable UUID gameId) {
        gameService.deleteGame(gameId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{gameId}/players/{playerId}/deal")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> dealCardsToPlayer(@PathVariable UUID gameId, @PathVariable UUID playerId, @RequestBody @Valid DealCardsRequest req) {
        playerService.dealCards(gameId, playerId, req.getNumberOfCards());

        return ResponseEntity.ok(gameService.getGame(gameId));
    }
}
