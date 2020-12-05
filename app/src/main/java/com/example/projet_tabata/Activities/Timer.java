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
import android.widget.TextView;

import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Timer extends AppCompatActivity implements Serializable {

    Bundle cycle;
    Seance seance;
    // VIEW
    private TextView etapeTimerView;
    private TextView globalTimerView;
    private TextView descriptif;
    private TextView descriptif2;
    private TextView onStart;
    private int started = 2;

    // DATA
    private long etapeTime;
    private CountDownTimer timer;
    private long globalTime;
    private CountDownTimer globalTimer;
    public int etape = 0;
    ArrayList<String> seanceCycle;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Récupérer les view
        etapeTimerView = findViewById(R.id.timerView);
        globalTimerView = findViewById(R.id.tempsTotal);
        descriptif = findViewById(R.id.Descriptif);
        descriptif2 = findViewById(R.id.Descriptif2);
        onStart = findViewById(R.id.onStart);
        seance = (Seance) getIntent().getSerializableExtra("Seance");
        Log.i("TAG", seance.name);

        seanceCycle = seance.getSeanceCycle();

        etapeTime = seance.preparation * 1000;
        globalTime = seance.getTempsTotal() * 1000;

        onStart.setBackgroundResource(R.drawable.play);
        etapeTimerView.setBackgroundResource(R.drawable.dot);
        globalTimerView.setBackgroundResource(R.drawable.dot);


        if (cycle != null) {
            etapeTime = cycle.getLong("etapeTime");
            globalTime = cycle.getLong("globalTime");
            if (etape > 0){
                etape = cycle.getInt("etape")-1;
            }
            next();
        }
        //timerView.setBackgroundResource(R.drawable.play);


        updateEtapeTime();
        updateGlobalTime();
    }

    // Mise à jour graphique
    private void updateEtapeTime() {

        // Décompositions en secondes et minutes
        int secs = (int) (etapeTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (etapeTime % 1000);

        // Affichage du "timer"
        etapeTimerView.setText("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));

    }

    private void updateGlobalTime() {
        // Décompositions en secondes et minutes
        int secs = (int) (globalTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;

        // Affichage du "timer"
        globalTimerView.setText("" + mins + ":"
                + String.format("%02d", secs));

    }

    public void onBegin(View view) {
        if (started == 2) {
            onStart.setBackgroundResource(R.drawable.pause);
            startGlobalTimer();
            next();
        } else if (started == 1) {
            this.Start();
        } else {

            this.Pause();
        }
    }

    // Mettre en pause le compteur
    public void Pause() {
        if (timer != null) {
            onStart.setBackgroundResource(R.drawable.play);

            started = 1;
            timer.cancel(); // Arrete le CountDownTimer
        }
        if (globalTimer != null){
            globalTimer.cancel();
        }

    }

    public void Start() {
        started = 0;
        onStart.setBackgroundResource(R.drawable.pause);

        timer = new CountDownTimer(etapeTime, 10) {

            public void onTick(long millisUntilFinished) {
                etapeTime = millisUntilFinished;
                updateEtapeTime();
            }

            public void onFinish() {
                etapeTime = 0;
                updateEtapeTime();
                next();
            }
        }.start();

        globalTimer = new CountDownTimer(globalTime, 10) {

            public void onTick(long millisUntilFinished) {
                globalTime = millisUntilFinished;
                updateGlobalTime();
            }

            public void onFinish() {
                globalTime = 0;
                updateGlobalTime();

            }
        }.start();
    }


    public void next() {
        Log.i("etape", String.valueOf(etape));
        started = 0;

        if (etape < seanceCycle.size()) {
            if (seanceCycle.get(etape) == "Preparation") {
                descriptif.setText(seanceCycle.get(etape));
                descriptif2.setText(seanceCycle.get(etape + 1));
                play();
                getWindow().getDecorView().setBackgroundColor(Color.rgb(232, 117, 117));
                startTimer(seance.preparation * 1000);

            }
            if (seanceCycle.get(etape) == "Travail") {
                descriptif.setText("Travail");
                descriptif2.setText(seanceCycle.get(etape + 1));
                play();
                getWindow().getDecorView().setBackgroundColor(Color.rgb(139, 231, 127));
                startTimer(seance.travail * 1000);
            }
            if (seanceCycle.get(etape) == "Repos") {
                descriptif.setText("Repos");
                descriptif2.setText(seanceCycle.get(etape + 1));
                play();
                getWindow().getDecorView().setBackgroundColor(Color.rgb(232, 117, 117));
                startTimer(seance.repos * 1000);
            }
            if (seanceCycle.get(etape) == "Repos Sequence") {
                descriptif.setText("Repos Sequence");
                if (etape + 1 == seanceCycle.size()) {
                    descriptif2.setText("FIN");
                } else {
                    descriptif2.setText(seanceCycle.get(etape + 1));
                }
                play();
                getWindow().getDecorView().setBackgroundColor(Color.rgb(232, 117, 117));
                startTimer(seance.reposLong * 1000);
            }
            etape++;
        } else {
            Intent intent = new Intent(this, Fin.class);
            startActivity(intent);
            finish();
        }

    }

    public void startGlobalTimer() {

        globalTimer = new CountDownTimer(globalTime, 10) {

            public void onTick(long millisUntilFinished) {
                globalTime = millisUntilFinished;
                updateGlobalTime();
            }

            public void onFinish() {
                globalTime = 0;
                updateGlobalTime();

            }
        }.start();


    }


    private void startTimer(int time) {

        timer = new CountDownTimer(time, 10) {

            public void onTick(long millisUntilFinished) {
                etapeTime = millisUntilFinished;
                updateEtapeTime();
            }

            public void onFinish() {
                etapeTime = 0;
                updateEtapeTime();
                next();

            }
        }.start();
    }


    public void play() {
        player = null;
        player = MediaPlayer.create(this, R.raw.bell);
        player.start();
    }

    @Override
    public void onSaveInstanceState(Bundle cycle) {
        super.onSaveInstanceState(cycle);
        Pause();
        cycle.putLong("globalTime", globalTime);
        cycle.putLong("etapeTime", etapeTime);
        cycle.putInt("etape", etape);
    }

    /*@Override
    public void onRestoreInstanceState(Bundle cycle) {
        super.onSaveInstanceState(cycle);
        etapeTime = cycle.getLong("etapeTime");
        globalTime = cycle.getLong("globalTime");
        if (etape > 0){
            etape = cycle.getInt("etape")-1;
        }
        updateEtapeTime();
        updateGlobalTime();
        next();

    }*/



    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void setColor(String color) {
        if (color == "red") {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_timer);
    }
}