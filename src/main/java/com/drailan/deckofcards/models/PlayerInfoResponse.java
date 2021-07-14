package com.drailan.deckofcards.models;

import lombok.Data;

import java.util.UUID;

@Data
public class PlayerInfoResponse {
    private final UUID playerId;
    private final int totalCardValue;
}
