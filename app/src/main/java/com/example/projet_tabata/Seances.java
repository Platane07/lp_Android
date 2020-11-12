package com.example.projet_tabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.projet_tabata.Fragments.SeanceCreation;
import com.example.projet_tabata.Fragments.Timer;

public class Seances extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        FragmentTransaction  addForm = getSupportFragmentManager().beginTransaction();
        FragmentTransaction  addTimer = getSupportFragmentManager().beginTransaction();

        Intent intent = getIntent();

        String seance = intent.getStringExtra("seance");

        addTimer.replace(R.id.top_fragment, new Timer());
        addTimer.commit();

        if (seance.equals("nouveau")){
            addForm.replace(R.id.bottom_fragment, new SeanceCreation());
            addForm.commit();
        }
    }
}