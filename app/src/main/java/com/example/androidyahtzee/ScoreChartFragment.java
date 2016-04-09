package com.example.androidyahtzee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoreChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoreChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreChartFragment extends Fragment {
    private static final String ARG_PLAYER_NAME = "playerName";

    private String mPlayerName;

    private OnFragmentInteractionListener mListener;
    private Map<Integer, String> mScoreEntryMap;
    private Map<String, TableRow> mRowMap;

    public ScoreChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param playerName PlayerName.
     * @return A new instance of fragment ScoreChartFragment.
     */
    public static ScoreChartFragment newInstance(String playerName) {
        ScoreChartFragment fragment = new ScoreChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYER_NAME, playerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayerName = getArguments().getString(ARG_PLAYER_NAME);
        }
        mScoreEntryMap = new HashMap<>();
        mRowMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_chart, container, false);
        ((TextView)view.findViewById(R.id.playerNameField)).setText(mPlayerName);
        invokeListeners(view);
        return view;
    }

    private void invokeListeners(View view){
        String[] entries = getResources().getStringArray(R.array.score_identifiers);
        for(String entry: entries) {
            int resID = getResources().getIdentifier(entry, "id", "com.example.androidyahtzee");
            TableRow tr = (TableRow)view.findViewById(resID);
            mRowMap.put(entry, tr);
            if(!entry.equals("BONUS")) {
                tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRowClicked(v);
                    }
                });
                mScoreEntryMap.put(tr.getId(), entry);
            }
        }
    }

    public void onRowClicked(View v) {
        if (mListener != null) {
            String scoreType = mScoreEntryMap.get(v.getId());
            mListener.onScoreClicked(scoreType);
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
        void onScoreClicked(String scoreType);
    }

    public void addScoreToChart(String scoreType, int value){
        TableRow tr = mRowMap.get(scoreType);
        final TextView tv = (TextView)tr.getChildAt(1);
        tv.setText(value);
        tr.setClickable(false);
        Toast.makeText(getActivity(), R.string.score_added, Toast.LENGTH_SHORT).show();
    }
}
