package com.drailan.deckofcards.controllers;

import com.drailan.deckofcards.entities.Game;
import com.drailan.deckofcards.models.AddDeckRequest;
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
import java.util.UUID;

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

    @PostMapping("{gameId}/deck")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Game> addDeckToGame(@PathVariable UUID gameId, @RequestBody @Valid AddDeckRequest req) {
        gameService.addDeck(gameId, req.getDeckId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("{gameId}/deck/shuffle")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity shuffleDeck(@PathVariable UUID gameId) {
        gameService.shuffleDeck(gameId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{gameId}/deck")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DeckResponse> getDeck(@PathVariable UUID gameId) {
        var cards = gameService.getGame(gameId).getDeck();

        return ResponseEntity.ok(new DeckResponse(cards));
    }

    @DeleteMapping("{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteGame(@PathVariable UUID gameId) {
        gameService.deleteGame(gameId);

        return ResponseEntity.noContent().build();
    }
}
