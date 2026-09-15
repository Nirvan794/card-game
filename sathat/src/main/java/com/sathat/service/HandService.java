package com.sathat.service;

import com.sathat.enums.Suit;
import com.sathat.model.Card;
import com.sathat.model.Hand;
import com.sathat.model.PlayedCard;
import com.sathat.model.Player;
import com.sathat.rules.CardPlayValidator;
import com.sathat.rules.HandWinnerCalculator;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HandService {

    private final CardPlayValidator cardPlayValidator;
    private final HandWinnerCalculator winnerCalculator;

    private final Scanner scanner;
    private final Random random;

    public HandService(
            CardPlayValidator cardPlayValidator,
            HandWinnerCalculator winnerCalculator,
            Scanner scanner
    ) {

        this.cardPlayValidator =
                cardPlayValidator;

        this.winnerCalculator =
                winnerCalculator;

        this.scanner = scanner;
        this.random = new Random();
    }

    public Player playHand(
            Player leader,
            List<Player> players,
            Suit troop
    ) {

        Hand hand =
                new Hand(
                        leader,
                        troop
                );

        System.out.println();
        System.out.println("==============================");
        System.out.println("          NEW HAND");
        System.out.println("==============================");

        System.out.println(
                "Leader: " +
                        leader.getName()
        );

        int leaderIndex =
                players.indexOf(leader);

        for (int i = 0;
             i < players.size();
             i++) {

            Player player =
                    players.get(
                            (leaderIndex + i)
                                    % players.size()
                    );

            playCard(
                    player,
                    hand,
                    troop
            );
        }

        printPlayedCards(hand);

        Player winner =
                winnerCalculator.determineWinner(
                        hand,
                        troop
                );

        System.out.println();
        System.out.println(
                "Winner: " +
                        winner.getName()
        );

        return winner;
    }

    private void playCard(
            Player player,
            Hand hand,
            Suit troop
    ) {

        List<Card> legalCards =
                cardPlayValidator.getLegalCards(
                        player,
                        hand,
                        troop
                );

        Card selectedCard;

        if (player.isHuman()) {

            selectedCard =
                    chooseHumanCard(
                            player,
                            legalCards
                    );

        } else {

            selectedCard =
                    chooseComputerCard(
                            player,
                            legalCards,
                            hand,
                            troop
                    );
        }

        player.removeCard(selectedCard);

        hand.addPlayedCard(
                player,
                selectedCard
        );

        /*
         * We don't need to print the computer
         * card here because chooseComputerCard()
         * already prints it.
         */
        if (player.isHuman()) {

            System.out.println(
                    player.getName() +
                            " played " +
                            selectedCard
            );
        }
    }

    private Card chooseHumanCard(
            Player player,
            List<Card> legalCards
    ) {

        System.out.println();
        System.out.println(
                "Your turn (" +
                        player.getName() +
                        ")"
        );

        System.out.println();
        System.out.println("Your cards:");

        for (int i = 0;
             i < player.getCards().size();
             i++) {

            Card card =
                    player.getCards().get(i);

            boolean legal =
                    legalCards.contains(card);

            System.out.println(
                    i +
                            ": " +
                            card +
                            (legal
                                    ? " [LEGAL]"
                                    : " [NOT LEGAL]")
            );
        }

        System.out.println();

        System.out.print(
                "Choose card index: "
        );

        int choice =
                scanner.nextInt();

        while (
                choice < 0 ||
                        choice >= player.getCards().size() ||
                        !legalCards.contains(
                                player.getCards().get(choice)
                        )
        ) {

            System.out.println(
                    "Invalid card."
            );

            System.out.print(
                    "Choose card index: "
            );

            choice =
                    scanner.nextInt();
        }

        return player.getCards().get(choice);
    }

    private Card chooseComputerCard(
            Player player,
            List<Card> legalCards,
            Hand hand,
            Suit troop
    ) {

        Card selectedCard;

        /*
         * If computer is leading,
         * use a simple strategy.
         */
        if (hand.getPlayedCards().isEmpty()) {

            selectedCard =
                    chooseComputerLead(
                            legalCards,
                            troop
                    );

        } else {

            PlayedCard currentWinner =
                    winnerCalculator.determineCurrentWinner(
                            hand,
                            troop
                    );

            Player currentWinningPlayer =
                    currentWinner.getPlayer();

            /*
             * Teammate is winning.
             *
             * Don't waste a strong card.
             */
            if (currentWinningPlayer.getTeam()
                    == player.getTeam()) {

                selectedCard =
                        cardPlayValidator.getLowestCard(
                                legalCards
                        );

            } else {

                /*
                 * Opponent is winning.
                 *
                 * Try to beat them using
                 * the cheapest possible card.
                 */
                selectedCard =
                        findCheapestWinningCard(
                                legalCards,
                                currentWinner,
                                hand,
                                troop
                        );

                /*
                 * Can't win.
                 *
                 * Throw lowest card.
                 */
                if (selectedCard == null) {

                    selectedCard =
                            cardPlayValidator.getLowestCard(
                                    legalCards
                            );
                }
            }
        }

        System.out.println(
                player.getName() +
                        " played " +
                        selectedCard
        );

        return selectedCard;
    }

    private Card chooseComputerLead(
            List<Card> legalCards,
            Suit troop
    ) {

        /*
         * Don't lead 2♥ unless necessary.
         *
         * For now, select the lowest card.
         */
        Card lowest =
                cardPlayValidator.getLowestCard(
                        legalCards
                );

        return lowest;
    }

    private Card findCheapestWinningCard(
            List<Card> legalCards,
            PlayedCard currentWinner,
            Hand hand,
            Suit troop
    ) {

        Card cheapestWinner = null;

        for (Card card : legalCards) {

            if (!winnerCalculator.beats(
                    card,
                    currentWinner.getCard(),
                    hand.getLeadingSuit(),
                    troop
            )) {

                continue;
            }

            if (cheapestWinner == null ||
                    card.getRank().getValue()
                            < cheapestWinner.getRank().getValue()) {

                cheapestWinner = card;
            }
        }

        return cheapestWinner;
    }

    private void printPlayedCards(
            Hand hand
    ) {

        System.out.println();
        System.out.println("------------------------------");
        System.out.println("Cards played:");

        for (PlayedCard playedCard :
                hand.getPlayedCards()) {

            System.out.println(
                    playedCard.getPlayer().getName() +
                            " played " +
                            playedCard.getCard()
            );
        }

        System.out.println("------------------------------");
    }
}