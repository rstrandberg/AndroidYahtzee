package com.example.androidyahtzee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PregameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PregameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PregameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText numPlayerSelector;
    private EditText player1;
    private EditText player2;
    private EditText player3;
    private EditText player4;

    public PregameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PregameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PregameFragment newInstance() {
        PregameFragment fragment = new PregameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pregame, container, false);

        final Button buttonStart = (Button) view.findViewById(R.id.buttonStart);
        numPlayerSelector = (EditText) view.findViewById(R.id.numPlayerSelector);
        player1 = (EditText)view.findViewById(R.id.player1Name);
        player2 = (EditText)view.findViewById(R.id.player2Name);
        player3 = (EditText)view.findViewById(R.id.player3Name);
        player4 = (EditText)view.findViewById(R.id.player4Name);
        buttonStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onButtonPressed(v);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View v) {
        if (mListener != null) {
            int numPlayers = Integer.parseInt(numPlayerSelector.getText().toString());

            String[] players = new String[numPlayers];
            switch(numPlayers){
                case 4:
                    players[3] = player4.getText().toString();
                case 3:
                    players[2] = player3.getText().toString();
                case 2:
                    players[1] = player2.getText().toString();
                case 1:
                    players[0] = player1.getText().toString();
                    break;
                default:
                    numPlayerSelector.setError("Incorrect selection");
                    return;
            }
            mListener.onStartGame(players, numPlayers);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStartGame(String[] playerNames, int numPlayers);
    }
}
