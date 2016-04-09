package com.example.yahtzee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.example.yahtzee.player.*;
import com.example.yahtzee.score.ScoreChart;
import com.example.yahtzee.score.ScoreHelper;
import com.example.yahtzee.score.ScoreType;

public class Yahtzee extends Observable{
	
	public static final int FINAL_TURN = 15;

	private GameMode gameMode;
	private Player currentPlayer;
	private int currentTurn;
	private int playerCount;
	private boolean started;
	private List<Player> players;
	private Iterator<Player> playerIterator;
	private Die[] dice;
	private Map<Player, ScoreChart> scoreCharts;
	
	public Yahtzee(){
		dice = new Die[5];
		for(int i = 0; i < dice.length; i++)
			dice[i] = new Die();
		players = new ArrayList<Player>(4);
		scoreCharts = new HashMap<Player, ScoreChart>();
	}
	
	public void setGameMode(GameMode gameMode){
		if (gameMode != null)
			this.gameMode = gameMode;
	}
	
	public GameMode getGameMode(){
		return this.gameMode;
	}
	
	public void addPlayer(String name, boolean isComputerPlayer){
		if (!started){
			Player p;
			if(isComputerPlayer)
				p = new ComputerPlayer(name, this);
			else
				p = new HumanPlayer(name, this);
			players.add(p);
			scoreCharts.put(p, new ScoreChart());
		}
	}
	
	public void setPlayerCount(int playerCount){
		this.playerCount = playerCount;
	}
	public int getPlayerCount(){
		return this.playerCount;
	}
	public int getCurrentTurn(){
		return currentTurn;
	}
	public Player[] getPlayers(){
		return players.toArray(new Player[0]);
	}
	
	public ScoreChart getScoreChart(Player player){
		return scoreCharts.get(player);
	}
	
	public Die[] getDice(){
		return dice;
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	public int[] sortDice(){
		Die.sort(dice);
		int[] result = new int[dice.length];
		for(int i = 0; i < dice.length; i++){
			result[i] = dice[i].getValue();
		}
		return result;
	}
	
	public boolean[] getHoldStatus(){
		boolean[] result = new boolean[5];
		for(int i = 0; i < dice.length; i++){
			result[i] = dice[i].isHeld();
		}
		return result;
	}
	public boolean saveScore(ScoreType type, Player player){
		int value = ScoreHelper.calculate(type, getDice());
		if(getScoreChart(player).addScore(type, value)){
			setChanged();
			notifyObservers("score"+","+type.name()+","+value+","+currentTurn);
			return true;
			}
		return false;
	}
	public boolean isActive(){
		return started;
	}
	
	public void init(){
		if(!started){
			players.clear();
			scoreCharts.clear();
			currentTurn = 1;
			currentPlayer = null;
			for (Die d: dice)
				d.reset();
		}
	}
	public void startGame(){
		setChanged();
		notifyObservers("start");
		playerIterator = players.iterator();
		currentPlayer = playerIterator.next();
		started = true;
		beginGameTurn();
	}
	public void endGame(){
//		System.out.println("Game has ended");
		started = false;
		playerIterator = null;
		setChanged();
		notifyObservers("endGame");
	}
	public void beginGameTurn(){
		if(currentTurn <= FINAL_TURN){
			currentPlayer.beginTurn();
			setChanged();
			notifyObservers("beginTurn");
		}else{
			endGame();
		}
	}
	public void endGameTurn(){
		setChanged();
		notifyObservers("endTurn");
		if(!playerIterator.hasNext()){
			playerIterator = players.iterator();
			currentTurn++;
		}
		currentPlayer = playerIterator.next();
		beginGameTurn();
	}
}
