package com.drailan.deckofcards.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Player {
    private final UUID id = UUID.randomUUID();
    private final List<Card> cards = new ArrayList<>();
}
