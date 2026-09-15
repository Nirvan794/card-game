package com.sathat.model;

import com.sathat.enums.Rank;
import com.sathat.enums.Suit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Deck {

    private final Deque<Card> cards;

    public Deck() {

        cards = new ArrayDeque<>();

        List<Card> newCards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                newCards.add(new Card(suit, rank));
            }
        }

        Collections.shuffle(newCards);

        cards.addAll(newCards);
    }

    public Card draw() {

        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty.");
        }

        return cards.removeFirst();
    }

    public int size() {
        return cards.size();
    }
}