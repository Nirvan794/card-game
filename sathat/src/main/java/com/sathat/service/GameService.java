package com.sathat.service;

import com.sathat.enums.GameStatus;
import com.sathat.enums.Suit;
import com.sathat.model.Game;
import com.sathat.model.Player;
import com.sathat.model.Team;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameService {

    private final DealService dealService;
    private final HandService handService;

    private final Random random;
    private final Scanner scanner;

    public GameService(
            DealService dealService,
            HandService handService,
            Scanner scanner
    ) {

        this.dealService =
                dealService;

        this.handService =
                handService;

        this.scanner =
                scanner;

        this.random =
                new Random();
    }

    public void startGame(Game game) {

        game.setStatus(
                GameStatus.DEALING
        );

        printWelcome();

        /*
         * Create and shuffle deck.
         */
        game.createDeck();

        /*
         * Randomly select the player
         * who chooses troop.
         */
        Player troopChooser =
                chooseTroopPlayer(
                        game.getPlayers()
                );

        game.setTroopChooser(
                troopChooser
        );

        System.out.println();

        System.out.println(
                troopChooser.getName() +
                        " will choose the troop."
        );

        /*
         * Deal first 5 cards.
         */
        dealService.dealInitialFive(
                game.getPlayers(),
                game.getDeck()
        );

        System.out.println();

        System.out.println(
                "First five cards dealt."
        );

        /*
         * Only reveal cards to the player
         * if they are the troop chooser.
         *
         * Otherwise the computer chooses
         * automatically.
         */
        Suit troop =
                chooseTroop(
                        troopChooser
                );

        game.setTroop(troop);

        System.out.println();

        System.out.println(
                "Troop: " +
                        troop.getDisplayName() +
                        " " +
                        troop.getSymbol()
        );

        /*
         * Deal remaining 8 cards.
         */
        dealService.dealRemainingCards(
                game.getPlayers(),
                game.getDeck()
        );

        System.out.println();

        System.out.println(
                "All cards have been dealt."
        );

        /*
         * The troop chooser leads
         * the first hand.
         */
        game.setCurrentPlayer(
                troopChooser
        );

        game.setStatus(
                GameStatus.IN_PROGRESS
        );

        playGame(game);

        game.setStatus(
                GameStatus.FINISHED
        );

        printFinalResult(game);
    }

    private void playGame(Game game) {

        while (!game.isFinished()) {

            Player leader =
                    game.getCurrentPlayer();

            Player winner =
                    handService.playHand(
                            leader,
                            game.getPlayers(),
                            game.getTroop()
                    );

            /*
             * Winner's team gets one hand.
             */
            winner.getTeam().winHand();

            printScore(game);

            /*
             * Winner leads next.
             */
            game.setCurrentPlayer(
                    winner
            );
        }
    }

    private Player chooseTroopPlayer(
            List<Player> players
    ) {

        int index =
                random.nextInt(
                        players.size()
                );

        return players.get(index);
    }

    private Suit chooseTroop(
            Player player
    ) {

        Suit[] suits =
                Suit.values();

        /*
         * Human player.
         */
        if (player.isHuman()) {

            printPlayerCards(player);

            System.out.println();

            System.out.println(
                    "Choose your troop:"
            );

            for (int i = 0;
                 i < suits.length;
                 i++) {

                System.out.println(
                        i +
                                ": " +
                                suits[i].getDisplayName() +
                                " " +
                                suits[i].getSymbol()
                );
            }

            System.out.print(
                    "Choose suit: "
            );

            int choice =
                    scanner.nextInt();

            while (
                    choice < 0 ||
                            choice >= suits.length
            ) {

                System.out.println(
                        "Invalid suit."
                );

                System.out.print(
                        "Choose suit: "
                );

                choice =
                        scanner.nextInt();
            }

            return suits[choice];
        }

        /*
         * Computer player.
         *
         * For now choose randomly.
         */
        Suit selectedSuit =
                suits[
                        random.nextInt(
                                suits.length
                        )
                        ];

        System.out.println();

        System.out.println(
                player.getName() +
                        " chose " +
                        selectedSuit.getDisplayName() +
                        " as troop."
        );

        return selectedSuit;
    }

    private void printPlayerCards(
            Player player
    ) {

        System.out.println();

        System.out.println(
                "Your first five cards:"
        );

        for (int i = 0;
             i < player.getCards().size();
             i++) {

            System.out.println(
                    i +
                            ": " +
                            player.getCards().get(i)
            );
        }
    }

    private void printScore(
            Game game
    ) {

        System.out.println();
        System.out.println("------------------------------");
        System.out.println("           SCORE");
        System.out.println("------------------------------");

        for (Team team :
                game.getTeams()) {

            System.out.println(
                    team +
                            ": " +
                            team.getHandsWon()
            );
        }

        System.out.println("------------------------------");
    }

    private void printWelcome() {

        System.out.println();
        System.out.println("================================");
        System.out.println("             SATHAT");
        System.out.println("================================");
        System.out.println("You: Player 1");
        System.out.println("Team 1: Player 1 + Player 3");
        System.out.println("Team 2: Player 2 + Player 4");
        System.out.println("Winning score: 7 hands");
        System.out.println("================================");
    }

    private void printFinalResult(
            Game game
    ) {

        Team winningTeam =
                game.getWinningTeam();

        System.out.println();
        System.out.println("================================");
        System.out.println("           GAME OVER");
        System.out.println("================================");

        System.out.println();

        System.out.println(
                winningTeam +
                        " WINS!"
        );

        printScore(game);
    }
}