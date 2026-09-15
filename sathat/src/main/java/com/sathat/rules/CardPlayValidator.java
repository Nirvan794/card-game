package com.sathat.rules;

import com.sathat.enums.Suit;
import com.sathat.model.Card;
import com.sathat.model.Hand;
import com.sathat.model.Player;

import java.util.ArrayList;
import java.util.List;

public class CardPlayValidator {

    public boolean isValidPlay(
            Player player,
            Card card,
            Hand hand,
            Suit troop
    ) {

        if (!player.hasCard(card)) {
            return false;
        }

        /*
         * First card of the hand.
         *
         * Anything can be played.
         */
        if (hand.getPlayedCards().isEmpty()) {
            return true;
        }

        Card leadingCard =
                hand.getLeadingCard();

        Suit leadingSuit =
                hand.getLeadingSuit();

        /*
         * 2 OF HEARTS LEADS
         *
         * If the player has troop,
         * they MUST play their highest troop.
         *
         * If they don't have troop,
         * they can play anything.
         */
        if (leadingCard.isTwoOfHearts()) {

            if (player.hasSuit(troop)) {

                Card highestTroop =
                        getHighestTroopCard(
                                player,
                                troop
                        );

                return card.equals(highestTroop);
            }

            return true;
        }

        /*
         * TROOP LEADS
         *
         * If player has troop,
         * they must play troop.
         */
        if (leadingSuit == troop) {

            if (player.hasSuit(troop)) {
                return card.getSuit() == troop;
            }

            return true;
        }

        /*
         * NORMAL LEAD
         *
         * If player has the leading suit,
         * they must follow it.
         */
        if (player.hasSuit(leadingSuit)) {
            return card.getSuit() == leadingSuit;
        }

        /*
         * Player does not have the leading suit.
         *
         * They may play anything.
         */
        return true;
    }

    public List<Card> getLegalCards(
            Player player,
            Hand hand,
            Suit troop
    ) {

        List<Card> legalCards =
                new ArrayList<>();

        for (Card card : player.getCards()) {

            if (isValidPlay(
                    player,
                    card,
                    hand,
                    troop
            )) {

                legalCards.add(card);
            }
        }

        return legalCards;
    }

    public Card getHighestTroopCard(
            Player player,
            Suit troop
    ) {

        Card highestTroop = null;

        for (Card card : player.getCards()) {

            if (card.getSuit() != troop) {
                continue;
            }

            if (highestTroop == null ||
                    card.getRank().getValue()
                            > highestTroop.getRank().getValue()) {

                highestTroop = card;
            }
        }

        return highestTroop;
    }

    public Card getLowestCard(
            List<Card> cards
    ) {

        Card lowest = null;

        for (Card card : cards) {

            if (lowest == null ||
                    card.getRank().getValue()
                            < lowest.getRank().getValue()) {

                lowest = card;
            }
        }

        return lowest;
    }
}