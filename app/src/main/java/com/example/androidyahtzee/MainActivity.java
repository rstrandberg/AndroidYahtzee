package com.example.androidyahtzee;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidyahtzee.dto.ChartEntry;
import com.example.androidyahtzee.dto.DiceRoll;
import com.example.androidyahtzee.dto.DieState;
import com.example.androidyahtzee.dto.GameState;
import com.example.androidyahtzee.dto.ScoreChart;

public class MainActivity extends AppCompatActivity implements
        PregameFragment.OnFragmentInteractionListener,
        IngameFragment.OnFragmentInteractionListener,
        DiceFragment.OnFragmentInteractionListener,
        ScoreChartFragment.OnFragmentInteractionListener
{

    private PregameFragment pregameFragment;
    private IngameFragment ingameFragment;

    private AndroidYahtzee game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            pregameFragment = PregameFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, pregameFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //PregameFragment interaction
    @Override
    public void onStartGame(String[] playerNames, int numPlayers) {
        game = new AndroidYahtzee();
        for(String name: playerNames){
            game.addPlayer(name);
        }

        ingameFragment = IngameFragment.newInstance(playerNames, numPlayers);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(pregameFragment);
        transaction.add(R.id.fragment_container, ingameFragment);
        transaction.commit();
        game.startGame();
    }

    //IngameFragment interaction
    @Override
    public DiceRoll onRollDice() {
        return game.rollDice();
    }

    //DiceFragment interaction
    @Override
    public DieState onDiceClicked(int index) {
        return game.holdDie(index);
    }

    @Override
    public void onScoreClicked(String scoreType) {
        GameState state = game.saveScore(scoreType);
        if (state != null) {
            ScoreChart chart = state.getScoreCharts().get(0);
            ScoreChartFragment scf = ingameFragment.getScoreChartFragment(chart.getPlayer());
            for(ChartEntry entry : chart.getEntries()){
                scf.addScoreToChart(entry.getType(), entry.getValue());
            }
            if(state.isActive()){
                ingameFragment.swapScoreChart(chart.getPlayer(), state.getCurrentPlayer());
                ingameFragment.setDiceRoll(state.getDiceRoll());
            }else{
                //TODO game has ended. Display game results
            }
        }
    }
}
