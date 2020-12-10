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

import static java.lang.Long.valueOf;

public class Timer extends AppCompatActivity implements Serializable {

    Seance seance;
    // VIEW
    TextView etapeTimerView;
    TextView globalTimerView;
    TextView descriptif;
    TextView descriptif2;
    TextView onStart;

    //Entité à trois états : 0,1,2
    //2 correspond au timer qui n'a pas commencé
    //1 correspond au timer en état de marche
    //0 correspond au timer en pause
    int started = 2;

    // DATA
    long etapeTime;
    CountDownTimer timer;
    long globalTime;
    CountDownTimer globalTimer;
    int etape = 0;
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

        etapeTimerView.setBackgroundResource(R.drawable.dot);
        globalTimerView.setBackgroundResource(R.drawable.dot);


        if (savedInstanceState != null) {
            etapeTime = savedInstanceState.getLong("etapeTime");
            globalTime = savedInstanceState.getLong("globalTime");
            started = savedInstanceState.getInt("started");
            if (etape > 0){
                etape = savedInstanceState.getInt("etape")-1;
            }
            if (started == 0) {
                onStart.setBackgroundResource(R.drawable.pause);
                startGlobalTimer(globalTime);
                next();
            }
        }



        updateEtapeTime();
        updateGlobalTime();
    }

    // Mise à jour graphique du timer pour chaque étape
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

    //Mise à jour graphique du timer global
    private void updateGlobalTime() {
        // Décompositions en secondes et minutes
        int secs = (int) (globalTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;

        // Affichage du "timer"
        globalTimerView.setText("" + mins + ":"
                + String.format("%02d", secs));

    }

    //fonction qui s'effectue lorsqu'on appuie sur le bouton start/pause
    public void BoutonStartPause(View view) {
        if (started == 2) {
            onStart.setBackgroundResource(R.drawable.pause);
            startGlobalTimer(globalTime);
            next();
        } else if (started == 1) {
            this.Start();
        } else {

            this.Pause();
        }
    }

    // Mettre en pause les compteurs
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

    //Mettre en marche les compteurs
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


    //fonction qui est appelé lorsque le timer des étapes arrive à zéro pour changer d'étape
    // On parcourt une liste pour connaitre à quel état on se situe et une condition est effectué pour modifier les éléments de l'activité
    public void next() {
        Log.i("etape", String.valueOf(etape));
        started = 0;

        if (etape < seanceCycle.size()) {
            if (seanceCycle.get(etape) == "Preparation") {
                descriptif.setText(seanceCycle.get(etape));
                descriptif2.setText(seanceCycle.get(etape + 1));
                getWindow().getDecorView().setBackgroundColor(Color.rgb(232, 117, 117));
                ifBundleExist(seance.preparation);
            }
            if (seanceCycle.get(etape) == "Travail") {
                descriptif.setText("Travail");
                descriptif2.setText(seanceCycle.get(etape + 1));
                getWindow().getDecorView().setBackgroundColor(Color.rgb(139, 231, 127));
                ifBundleExist(seance.travail);
            }
            if (seanceCycle.get(etape) == "Repos") {
                descriptif.setText("Repos");
                descriptif2.setText(seanceCycle.get(etape + 1));
                getWindow().getDecorView().setBackgroundColor(Color.rgb(232, 117, 117));
                ifBundleExist(seance.repos);
            }
            if (seanceCycle.get(etape) == "Repos Long") {
                descriptif.setText("Repos Long");
                if (etape + 1 == seanceCycle.size()) {
                    descriptif2.setText("FIN");
                } else {
                    descriptif2.setText(seanceCycle.get(etape + 1));
                }
                getWindow().getDecorView().setBackgroundColor(Color.rgb(232, 117, 117));
                ifBundleExist(seance.reposLong);
            }
            etape++;
        } else {
            Intent intent = new Intent(this, Fin.class);
            startActivity(intent);
            finish();
        }

    }

    //Sert lorsqu'on détruit l'activité
    //Si activité détruite puis recréée, on affiche le temps exact sans jouer le son
    //Si etapeTime == 0 on affiche la totalité du temps de l'étape et on joue le son
    public void ifBundleExist(int tempsEtape){
        if (etapeTime == 0) {
            startTimer(Long.valueOf(tempsEtape * 1000));
            play();
        } else {
            startTimer(etapeTime);
        }

    }

    //Fonction qui configure le temps global
    public void startGlobalTimer(long time) {

        globalTimer = new CountDownTimer(time, 10) {

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


    //Fonction qui configure le temps de chaque étape
    private void startTimer(long time) {

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


    //Fonction qui joue un son
    public void play() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        player = MediaPlayer.create(this, R.raw.bell);
        player.start();
    }

    //Sauvegarde des éléments importants lorsque l'étape est détruite pour les récupérer si elle est recréée
    @Override
    public void onSaveInstanceState(Bundle cycle) {
        super.onSaveInstanceState(cycle);
        globalTimer.cancel();
        timer.cancel();
        cycle.putLong("globalTime", globalTime);
        cycle.putLong("etapeTime", etapeTime);
        cycle.putInt("etape", etape);
        cycle.putInt("started", started);
    }



    //Détruit l'activité pour retourner sur la page Home en toute tranquillité (correction d'un bug qui faisait jouer le son même lorsque le timer n'existait plus).
    @Override
    public void onBackPressed() {
        Log.i("TAG", "ONBACKPRESSED");
        Pause();
        this.player = null;
        this.finish();
        super.onBackPressed();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_timer);
    }
}