package com.example.yahtzee.score;

import com.example.yahtzee.*;

public class ScoreHelper {

	public static int calculate(ScoreType type, Die[] dice){
		Die[] dCopy = dice.clone();
		Die.sort(dCopy);
		int sum = 0;
		int count = 0;
		switch(type){
			case ONES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				for(Die d: dCopy)
					count += (d.getValue() == type.getValue()) ? 1 : 0;
				sum = count * type.getValue();
				break;
			case PAIR:
				for(int i = dCopy.length-2; i >= 0; i--){
					if(dCopy[i+1].getValue() == dCopy[i].getValue()){
						sum = dCopy[i].getValue()*2;
						break;
					}
				}
				break;
			case TWO_PAIRS:
				int p1 = 0, p2 = 0;
				for(int i = 0, j = dCopy.length-1; i < j ; ){
					if(p1 == 0 && dCopy[i].getValue() == dCopy[++i].getValue())
						p1 = dCopy[i].getValue();
					if(p2 == 0 && dCopy[j].getValue() == dCopy[--j].getValue())
						p2 = dCopy[j].getValue();
					if(p1 != 0 && p2 != 0)
						break;
				}
				sum = (p1 != p2 && p1 > 0 && p2 > 0) ? (p1+p2)*2 : 0;
				break;
			case THREE_OF_A_KIND:
				for(int i = dCopy.length-3; i >= 0; i--){
					if(dCopy[i].getValue() == dCopy[i+1].getValue() &&
							dCopy[i].getValue() == dCopy[i+2].getValue()){
						sum = dCopy[i].getValue()*3;
						break;
					}
				}
				break;
			case FOUR_OF_A_KIND:
				count = 1;
				for(int i = dCopy.length-2; i >= 0; i--){
					count = (dCopy[i].getValue() == dCopy[i+1].getValue()) ? 
							count+1 : 1;
					if(count == 4){
						sum = dCopy[i].getValue()*4;
						break;
					}
				}
				break;
			case SMALL_STRAIGHT:
				if(isSmallStraight(dCopy, 0))
					sum = type.getValue();
				break;
			case LARGE_STRAIGHT:
				if(isLargeStraight(dCopy, 0))
					sum = type.getValue();
				break;
			case HOUSE:
				if(dCopy[0].getValue() == dCopy[1].getValue() && dCopy[3].getValue() == dCopy[4].getValue()){
					if(dCopy[2].getValue() == dCopy[1].getValue())
						sum = dCopy[2].getValue()*3 + dCopy[3].getValue()*2;
					else if(dCopy[2].getValue() == dCopy[3].getValue())
						sum = dCopy[1].getValue()*2 + dCopy[2].getValue()*3;
				}
				break;
			case CHANCE:
				for(Die d: dCopy)
					sum += d.getValue();
				break;
			case YAHTZEE:
				for(Die d: dCopy){
					if(d.getValue() == dCopy[0].getValue())
						count++;
					else
						break;
				}
				sum = (count == 5) ? 50 : 0;
				break;
			default:
		}
		return sum;
	}
	
	public static boolean isSmallStraight(Die[] d, int index){
		if(index < d.length){
			return (d[index].getValue() == index+1) ? 
					isSmallStraight(d, index+1) : false;
		}
		return true;
	}
	public static boolean isLargeStraight(Die[] d, int index){
		if(index < d.length){
			return (d[index].getValue() == index+2) ? 
					isLargeStraight(d, index+1) : false;
		}
		return true;
	}
}
