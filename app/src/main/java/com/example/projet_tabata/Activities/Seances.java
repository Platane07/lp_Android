package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Seances extends AppCompatActivity implements Serializable {
    private DatabaseClient mDb;

    private final static long INITIAL_TIME = 5000;

    Seance seance;
    // VIEW
    private TextView timerView;

    // DATA
    private long updatedTime = INITIAL_TIME;
    private CountDownTimer timer;
    public int etape = 0;
    ArrayList<String> seanceCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);

        // Récupérer les view
        timerView = (TextView) findViewById(R.id.textView8);
        seance = (Seance) getIntent().getSerializableExtra("Seance");
        Log.i("TAG", seance.name);

        seanceCycle = seance.getSeanceCycle();

        updatedTime = seance.preparation;

        miseAJour();
        next();
    }

    // Mise à jour graphique
    private void miseAJour() {

        // Décompositions en secondes et minutes
        int secs = (int) (updatedTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (updatedTime % 1000);

        // Affichage du "timer"
        timerView.setText("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));

    }

    public void onStart(View view) {
        next();
    }



    public void next() {

        if (etape < seanceCycle.size()) {
            if (seanceCycle.get(etape) == "Preparation") {
                startTimer(seance.preparation * 1000);
            }
            if (seanceCycle.get(etape) == "Travail") {
                startTimer(seance.travail * 1000);
            }
            if (seanceCycle.get(etape) == "Repos") {
                startTimer(seance.repos * 1000);
            }
            if (seanceCycle.get(etape) == "Repos Sequence") {
                startTimer(seance.reposSeq * 1000);
            }
            etape++;
        } else {
            Intent intent = new Intent (this, Fin.class);
            startActivity(intent);
        }

    }


    private void startTimer(int time) {

        timer = new CountDownTimer(time, 10) {

            public void onTick(long millisUntilFinished) {
                updatedTime = millisUntilFinished;
                miseAJour();
            }

            public void onFinish() {
                updatedTime = 0;
                miseAJour();
                next();

            }
        }.start();
    }


    // Mettre en pause le compteur
    public void onPause(View view) {
        if (timer != null) {
            timer.cancel(); // Arrete le CountDownTimer
        }
    }

    // Remettre à le compteur à la valeur initiale
    public void onReset(View view) {

        // Mettre en pause
        if (timer != null) {
            timer.cancel();
        }

        // Réinitialiser
        updatedTime = INITIAL_TIME;

        // Mise à jour graphique
        miseAJour();
    }

    public HashMap createInitialTable(Seance seance) {
        HashMap<String, Integer> mapSeance = new HashMap<String, Integer>();

        mapSeance.put("Préparation", seance.preparation);

        for (int i = 0; i < seance.sequence; i++) {
            for (int j = 0; j < seance.cycle; j++) {
                mapSeance.put("Travail", seance.travail);
                mapSeance.put("Repos", seance.repos);
            }
            mapSeance.put("Repos Long", seance.reposSeq);
        }
        return mapSeance;

    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);
    }

        mDb = DatabaseClient.getInstance(getApplicationContext());

        Seance seance = (Seance) getIntent().getSerializableExtra("Seance");
        Log.i("TAG", seance.name);

        Toast.makeText(getApplicationContext() ,seance.name, Toast.LENGTH_SHORT).show();


*/
}