package com.example.androidyahtzee.dto;

import java.util.List;

public class DiceRoll {
	
	private final int rollsLeft;
	private final List<DieState> dice;
	
	public DiceRoll(int rollsLeft, List<DieState> dice) {
		this.rollsLeft = rollsLeft;
        this.dice = dice;
	}

	public int getRollsLeft() {
		return rollsLeft;
	}


	public List<DieState> getDice() {
		return dice;
	}

	
	

}
