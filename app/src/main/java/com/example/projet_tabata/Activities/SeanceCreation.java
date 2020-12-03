package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

import java.util.List;
import java.util.Locale;

public class SeanceCreation extends AppCompatActivity {
    private DatabaseClient mDb;

    public Seance newSeance;
    public String nom;
    public int preparation;
    public int sequences;
    public int cycles;
    public int travail;
    public int repos;
    public int reposLong;
    boolean modif = false;

    NumberPicker cyclesPicker;
    NumberPicker sequencesPicker;
    NumberPicker preparationPicker;
    NumberPicker travailPicker;
    NumberPicker reposPicker;
    NumberPicker reposLongPicker;
    TextView tempsTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_creation);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        Intent intent = getIntent();

        if (intent.getSerializableExtra("Seance") != null){
            newSeance = (Seance) intent.getSerializableExtra("Seance");
            modif = true;
        } else {
            newSeance = new Seance("", 0,0,0,0,0,0);
        }


        tempsTotal = findViewById(R.id.tempsTotal);
        setTempsTotal();

        setUpPicker();





    }

    public void setUpPicker(){
        preparationPicker = findViewById(R.id.EditPrep);
        sequencesPicker = findViewById(R.id.EditSeq);
        cyclesPicker = findViewById(R.id.EditCycles);
        travailPicker = findViewById(R.id.EditTravail);
        reposPicker = findViewById(R.id.EditRepos);
        reposLongPicker = findViewById(R.id.EditReposLong);

        preparationPicker.setMinValue(0);
        preparationPicker.setMaxValue(900);
        preparationPicker.setValue(newSeance.preparation);

        preparationPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                newSeance.preparation = preparationPicker.getValue();
                setTempsTotal();
            }
        });

        sequencesPicker.setMinValue(1);
        sequencesPicker.setMaxValue(20);
        sequencesPicker.setValue(newSeance.sequence);

        sequencesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                newSeance.sequence = sequencesPicker.getValue();
                setTempsTotal();
            }
        });

        cyclesPicker.setMinValue(1);
        cyclesPicker.setMaxValue(20);
        cyclesPicker.setValue(newSeance.cycle);

        cyclesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                newSeance.cycle = cyclesPicker.getValue();
                setTempsTotal();
            }
        });

        travailPicker.setMinValue(1);
        travailPicker.setMaxValue(900);
        travailPicker.setValue(newSeance.travail);

        travailPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                newSeance.travail = travailPicker.getValue();
                setTempsTotal();
            }
        });

        reposPicker.setMinValue(1);
        reposPicker.setMaxValue(900);
        reposPicker.setValue(newSeance.repos);

        reposPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                newSeance.repos = reposPicker.getValue();
                setTempsTotal();
            }
        });

        reposLongPicker.setMinValue(1);
        reposLongPicker.setMaxValue(900);
        reposLongPicker.setValue(newSeance.reposLong);

        reposLongPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                newSeance.reposLong = reposLongPicker.getValue();
                setTempsTotal();

            }
        });



    }

    public void setTempsTotal(){
        if (newSeance.getTempsTotal() > 60) {
            tempsTotal.setText(Integer.toString(newSeance.getTempsTotal() / 60) + ":" + Integer.toString(newSeance.getTempsTotal() % 60));
        } else {
            tempsTotal.setText(Integer.toString(newSeance.getTempsTotal()) + "s");

        }
    }

    public void Sauvegarder(View v){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Entrez un nom pour votre séance");

            EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nom = input.getText().toString();
                    newSeance.name = nom;

                    class InsertSeanceInDB extends AsyncTask<Void, Void, Void> {

                        @Override
                        protected Void doInBackground(Void... voids) {

                            if (modif == true ) {
                                mDb.getAppDatabase().seanceDao().update(newSeance);
                            } else {
                                mDb.getAppDatabase().seanceDao().insert(newSeance);
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void avoid) {
                            super.onPostExecute(avoid);
                        }
                    }
                    InsertSeanceInDB iDB = new InsertSeanceInDB();
                    iDB.execute();
                    Toast.makeText(getApplicationContext(), "Séance sauvegardée", Toast.LENGTH_SHORT).show();

                }
            });
            builder.show();


        }
    public void Commencer(View v) {

        Intent intent = new Intent(this, Seances.class);
        intent.putExtra("Seance", newSeance);
        startActivity(intent);
        finish();

    }


}