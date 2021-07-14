package com.drailan.deckofcards.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddDeckRequest {
    @NotNull
    private UUID deckId;
}
