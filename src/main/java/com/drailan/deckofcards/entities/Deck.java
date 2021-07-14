package com.drailan.deckofcards.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Deck {
    private final UUID id = UUID.randomUUID();
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();

        cards.add(new Card("Ace", 1, Suit.CLUBS, id));
        cards.add(new Card("2", 2, Suit.CLUBS, id));
        cards.add(new Card("3", 3, Suit.CLUBS, id));
        cards.add(new Card("4", 4, Suit.CLUBS, id));
        cards.add(new Card("5", 5, Suit.CLUBS, id));
        cards.add(new Card("6", 6, Suit.CLUBS, id));
        cards.add(new Card("7", 7, Suit.CLUBS, id));
        cards.add(new Card("8", 8, Suit.CLUBS, id));
        cards.add(new Card("9", 9, Suit.CLUBS, id));
        cards.add(new Card("10", 10, Suit.CLUBS, id));
        cards.add(new Card("Jack", 11, Suit.CLUBS, id));
        cards.add(new Card("Queen", 12, Suit.CLUBS, id));
        cards.add(new Card("King", 13, Suit.CLUBS, id));

        cards.add(new Card("Ace", 1, Suit.DIAMONDS, id));
        cards.add(new Card("2", 2, Suit.DIAMONDS, id));
        cards.add(new Card("3", 3, Suit.DIAMONDS, id));
        cards.add(new Card("4", 4, Suit.DIAMONDS, id));
        cards.add(new Card("5", 5, Suit.DIAMONDS, id));
        cards.add(new Card("6", 6, Suit.DIAMONDS, id));
        cards.add(new Card("7", 7, Suit.DIAMONDS, id));
        cards.add(new Card("8", 8, Suit.DIAMONDS, id));
        cards.add(new Card("9", 9, Suit.DIAMONDS, id));
        cards.add(new Card("10", 10, Suit.DIAMONDS, id));
        cards.add(new Card("Jack", 11, Suit.DIAMONDS, id));
        cards.add(new Card("Queen", 12, Suit.DIAMONDS, id));
        cards.add(new Card("King", 13, Suit.DIAMONDS, id));

        cards.add(new Card("Ace", 1, Suit.HEARTS, id));
        cards.add(new Card("2", 2, Suit.HEARTS, id));
        cards.add(new Card("3", 3, Suit.HEARTS, id));
        cards.add(new Card("4", 4, Suit.HEARTS, id));
        cards.add(new Card("5", 5, Suit.HEARTS, id));
        cards.add(new Card("6", 6, Suit.HEARTS, id));
        cards.add(new Card("7", 7, Suit.HEARTS, id));
        cards.add(new Card("8", 8, Suit.HEARTS, id));
        cards.add(new Card("9", 9, Suit.HEARTS, id));
        cards.add(new Card("10", 10, Suit.HEARTS, id));
        cards.add(new Card("Jack", 11, Suit.HEARTS, id));
        cards.add(new Card("Queen", 12, Suit.HEARTS, id));
        cards.add(new Card("King", 13, Suit.HEARTS, id));

        cards.add(new Card("Ace", 1, Suit.SPADES, id));
        cards.add(new Card("2", 2, Suit.SPADES, id));
        cards.add(new Card("3", 3, Suit.SPADES, id));
        cards.add(new Card("4", 4, Suit.SPADES, id));
        cards.add(new Card("5", 5, Suit.SPADES, id));
        cards.add(new Card("6", 6, Suit.SPADES, id));
        cards.add(new Card("7", 7, Suit.SPADES, id));
        cards.add(new Card("8", 8, Suit.SPADES, id));
        cards.add(new Card("9", 9, Suit.SPADES, id));
        cards.add(new Card("10", 10, Suit.SPADES, id));
        cards.add(new Card("Jack", 11, Suit.SPADES, id));
        cards.add(new Card("Queen", 12, Suit.SPADES, id));
        cards.add(new Card("King", 13, Suit.SPADES, id));
    }
}
