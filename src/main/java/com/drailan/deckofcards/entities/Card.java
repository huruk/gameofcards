package com.drailan.deckofcards.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Card implements Comparable<Card> {
    private final String name;
    private final int value;
    private final Suit suit;

    private final UUID deckId;

    @Override
    public int compareTo(Card o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return name;
    }
}
