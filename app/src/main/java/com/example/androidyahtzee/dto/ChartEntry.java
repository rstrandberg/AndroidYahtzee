package com.example.androidyahtzee.dto;

public class ChartEntry {

	private final String type;
	private final int value;

	public ChartEntry(String type, int value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	
}
