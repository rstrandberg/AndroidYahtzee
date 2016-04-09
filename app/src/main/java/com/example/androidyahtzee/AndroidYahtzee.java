package com.example.androidyahtzee;

import android.support.v4.util.Pair;

import com.example.androidyahtzee.dto.ChartEntry;
import com.example.androidyahtzee.dto.DiceRoll;
import com.example.androidyahtzee.dto.DieState;
import com.example.androidyahtzee.dto.GameState;
import com.example.androidyahtzee.dto.ScoreChart;
import com.example.yahtzee.Die;
import com.example.yahtzee.Yahtzee;
import com.example.yahtzee.player.Player;
import com.example.yahtzee.score.Score;
import com.example.yahtzee.score.ScoreType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AndroidYahtzee {
    private Yahtzee game;

    public AndroidYahtzee() {
        game = new Yahtzee();
        game.init();
    }

    public void addPlayer(String name){
        game.addPlayer(name, false);
    }

    public void startGame(){
        game.startGame();
    }

    public DiceRoll rollDice(){
        List<DieState> dList = new ArrayList<>(5);
        Player p = game.getCurrentPlayer();
        p.throwDice();
        for(Die d: game.getDice()){
            dList.add(new DieState(d.getValue(), d.isHeld()));
        }
        return new DiceRoll(p.getRollsLeft(), dList);
    }

    public DieState holdDie(int index){
        Player p = game.getCurrentPlayer();
        if(p.getRollsLeft() == 3){
            return new DieState(-1, false);
        }
        p.toggleDieHold(index);
        Die d = game.getDice()[index];
        return new DieState(d.getValue(), d.isHeld());
    }

    public GameState saveScore(String scoreType){
        ScoreType type = ScoreType.valueOf(scoreType);
        Player p = game.getCurrentPlayer();
        boolean hasBonus = game.getScoreChart(p).hasBonus();
        GameState state = null;
        if(p.saveScore(type)){
            List<ChartEntry> entries = new ArrayList<>();
            ChartEntry ent = new ChartEntry(scoreType, game.getScoreChart(p).getValueForType(type));
            entries.add(ent);
            if(!hasBonus && game.getScoreChart(p).hasBonus()){
                ent = new ChartEntry(ScoreType.BONUS.name(),ScoreType.BONUS.getValue());
                entries.add(ent);
            }
            List<ScoreChart> scoreCharts = new ArrayList<>(1);
            scoreCharts.add(new ScoreChart(p.getName(), entries));

            p.endTurn();
            p = game.getCurrentPlayer();
            List<DieState> dieStates = new ArrayList<>(5);
            for(int i = 0; i < 5; i++){
                dieStates.add(new DieState(-1, false));
            }
            DiceRoll diceRoll = new DiceRoll(p.getRollsLeft(), dieStates);
            state = new GameState(p.getName(), game.getCurrentTurn(), game.isActive(), diceRoll, scoreCharts);
        }
        return state;
    }

    public GameState getGameState(){
        Player player = game.getCurrentPlayer();

        List<DieState> dList = new ArrayList<>(5);
        for(Die d: game.getDice()){
            dList.add(new DieState(d.getValue(), d.isHeld()));
        }
        DiceRoll diceRoll = new DiceRoll(player.getRollsLeft(), dList);

        List<ScoreChart> scoreCharts = new ArrayList<>();
        for(Player p: game.getPlayers()){
            List<ChartEntry> entries = new ArrayList<>();
            for(Score s :game.getScoreChart(p).getScoresList()){
                ChartEntry e = new ChartEntry(s.getType().name(), s.getValue());
                entries.add(e);
            }
            scoreCharts.add(new ScoreChart(p.getName(), entries));
        }
        return new GameState(player.getName(), game.getCurrentTurn(), game.isActive(), diceRoll, scoreCharts);
    }

    private int[] getDieValues(){
        int[] dValues = new int[5];
        int index = 0;
        for(Die d: game.getDice()){
            dValues[index++] = d.getValue();
        }
        return dValues;
    }

    public List<Pair<String, Integer>> getFinalScores(){
        List<Pair<String, Integer>> finalScores = new ArrayList<>(4);
        if(!game.isActive()){
            for(Player p : game.getPlayers()){
                finalScores.add(Pair.create(p.getName(), game.getScoreChart(p).getFinalScore()));
            }
            Collections.sort(finalScores, new Comparator<Pair<String, Integer>>() {
               @Override
                public int compare(final Pair<String, Integer> o1, final Pair<String, Integer>o2) {
                   return (o1.second < o2.second) ? -1 : (o1.second > o2.second) ? 1 : 0;
               }
            });
        }
        return finalScores;
    }
}
