package com.example.yahtzee.score;

public enum ScoreType {
	ONES("Ones", 1),
	TWOS("Twos", 2),
	THREES("Threes", 3),
	FOURS("Fours", 4),
	FIVES("Fives", 5),
	SIXES("Sixes", 6),
	BONUS("Bonus", 50),
	PAIR("Pair", 2),
	TWO_PAIRS("Two Pairs", 2),
	THREE_OF_A_KIND("Three of a kind", 3),
	FOUR_OF_A_KIND("Four of a kind", 4),
	SMALL_STRAIGHT("Small straight", 15),
	LARGE_STRAIGHT("Large straight", 20),
	HOUSE("House", -1),
	CHANCE("Chance", -1),
	YAHTZEE("Yahtzee", 50);
	
	private String name;
	private int value;
	
	private ScoreType(String name, int value){
		this.name = name;
		this.value = value;
	}
	public String getName(){
		return this.name;
	}
	public int getValue(){
		return this.value;
	}
	
}
