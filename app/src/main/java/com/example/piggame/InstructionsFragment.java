package com.example.piggame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.fragment.app.Fragment;

public class InstructionsFragment extends Fragment
    implements OnEditorActionListener
{
    PigGame game = new PigGame();

    //define variables for widgets
    private EditText player1NameEditText;
    private EditText player2NameEditText;

    // define instance variables that should be saved
    private String player1Name;
    private String player2Name;

    private SharedPreferences savedValues;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);


        player1NameEditText = (EditText) view.findViewById(R.id.player1NameEditText);
        player2NameEditText = (EditText) view.findViewById(R.id.player2NameEditText);

        player1NameEditText.setOnEditorActionListener(this);
        player2NameEditText.setOnEditorActionListener(this);
        // return the View for the layout
        return view;
    }
    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("player1Name",player1NameEditText.toString());
        editor.putString("player2Name",player2NameEditText.toString());
        editor.commit();
        super.onPause();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        game.setPlayer1Name(player1NameEditText.getText().toString());
        game.setPlayer2Name(player2NameEditText.getText().toString());

       //playerTurnLabelTextView.setText(game.getPlayer1Name() + "'s turn");


        return false;
    }
}
