package com.example.piggame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.Menu;
import android.os.Bundle;

import android.widget.Toast;


import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity
 {
    PigGame game = new PigGame();

    private static final String PIG_GAME="PigGameActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //display the fragment within the activity
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GameplayFragment())
                .commit();

        //get suppport action bar
        ActionBar ab = getSupportActionBar();
        //enable up button
        ab.setDisplayHomeAsUpEnabled(true);
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
}
