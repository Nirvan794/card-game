package com.sathat.service;

import com.sathat.model.Deck;
import com.sathat.model.Player;

import java.util.List;

public class DealService {

    /**
     * Deals five cards to every player.
     */
    public void dealInitialFive(
            List<Player> players,
            Deck deck
    ) {

        dealCards(
                players,
                deck,
                5
        );
    }

    /**
     * Deals the remaining two rounds
     * of four cards each.
     */
    public void dealRemainingCards(
            List<Player> players,
            Deck deck
    ) {

        dealCards(
                players,
                deck,
                4
        );

        dealCards(
                players,
                deck,
                4
        );
    }

    /**
     * Deals cards around the table one at a time.
     *
     * For example:
     *
     * P1
     * P2
     * P3
     * P4
     * P1
     * P2
     * ...
     */
    private void dealCards(
            List<Player> players,
            Deck deck,
            int numberOfCards
    ) {

        for (int cardNumber = 0;
             cardNumber < numberOfCards;
             cardNumber++) {

            for (Player player : players) {

                player.addCard(
                        deck.draw()
                );
            }
        }
    }
}