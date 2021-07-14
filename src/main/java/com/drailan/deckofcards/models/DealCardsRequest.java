package com.drailan.deckofcards.models;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class DealCardsRequest {
    @Min(1)
    private int numberOfCards;
}
