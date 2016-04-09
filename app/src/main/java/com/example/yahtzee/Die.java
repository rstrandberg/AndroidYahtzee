package com.example.yahtzee;

import java.util.Random;

public class Die implements Comparable<Die>{

	private int sides;
	private int currentValue;
	private boolean holdStatus;
	private Random random;
	
	public Die(){
		this(6);
	}
	
	public Die(int sides){
		this.sides = sides;
		this.random = new Random();
		roll();
	}
	
	public int getValue(){
		return currentValue;
	}
	
	public boolean isHeld(){
		return holdStatus;
	}
	
	public void setHeld(boolean isHeld){
		this.holdStatus = isHeld;
	}
	public void setValue(int value){
		this.currentValue = value;
	}
	
	public int roll(){
		currentValue = 1 + random.nextInt(sides);
		return currentValue;
	}
	public void reset(){
		holdStatus = false;
		roll();
	}
	
	public static void sort(Die[] dice){
		Die tmp;
		int i;
		for(int j = 1; j < dice.length; j++){
			tmp = dice[j];
			for(i = j ; i > 0 && dice[i-1].compareTo(tmp) >= 0; i--){
				dice[i] = dice[i-1];
			}
			dice[i] = tmp;
		}
	}

	@Override
	public int compareTo(Die other) {
		return this.currentValue - other.currentValue;
	}
}
