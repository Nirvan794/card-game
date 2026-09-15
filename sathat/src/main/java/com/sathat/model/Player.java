package com.sathat.model;

import com.sathat.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int id;
    private final String name;
    private final Team team;
    private final boolean human;

    private final List<Card> cards;

    public Player(
            int id,
            String name,
            Team team,
            boolean human
    ) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.human = human;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public boolean hasSuit(Suit suit) {

        for (Card card : cards) {

            if (card.getSuit() == suit) {
                return true;
            }
        }

        return false;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getCardCount() {
        return cards.size();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isHuman() {
        return human;
    }

    @Override
    public String toString() {
        return name;
    }
}