package com.example.androidyahtzee.dto;

import java.util.List;

public class GameState {
	
	private final String currentPlayer;
	private final int gameTurn;
	private final boolean active;
	private final DiceRoll diceRoll;
	private final List<ScoreChart> scoreCharts;
	
	public GameState(String currentPlayer, int gameTurn, boolean active, DiceRoll diceRoll, List<ScoreChart> scoreCharts) {
        this.currentPlayer = currentPlayer;
        this.gameTurn = gameTurn;
        this.active = active;
        this.diceRoll = diceRoll;
        this.scoreCharts = scoreCharts;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public int getGameTurn() {
		return gameTurn;
	}

	public boolean isActive() {
		return active;
	}

	public List<ScoreChart> getScoreCharts() {
		return scoreCharts;
	}

	public DiceRoll getDiceRoll() {
		return diceRoll;
	}

}
