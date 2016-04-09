package com.example.androidyahtzee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidyahtzee.dto.DiceRoll;
import com.example.androidyahtzee.dto.DieState;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngameFragment extends Fragment {
    private static final String ARG_PLAYERS = "playerNames";
    private static final String ARG_NUM_PLAYERS = "numPlayers";

    private String[] mPlayerNames;
    private int mNumPlayers;

    private OnFragmentInteractionListener mListener;
    private Map<String, ScoreChartFragment> mScoreFragments;
    private Button mButtonRoll;
    private DiceFragment mDiceFragment;

    public IngameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param playerNames Player names.
     * @param numPlayers Number of players
     * @return A new instance of fragment IngameFragment.
     */
    public static IngameFragment newInstance(String[] playerNames, int numPlayers) {
        IngameFragment fragment = new IngameFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PLAYERS, playerNames);
        args.putInt(ARG_NUM_PLAYERS, numPlayers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayerNames = getArguments().getStringArray(ARG_PLAYERS);
            mNumPlayers = getArguments().getInt(ARG_NUM_PLAYERS);
        }
        mScoreFragments = new HashMap<>();
        for(int i = 0; i<mNumPlayers; i++){
            mScoreFragments.put(mPlayerNames[i], ScoreChartFragment.newInstance(mPlayerNames[i]));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingame, container, false);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for(int i = 0; i < mPlayerNames.length; i++) {
            transaction.add(R.id.score_container, mScoreFragments.get(mPlayerNames[i]));
            if (i == 0) {
                transaction.show(mScoreFragments.get(mPlayerNames[i]));
            } else{
                transaction.hide(mScoreFragments.get(mPlayerNames[i]));
            }

        }
        transaction.commit();
        mButtonRoll = (Button)view.findViewById(R.id.buttonRoll);
        mButtonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(v);
            }
        });
        mDiceFragment = (DiceFragment)getChildFragmentManager().findFragmentById(R.id.dice_fragment);
        return view;
    }

    public void onButtonPressed(View v) {
        if (mListener != null) {
            DiceRoll diceRoll = mListener.onRollDice();
            mButtonRoll.setText(R.string.roll + diceRoll.getRollsLeft());
            if(diceRoll.getRollsLeft() <= 0){
                mButtonRoll.setEnabled(false);
            }

            int index = 0;
            for(DieState dieState: diceRoll.getDice()){
                mDiceFragment.updateDieImage(index++, dieState);
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        DiceRoll onRollDice();
    }

    public ScoreChartFragment getScoreChartFragment(String playerName){
        return mScoreFragments.get(playerName);
    }
    public void setDiceRoll(DiceRoll diceRoll){
        int index = 0;
        for(DieState dieState : diceRoll.getDice()){
            mDiceFragment.updateDieImage(index++, dieState);
        }
        if(diceRoll.getRollsLeft() > 0){
            mButtonRoll.setText("Roll Dice " + diceRoll.getRollsLeft());
            mButtonRoll.setEnabled(true);
        }
    }
    public void showScoreChart(String playerName){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.show(mScoreFragments.get(playerName));
        transaction.commit();
    }
    public void swapScoreChart(String fromPlayerName, String toPlayerName){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(mScoreFragments.get(fromPlayerName));
        transaction.show(mScoreFragments.get(toPlayerName));
        transaction.commit();
    }
}
