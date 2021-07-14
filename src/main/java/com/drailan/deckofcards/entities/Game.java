package com.drailan.deckofcards.entities;

import lombok.Data;

import java.util.LinkedList;
import java.util.UUID;

@Data
public class Game {
    private final UUID id = UUID.randomUUID();
    private final LinkedList<Player> players = new LinkedList<>();
    private LinkedList<Card> deck = new LinkedList<>();
}
