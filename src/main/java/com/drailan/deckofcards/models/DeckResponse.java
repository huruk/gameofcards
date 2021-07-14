package com.drailan.deckofcards.models;

import com.drailan.deckofcards.entities.Card;
import com.drailan.deckofcards.entities.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class DeckResponse {
    private List<Card> cards;

    public Map<Suit, Long> getUndealtCardsPerSuite() {
        return cards
                .stream()
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    public Map<Suit, NavigableMap<Card, Long>> getUndealtCardsPerSuiteAndFaceValue() {
        var initialMap = cards
                .stream()
                .collect(Collectors.groupingBy(
                        Card::getSuit,
                        Collectors.groupingBy(c -> c, () -> new TreeMap<Card, Long>().descendingMap(), Collectors.counting())
                ));

        return initialMap;
    }
}
