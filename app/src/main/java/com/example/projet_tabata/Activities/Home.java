package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projet_tabata.Activities.SeanceCreation;
import com.example.projet_tabata.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }




    public void creer (View v){
        Intent intent = new Intent(this, SeanceCreation.class);
        startActivity(intent);
    }
}