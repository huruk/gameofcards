package com.drailan.deckofcards.controllers;

import com.drailan.deckofcards.entities.Game;
import com.drailan.deckofcards.services.contracts.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/game")
public class GameController {
    private final IGameService gameService;

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
        var deck = gameService.getGame(gameId);
        return ResponseEntity.ok(deck);
    }

    @DeleteMapping("{gameId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteGame(@PathVariable UUID gameId) {
        gameService.deleteGame(gameId);

        return ResponseEntity.noContent().build();
    }
}
