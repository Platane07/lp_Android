package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    Bundle cycle;
    Seance seance;
    // VIEW
    private TextView timerView;
    private TextView descriptif;
    private TextView descriptif2;
    private Button onStart;
    private int started = 2;

    // DATA
    private long updatedTime = INITIAL_TIME;
    private CountDownTimer timer;
    public int etape = 0;
    ArrayList<String> seanceCycle;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);

        // Récupérer les view
        timerView = findViewById(R.id.textView8);
        descriptif = findViewById(R.id.Descriptif);
        descriptif2 = findViewById(R.id.Descriptif2);
        onStart = findViewById(R.id.onStart);
        seance = (Seance) getIntent().getSerializableExtra("Seance");
        Log.i("TAG", seance.name);

        seanceCycle = seance.getSeanceCycle();

        updatedTime = seance.preparation*1000;


        if (cycle != null){
            updatedTime = cycle.getLong("updatedTime");
            etape = cycle.getInt("etape")-1;
            next();
        }

        miseAJour();
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

    public void onBegin(View view) {
        if (started == 2){
            next();
        }
        else if (started == 1){
            this.Start();
        }
        else {
            this.Pause();
        }
    }

    // Mettre en pause le compteur
    public void Pause() {
        if (timer != null) {
            onStart.setText("Start");
            started = 1;
            timer.cancel(); // Arrete le CountDownTimer
        }

    }
    public void Start(){
        onStart.setText("Pause");
        started = 0;
        timer = new CountDownTimer(updatedTime, 10) {

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



    public void next() {
        Log.i("etape", String.valueOf(etape));
        started = 0;

        if (etape < seanceCycle.size()) {
            if (seanceCycle.get(etape) == "Preparation") {
                descriptif.setText(seanceCycle.get(etape));
                descriptif2.setText(seanceCycle.get(etape+1));
                play();
                getWindow().getDecorView().setBackgroundColor(Color.RED);
                startTimer(seance.preparation * 1000);

            }
            if (seanceCycle.get(etape) == "Travail") {
                descriptif.setText("Travail");
                play();
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                startTimer(seance.travail * 1000);
            }
            if (seanceCycle.get(etape) == "Repos") {
                descriptif.setText("Repos");
                play();
                getWindow().getDecorView().setBackgroundColor(Color.RED);
                startTimer(seance.repos * 1000);
            }
            if (seanceCycle.get(etape) == "Repos Sequence") {
                descriptif.setText("Repos Sequence");
                play();
                getWindow().getDecorView().setBackgroundColor(Color.RED);
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


    public void play(){
        player = null;
        player = MediaPlayer.create(this, R.raw.bell);
        player.start();
    }

    public void onSaveInstanceState(Bundle cycle) {
        super.onSaveInstanceState(cycle);
        Pause();
        cycle.putLong("updatedTime", updatedTime);
        cycle.putInt("etape", etape);
    }
/*

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onSaveInstanceState(cycle);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            onSaveInstanceState(cycle);
        }
    }



*/





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