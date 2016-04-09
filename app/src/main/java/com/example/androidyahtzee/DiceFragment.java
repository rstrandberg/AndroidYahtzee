package com.example.androidyahtzee;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.androidyahtzee.dto.DieState;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Map<Integer, ImageButton> mDiceMap;

    public DiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiceFragment newInstance() {
        DiceFragment fragment = new DiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDiceMap = new HashMap<Integer, ImageButton>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice, container, false);
        for(int i = 0; i < 5; i++){
            int resID = getResources().getIdentifier("die"+i, "id", "com.example.androidyahtzee");
            ImageButton button = (ImageButton)view.findViewById(resID);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(v);
                }
            });
            mDiceMap.put(i, button);
        }

        return view;
    }

    /*
     * Handles click events from the dice buttons
     */
    public void onButtonPressed(View v) {
        if (mListener != null) {
            int index = -1;
            switch(v.getId()){
                case R.id.die0:
                    index = 0;
                    break;
                case R.id.die1:
                    index = 1;
                    break;
                case R.id.die2:
                    index = 2;
                    break;
                case R.id.die3:
                    index = 3;
                    break;
                case R.id.die4:
                    index = 4;
                    break;
            }
            updateDieImage(index, mListener.onDiceClicked(index));
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
     * Interface for cross Activity/Fragment communication
     */
    public interface OnFragmentInteractionListener {
        DieState onDiceClicked(int index);
    }

    public void updateDieImage(int index, DieState dieState){
        int value = dieState.getValue();
        if(value < 1 || value > 6){
            mDiceMap.get(index).setImageResource(R.drawable.unknown);
        }else {
            String resourceName = (dieState.isHeld()) ? "held" : "die";
            resourceName += value;
            mDiceMap.get(index).setImageResource(getResources().getIdentifier(resourceName, "drawable", "com.example.androidyahtzee"));
        }
    }
}
