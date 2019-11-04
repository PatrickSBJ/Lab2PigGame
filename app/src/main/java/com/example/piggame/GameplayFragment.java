package com.example.piggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

public class GameplayFragment extends Fragment
{

    private static final String PIG_GAME="PigGameActivity";


    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;
    private TextView playerTurnLabelTextView;
    private TextView winningMessage;
    private ImageView dieImageView;
    private ImageView dieImageView2;
    private TextView turnPointsTextView;
    private Button rollDieButton;
    private Button endTurnButton;
    int dieNumber1;
    int dieNumber2;
    //Defined instance variables for settings preferences
    private int numberOfDie;

    private SharedPreferences savedValues;
    private SharedPreferences prefs;


    final int[] IMG_IDS = new int[]{
            R.drawable.die1,R.drawable.die1,R.drawable.die2,
            R.drawable.die3,R.drawable.die4,
            R.drawable.die5,R.drawable.die6,
            R.drawable.die7,R.drawable.die8,

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gameplay, container, false);

        player1ScoreTextView = (TextView) view.findViewById(R.id.player1Score);
        player2ScoreTextView = (TextView) view.findViewById(R.id.player2Score);
        playerTurnLabelTextView = (TextView) view.findViewById(R.id.playerTurnLabel);
        winningMessage = (TextView) view.findViewById(R.id.winningMessage);
        dieImageView = (ImageView) view.findViewById(R.id.dieImage);
        dieImageView2 = (ImageView) view.findViewById(R.id.secondDieImageView);
        turnPointsTextView = (TextView) view.findViewById(R.id.turnPoints);
        rollDieButton = (Button) view.findViewById(R.id.rollDieButton);
        endTurnButton = (Button) view.findViewById(R.id.endTurnButton);



        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        PreferenceManager.setDefaultValues(this,R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        rollDieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollDieClick();
            }
        });
        endTurnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endTurnClick();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newGameClick();
            }
        });

        game.PigGame();
        newGame();
        // return the View for the layout
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_pig_game, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onPause()
    {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("player1Score",player1ScoreTextView.toString());
        editor.putString("player2Score",player2ScoreTextView.toString());
        editor.putString("turnPoints",turnPointsTextView.toString());
        editor.putString("playerTurnLabel",playerTurnLabelTextView.toString());
        editor.putInt("dieNumber1",dieNumber1);
        editor.putInt("dieNumber2",dieNumber2);
        editor.commit();
        super.onPause();
    }
    @Override
    public void onResume()
    {
        super.onResume();

        game.setPlayer1Name(savedValues.getString("player1Name",""));
        game.setPlayer2Name(savedValues.getString("player2Name",""));

        dieNumber1 = savedValues.getInt("dieNumber1",dieNumber1);
        dieNumber2 = savedValues.getInt("dieNumber2",dieNumber2);


        dieImageView.setImageResource(IMG_IDS[dieNumber1]);
        dieImageView.setImageResource(IMG_IDS[dieNumber2]);

        //Usable preferences
        game.setNumberOfDie(Integer.parseInt(prefs.getString("pref_number_of_die", "1")));
        game.setWinningScore(Integer.parseInt(prefs.getString("pref_winning_score", "100")));
        game.badNumber = Integer.parseInt(prefs.getString("pref_bad_number", "8"));
    }
    public void newGame()
    {
        //clear views
        //player1NameEditText.setText("");
        //player2NameEditText.setText("");
        playerTurnLabelTextView.setText("");
        dieImageView.setImageResource(android.R.color.transparent);
        dieImageView2.setImageResource(android.R.color.transparent);
        turnPointsTextView.setText("");
        player1ScoreTextView.setText("");
        player2ScoreTextView.setText("");
        winningMessage.setText("");
        //
    }
    public void rollDieClick()
    {
        if(game.getNumberOfDie() == 1)
        {
            dieNumber1 = game.rollDie();
            dieImageView.setImageResource(IMG_IDS[dieNumber1]);
        }
        else
        {
            dieNumber1 = game.rollDie();
            dieImageView.setImageResource(IMG_IDS[dieNumber1]);

            dieNumber2 = game.rollDie();
            dieImageView2.setImageResource(IMG_IDS[dieNumber2]);
        }


        turnPointsTextView.setText(Integer.toString(game.getTurnPoints()));
        if(dieNumber1 == game.badNumber || dieNumber2 == game.badNumber) {
            rollDieButton.setEnabled(false);
        }
    }
    public void endTurnClick()
    {
        //Assign turn points to player
        //update UI
        //switch turn
        int turn = game.getTurn();

        game.changeTurn();

        turnPointsTextView.setText("");
        dieImageView.setImageResource(android.R.color.transparent);
        dieImageView2.setImageResource(android.R.color.transparent);

        if(rollDieButton.isEnabled() == false)
        {
            rollDieButton.setEnabled(true);
        }
        Log.d(PIG_GAME, Integer.toString(turn));
        if(turn == 1)
        {
            playerTurnLabelTextView.setText(String.format("%s's turn", game.getPlayer2Name()));
            player1ScoreTextView.setText(Integer.toString(game.getPlayer1Score()));
        }
        else {
            playerTurnLabelTextView.setText(String.format("%s's turn", game.getPlayer1Name()));
            player2ScoreTextView.setText(Integer.toString(game.getPlayer2Score()));

        }
       /* if(game.checkForWinner() == "Tie" || game.checkForWinner() == String.format("%s wins!", game.getPlayer2Name())
                || game.checkForWinner() == String.format("%s wins!", game.getPlayer1Name()) )
        {
            playerTurnLabelTextView.setText(game.checkForWinner());
        } */

        winningMessage.setText(game.checkForWinner());
    }
    public void newGameClick()
    {
        game.resetGame();
        newGame();
    }
}
