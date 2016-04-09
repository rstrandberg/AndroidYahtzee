package com.example.yahtzee.score;


public class ScoreList {
	
	private Score[] data;
	private int size;
	
	public ScoreList(int maxSize){
		this.data = new Score[maxSize];
		this.size = 0;
	}
	
	public boolean add(Score score){
		if(size < data.length){
			if(!contains(score)){
				data[size] = score;
				size++;
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Score score){
		for(int i = 0; i < size; i++){
			if(data[i].equals(score))
				return true;
		}
		return false;
	}
	
	public Score get(ScoreType type){
		for(int i = 0; i < size; i++){
			if(data[i].getType() == type)
				return data[i];
		}
		return null;
	}
	
	public void sort(){
		if(size > 1){
			Score[] workSpace = new Score[size];
			sort(workSpace, 0, size-1);
		}
	}
	private void sort(Score[] workSpace, int lower, int upper){
		if(lower < upper){
			int mid = (lower + upper) / 2;
			sort(workSpace, lower, mid);
			sort(workSpace, mid+1, upper);
			merge(workSpace, lower, mid, upper);
		}
	}
	private void merge(Score[] workSpace, int begin, int mid, int end){
		int loPtr = begin;
		int hiPtr = mid+1;
		int wsIndex = begin;
		
		while (loPtr <= mid && hiPtr <= end){
			workSpace[wsIndex++] = (data[loPtr].getType().compareTo(data[hiPtr].getType()) < 0)
					? data[loPtr++] : data[hiPtr++];
		}
		while (loPtr <= mid)
			workSpace[wsIndex++] = data[loPtr++];
		while (hiPtr <= end)
			workSpace[wsIndex++] = data[hiPtr++];
		System.arraycopy(workSpace, begin, data, begin, end-begin+1);
	}
	
	public Score[] toArray(){
		Score[] arr = new Score[size];
		System.arraycopy(data, 0, arr, 0, size);
		return arr;
	}
	
	
}
