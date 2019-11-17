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
import android.content.Intent;


public class InstructionsFragment extends Fragment
    implements OnEditorActionListener, View.OnClickListener
{

    //define variables for widgets
    private EditText player1NameEditText;
    private EditText player2NameEditText;
    private Button newGameButton;

    // define instance variables that should be saved
    private String player1Name;
    private String player2Name;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);


        player1NameEditText = view.findViewById(R.id.player1NameEditText);
        player2NameEditText = view.findViewById(R.id.player2NameEditText);
        newGameButton =  view.findViewById(R.id.newGameButton);



        player1NameEditText.setOnEditorActionListener(this);
        player2NameEditText.setOnEditorActionListener(this);
        newGameButton.setOnClickListener(this);
        // return the View for the layout
        return view;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        player1Name = player1NameEditText.getText().toString();
        player2Name = player2NameEditText.getText().toString();

        return false;
    }
    @Override
    public void onClick(View v)
    {
        //Create a new intent to start the main activity
        Intent intent = new Intent(getActivity(), MainActivity.class);
        // put the data to be passed to the next activity in the intent extras
        intent.putExtra("PLAYER_1_NAME", player1Name);
        intent.putExtra("PLAYER_2_NAME", player2Name);
        //Start new activity
        startActivity(intent);
    }
}
