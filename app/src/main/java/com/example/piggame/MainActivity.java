package com.example.piggame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.ParcelField;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TextView.OnEditorActionListener;


import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity
implements OnEditorActionListener {
    PigGame game = new PigGame();

    private EditText player1NameEditText;
    private EditText player2NameEditText;
    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;
    private TextView playerTurnLabelTextView;
    private ImageView dieImageView;
    private TextView turnPointsTextView;
    private Button rollDieButton;
    private Button endTurnButton;
    private Button newGameButton;

    private SharedPreferences savedValues;

    final int[] imgIds = new int[]{
            R.drawable.die1,R.drawable.die1,R.drawable.die2,
            R.drawable.die3,R.drawable.die4,
            R.drawable.die5,R.drawable.die6,
            R.drawable.die7,R.drawable.die8,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1NameEditText = (EditText) findViewById(R.id.player1NameEditText);
        player2NameEditText = (EditText) findViewById(R.id.player2NameEditText);
        player1ScoreTextView = (TextView) findViewById(R.id.player1Score);
        player2ScoreTextView = (TextView) findViewById(R.id.player2Score);
        playerTurnLabelTextView = (TextView) findViewById(R.id.playerTurnLabel);
        dieImageView = (ImageView) findViewById(R.id.dieImage);
        turnPointsTextView = (TextView) findViewById(R.id.turnPoints);
        rollDieButton = (Button) findViewById(R.id.rollDieButton);
        endTurnButton = (Button) findViewById(R.id.endTurnButton);
        newGameButton = (Button) findViewById(R.id.newGameButton);

        player1NameEditText.setOnEditorActionListener(this);
        player2NameEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        rollDieButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                rollDieClick();
            }
        });
        endTurnButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                endTurnClick();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                newGameClick();
            }
        });

        game.PigGame();
        newGame();
    }
    @Override
    public void onPause()
    {
        Editor editor = savedValues.edit();
        editor.putString("player1Name",player1NameEditText.toString());
        editor.putString("player2Name",player2NameEditText.toString());
        editor.putString("player1Score",player1ScoreTextView.toString());
        editor.putString("player2Score",player2ScoreTextView.toString());
        editor.putString("turnPoints",turnPointsTextView.toString());
        editor.putString("playerTurnLabel",playerTurnLabelTextView.toString());
        editor.commit();
        super.onPause();
    }
    @Override
    public void onResume()
    {
        super.onResume();

    }
    public void newGame()
    {
        //clear views
        player1NameEditText.setText("");
        player2NameEditText.setText("");
        playerTurnLabelTextView.setText("");
        dieImageView.setImageResource(R.drawable.pig_background);
        turnPointsTextView.setText("");
        player1ScoreTextView.setText("");
        player2ScoreTextView.setText("");
        //
    }
    public void rollDieClick()
    {
        int dieNumber = game.rollDie();
        dieImageView.setImageResource(imgIds[dieNumber]);
        turnPointsTextView.setText(Integer.toString(game.getTurnPoints()));
        if(dieNumber == 8) {
            rollDieButton.setEnabled(false);

        }
    }
    public void endTurnClick()
    {
        //Assign turn points to player
        //update UI
        //switch turn
        int turn = game.getTurn();

        if(rollDieButton.isEnabled() == false)
        {
            rollDieButton.setEnabled(true);
        }
        turnPointsTextView.setText("");

        game.changeTurn();

        if(turn % 2 == 1)
        {
            playerTurnLabelTextView.setText(String.format("%s's turn", game.getPlayer1Name()));
            player1ScoreTextView.setText(Integer.toString(game.getPlayer1Score()));
        }
        else {
            playerTurnLabelTextView.setText(String.format("%s's turn", game.getPlayer2Name()));
            player2ScoreTextView.setText(Integer.toString(game.getPlayer2Score()));
        }
        if(game.checkForWinner() == "Tie" || game.checkForWinner() == String.format("%s wins!", game.getPlayer2Name())
                || game.checkForWinner() == String.format("%s wins!", game.getPlayer1Name()) )
        {
            playerTurnLabelTextView.setText(game.checkForWinner());
        }

    }
    public void newGameClick()
    {
        game.resetGame();
        newGame();
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        game.setPlayer1Name(player1NameEditText.getText().toString());
        game.setPlayer2Name(player2NameEditText.getText().toString());

        playerTurnLabelTextView.setText(game.getPlayer1Name() + "'s turn");


        return false;
    }
}
