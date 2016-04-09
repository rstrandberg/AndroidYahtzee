package com.example.androidyahtzee.dto;

import java.util.List;

public class ScoreChart {

	private final String player;
	private final List<ChartEntry> entries;
	
	public ScoreChart(String player, List<ChartEntry> entries) {
	    this.player = player;
        this.entries = entries;
	}

	public String getPlayer() {
		return player;
	}

	public List<ChartEntry> getEntries() {
		return entries;
	}

}
