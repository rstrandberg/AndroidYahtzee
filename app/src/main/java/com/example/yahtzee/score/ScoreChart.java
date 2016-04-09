package com.example.yahtzee.score;


public class ScoreChart {

	private static final int DEFAULT_SIZE = 16;
	private static final int BONUS_THRESHOLD = 63;
	private ScoreList scores;
	private int upperScore;
	private boolean hasBonus;
	
	
	public ScoreChart(){
		scores = new ScoreList(DEFAULT_SIZE);
		upperScore = 0;
		hasBonus = false;
	}
	
	public boolean addScore(Score score){
		return scores.add(score);
	}
	
	public boolean addScore(ScoreType type, int value){
		Score temp = scores.get(type);
		if(temp == null){
			temp = new Score(type, value);			
		}else {
			if(temp.getValue() == Score.UNASSIGNED)
				temp.setValue(value);
			else
				return false;
		}
		if(!hasBonus)
			checForkBonus(type, value);
		return addScore(temp);
	}
	
	public Score[] getSortedScoresList(){
		scores.sort();
		return scores.toArray();
	}
	
	public Score[] getScoresList(){
		return scores.toArray();
	}
	
	public boolean hasValue(ScoreType type){
		Score temp = scores.get(type);
		if(temp != null && temp.getValue() == Score.UNASSIGNED)
			return true;
		return false;
	}
	public int getValueForType(ScoreType type){
		Score s =  scores.get(type);
		if(s == null)
			return -1;
		return s.getValue();
	}
	
	public boolean hasBonus(){
		return hasBonus;
	}
	
	private void checForkBonus(ScoreType type, int value){
		switch(type){
			case ONES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				upperScore += value;
				break;
			default:
				return;
		}
		if (upperScore >= BONUS_THRESHOLD){
			hasBonus = true;
			addScore(new Score(ScoreType.BONUS, ScoreType.BONUS.getValue()));
		}
	}
	public int getFinalScore(){
		int finalScore = 0;
		for(Score s: scores.toArray())
			finalScore += s.getValue();
		return finalScore;
	}
}
