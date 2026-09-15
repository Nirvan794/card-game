package com.sathat.model;

import com.sathat.enums.TeamId;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private final TeamId id;
    private final List<Player> players;

    private int handsWon;

    public Team(TeamId id) {
        this.id = id;
        this.players = new ArrayList<>();
        this.handsWon = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void winHand() {
        handsWon++;
    }

    public TeamId getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getHandsWon() {
        return handsWon;
    }

    public boolean hasWonGame() {
        return handsWon >= 7;
    }

    @Override
    public String toString() {

        if (id == TeamId.TEAM_ONE) {
            return "Team 1";
        }

        return "Team 2";
    }
}