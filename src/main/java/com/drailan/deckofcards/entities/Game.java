package com.drailan.deckofcards.entities;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Game {
    private final UUID id = UUID.randomUUID();
    private final List<Player> players = new LinkedList<>();
    private final List<Card> deck = new LinkedList<>();
}
