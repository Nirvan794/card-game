package com.sathat;

import com.sathat.enums.TeamId;
import com.sathat.model.Game;
import com.sathat.model.Player;
import com.sathat.model.Team;
import com.sathat.rules.CardPlayValidator;
import com.sathat.rules.HandWinnerCalculator;
import com.sathat.service.DealService;
import com.sathat.service.GameService;
import com.sathat.service.HandService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
         * Teams
         *
         * Team 1:
         * Player 1 + Player 3
         *
         * Team 2:
         * Player 2 + Player 4
         */
        Team teamOne =
                new Team(
                        TeamId.TEAM_ONE
                );

        Team teamTwo =
                new Team(
                        TeamId.TEAM_TWO
                );

        /*
         * Only Player 1 is controlled
         * by the human.
         *
         * The other three are computers.
         */
        Player player1 =
                new Player(
                        1,
                        "Player 1",
                        teamOne,
                        true
                );

        Player player2 =
                new Player(
                        2,
                        "Player 2",
                        teamTwo,
                        false
                );

        Player player3 =
                new Player(
                        3,
                        "Player 3",
                        teamOne,
                        false
                );

        Player player4 =
                new Player(
                        4,
                        "Player 4",
                        teamTwo,
                        false
                );

        /*
         * Add players to teams.
         */
        teamOne.addPlayer(player1);
        teamOne.addPlayer(player3);

        teamTwo.addPlayer(player2);
        teamTwo.addPlayer(player4);

        /*
         * Table order.
         */
        List<Player> players =
                new ArrayList<>(
                        Arrays.asList(
                                player1,
                                player2,
                                player3,
                                player4
                        )
                );

        List<Team> teams =
                new ArrayList<>(
                        Arrays.asList(
                                teamOne,
                                teamTwo
                        )
                );

        /*
         * Create game.
         */
        Game game =
                new Game(
                        players,
                        teams
                );

        /*
         * Services.
         */
        Scanner scanner =
                new Scanner(System.in);

        CardPlayValidator validator =
                new CardPlayValidator();

        HandWinnerCalculator winnerCalculator =
                new HandWinnerCalculator();

        DealService dealService =
                new DealService();

        HandService handService =
                new HandService(
                        validator,
                        winnerCalculator,
                        scanner
                );

        GameService gameService =
                new GameService(
                        dealService,
                        handService,
                        scanner
                );

        /*
         * Start game.
         */
        gameService.startGame(game);
    }
}