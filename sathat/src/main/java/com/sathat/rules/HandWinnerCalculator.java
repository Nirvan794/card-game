package com.sathat.rules;

import com.sathat.enums.Suit;
import com.sathat.model.Card;
import com.sathat.model.Hand;
import com.sathat.model.PlayedCard;
import com.sathat.model.Player;

public class HandWinnerCalculator {

    public Player determineWinner(
            Hand hand,
            Suit troop
    ) {

        return determineWinningPlayedCard(
                hand,
                troop
        ).getPlayer();
    }

    public PlayedCard determineWinningPlayedCard(
            Hand hand,
            Suit troop
    ) {

        if (!hand.isComplete()) {

            throw new IllegalStateException(
                    "Cannot determine winner until " +
                            "all four players have played."
            );
        }

        return findWinningPlayedCard(
                hand,
                troop
        );
    }

    public PlayedCard determineCurrentWinner(
            Hand hand,
            Suit troop
    ) {

        if (hand.getPlayedCards().isEmpty()) {
            return null;
        }

        return findWinningPlayedCard(
                hand,
                troop
        );
    }

    private PlayedCard findWinningPlayedCard(
            Hand hand,
            Suit troop
    ) {

        PlayedCard winner =
                hand.getPlayedCards().get(0);

        for (int i = 1;
             i < hand.getPlayedCards().size();
             i++) {

            PlayedCard challenger =
                    hand.getPlayedCards().get(i);

            if (beats(
                    challenger.getCard(),
                    winner.getCard(),
                    hand.getLeadingSuit(),
                    troop
            )) {

                winner = challenger;
            }
        }

        return winner;
    }

    public boolean beats(
            Card challenger,
            Card currentWinner,
            Suit leadingSuit,
            Suit troop
    ) {

        /*
         * 2♥ ALWAYS wins.
         */
        if (challenger.isTwoOfHearts()) {
            return !currentWinner.isTwoOfHearts();
        }

        if (currentWinner.isTwoOfHearts()) {
            return false;
        }

        boolean challengerIsTroop =
                challenger.getSuit() == troop;

        boolean currentWinnerIsTroop =
                currentWinner.getSuit() == troop;

        /*
         * Troop beats non-troop.
         */
        if (challengerIsTroop &&
                !currentWinnerIsTroop) {

            return true;
        }

        if (!challengerIsTroop &&
                currentWinnerIsTroop) {

            return false;
        }

        /*
         * Both troop.
         */
        if (challengerIsTroop &&
                currentWinnerIsTroop) {

            return challenger.getRank().getValue()
                    > currentWinner.getRank().getValue();
        }

        /*
         * Neither is troop.
         */
        boolean challengerFollowsLead =
                challenger.getSuit() == leadingSuit;

        boolean currentWinnerFollowsLead =
                currentWinner.getSuit() == leadingSuit;

        if (challengerFollowsLead &&
                !currentWinnerFollowsLead) {

            return true;
        }

        if (!challengerFollowsLead &&
                currentWinnerFollowsLead) {

            return false;
        }

        /*
         * Both follow lead.
         */
        if (challengerFollowsLead &&
                currentWinnerFollowsLead) {

            return challenger.getRank().getValue()
                    > currentWinner.getRank().getValue();
        }

        return false;
    }
}