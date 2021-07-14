package com.drailan.deckofcards.models;

import com.drailan.deckofcards.entities.Card;
import com.drailan.deckofcards.entities.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class DeckResponse {
    private List<Card> cards;

    public Map<Suit, Long> getUndealtCardsPerSuite() {
        return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    public Map<Suit, Map<String, Integer>> getUndealtCardsPerSuiteAndFaceValue() {
        var initialMap = cards.stream().collect(Collectors.groupingBy(Card::getSuit));
    }
}
