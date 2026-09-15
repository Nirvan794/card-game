package com.sathat.model;

import com.sathat.enums.Rank;
import com.sathat.enums.Suit;

import java.util.Objects;

public class Card {

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isTwoOfHearts() {
        return suit == Suit.HEARTS && rank == Rank.TWO;
    }

    public boolean isSuit(Suit suit) {
        return this.suit == suit;
    }

    @Override
    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Card other)) {
            return false;
        }

        return suit == other.suit && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}