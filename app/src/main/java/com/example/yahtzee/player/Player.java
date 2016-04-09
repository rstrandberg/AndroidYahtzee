package com.example.yahtzee.player;


import com.example.yahtzee.Die;
import com.example.yahtzee.Yahtzee;
import com.example.yahtzee.score.ScoreType;

public abstract class Player{
	
	private static final String DEFAULT_NAME = "";
	protected static final int MAX_ROLLS = 3;
	
	private String name;
	protected int rollsLeft;
	protected Yahtzee game;
	
	public Player(Yahtzee game){
		this(DEFAULT_NAME, game);
	}
	
	public Player(String name, Yahtzee game){
		this.name = name;
		this.game = game;
		rollsLeft = 0;
	}
	
	public abstract void beginTurn();
	
	public abstract void endTurn();
	
	public String getName(){
		return this.name;
	}
	
	public int getRollsLeft(){
		return this.rollsLeft;
	}
	
	public boolean toggleDieHold(int index){
		Die[] dice = game.getDice();
		boolean newState = !dice[index].isHeld();
		dice[index].setHeld(newState);
		return newState;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int[] throwDice(){
		if(rollsLeft > 0){
		Die[] dice = game.getDice();
			int[] result = new int[dice.length];
			for(int i = 0; i < dice.length; i++){
				if(!dice[i].isHeld())
					result[i] = dice[i].roll();
				else
					result[i] = dice[i].getValue();
			}
			rollsLeft--;
			return result;
		}
		return null;
	}
	
	public boolean saveScore(ScoreType type){
		if(rollsLeft < MAX_ROLLS){
			if(game.saveScore(type, this)){
				for(Die d : game.getDice())
					d.setHeld(false);
				return true;
			}
		}
		return false;
	}
	
}
