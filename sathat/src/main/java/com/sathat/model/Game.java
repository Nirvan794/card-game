package com.sathat.model;

import com.sathat.enums.GameStatus;
import com.sathat.enums.Suit;

import java.util.List;

public class Game {

    private final List<Player> players;
    private final List<Team> teams;

    private Deck deck;

    private Suit troop;

    private Player troopChooser;
    private Player currentPlayer;

    private GameStatus status;

    public Game(
            List<Player> players,
            List<Team> teams
    ) {
        this.players = players;
        this.teams = teams;
        this.status = GameStatus.NOT_STARTED;
    }

    public void createDeck() {
        deck = new Deck();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Deck getDeck() {
        return deck;
    }

    public Suit getTroop() {
        return troop;
    }

    public void setTroop(Suit troop) {
        this.troop = troop;
    }

    public Player getTroopChooser() {
        return troopChooser;
    }

    public void setTroopChooser(Player troopChooser) {
        this.troopChooser = troopChooser;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public boolean isFinished() {

        for (Team team : teams) {

            if (team.hasWonGame()) {
                return true;
            }
        }

        return false;
    }

    public Team getWinningTeam() {

        for (Team team : teams) {

            if (team.hasWonGame()) {
                return team;
            }
        }

        return null;
    }
}