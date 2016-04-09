package com.example.androidyahtzee.dto;

public class DieState {
	private final int value;
	private final boolean held;
	
	public DieState(int value, boolean held) {
	    this.value = value;
        this.held = held;
	}

	public int getValue() {
		return value;
	}

	public boolean isHeld() {
		return held;
	}

}
