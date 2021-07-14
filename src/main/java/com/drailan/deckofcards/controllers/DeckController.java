package com.drailan.deckofcards.controllers;

import com.drailan.deckofcards.entities.Deck;
import com.drailan.deckofcards.services.contracts.IDeckService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/deck")
public class DeckController {
    private final IDeckService deckService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createDeck() {
        var newDeckId = deckService.createDeck();

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{deckId}")
                .buildAndExpand(newDeckId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{deckId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Deck> getDeck(@PathVariable UUID deckId) {
        var deck = deckService.getDeck(deckId);
        return ResponseEntity.ok(deck);
    }
}
