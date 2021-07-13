package com.drailan.deckofcards.services;

import com.drailan.deckofcards.entities.Deck;
import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.services.contracts.IDeckService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeckService implements IDeckService {
    private final List<Deck> decks = new ArrayList<>();

    @Override
    public UUID createDeck() {
        var deck = new Deck();
        decks.add(deck);

        return deck.getId();
    }

    @SneakyThrows
    @Override
    public Deck getDeck(UUID deckId) {
        return decks.stream()
                .filter(d -> d.getId().equals(deckId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));
    }
}
