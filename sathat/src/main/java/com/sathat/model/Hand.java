package com.sathat.model;

import com.sathat.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final Player leader;
    private final Suit troop;

    private final List<PlayedCard> playedCards;

    public Hand(
            Player leader,
            Suit troop
    ) {
        this.leader = leader;
        this.troop = troop;
        this.playedCards = new ArrayList<>();
    }

    public void addPlayedCard(
            Player player,
            Card card
    ) {
        playedCards.add(
                new PlayedCard(
                        player,
                        card
                )
        );
    }

    public Player getLeader() {
        return leader;
    }

    public Suit getTroop() {
        return troop;
    }

    public List<PlayedCard> getPlayedCards() {
        return playedCards;
    }

    public Card getLeadingCard() {

        if (playedCards.isEmpty()) {
            return null;
        }

        return playedCards.get(0).getCard();
    }

    public Suit getLeadingSuit() {

        if (playedCards.isEmpty()) {
            return null;
        }

        return playedCards.get(0).getCard().getSuit();
    }

    public boolean isComplete() {
        return playedCards.size() == 4;
    }
}