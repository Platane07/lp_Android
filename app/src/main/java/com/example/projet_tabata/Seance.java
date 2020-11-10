package com.example.projet_tabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.projet_tabata.fragments.SeanceCreation;

public class Seance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Intent intent = getIntent();

        String seance = intent.getStringExtra("seance");

        if (seance.equals("nouveau")){
            fragmentTransaction.replace(R.id.bottom_fragment, new SeanceCreation());
            fragmentTransaction.commit();

        }
    }
}