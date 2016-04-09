package com.example.yahtzee.player;

import com.example.yahtzee.Yahtzee;

public class HumanPlayer extends Player {

	public HumanPlayer(Yahtzee game) {
		super(game);
	}

	public HumanPlayer(String name, Yahtzee game) {
		super(name, game);
	}

	@Override
	public void beginTurn() {
		rollsLeft = Player.MAX_ROLLS;
	}

	@Override
	public void endTurn() {
		rollsLeft = 0;
		game.endGameTurn();
	}

}
