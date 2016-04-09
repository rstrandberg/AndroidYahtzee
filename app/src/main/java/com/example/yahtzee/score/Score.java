package com.example.yahtzee.score;


public class Score {
	
	public static final int UNASSIGNED = -1;
	
	private ScoreType type;
	private int value;
	
	public Score(ScoreType type, int value){
		this.type = type;
		this.value = value;
	}
	
	public ScoreType getType(){
		return this.type;
	}
	public int getValue(){
		return this.value;
	}
	public void setValue(int value){
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (type != other.type)
			return false;
		return true;
	}
	
}
