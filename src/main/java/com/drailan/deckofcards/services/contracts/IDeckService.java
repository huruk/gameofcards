package com.drailan.deckofcards.services.contracts;

import com.drailan.deckofcards.entities.Deck;

import java.util.UUID;

public interface IDeckService {
    UUID createDeck();

    Deck getDeck(UUID deckId);
}
