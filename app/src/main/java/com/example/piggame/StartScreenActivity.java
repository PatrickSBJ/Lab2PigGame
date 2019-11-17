package com.example.piggame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


public class StartScreenActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
       // getSupportFragmentManager().beginTransaction()
        //        .replace(android.R.id.content, new InstructionsFragment())
             //   .commit();

    }
}
