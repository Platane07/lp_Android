package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

public class SeanceCreation extends AppCompatActivity {
    private DatabaseClient mDb;

    public Seance newSeance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_creation);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        Intent intent = getIntent();
    }

    public void commencer(View v){

        EditText Nom = findViewById(R.id.nom);
        String nom = Nom.getText().toString();
        EditText TmpsPreparation = findViewById(R.id.tmpsPreparation);
        int tmpsPreparation = Integer.parseInt(String.valueOf(TmpsPreparation.getText()));
        EditText NbSequences = findViewById(R.id.Sequences);
        int nbSequences = Integer.parseInt(String.valueOf(NbSequences.getText()));
        EditText NbCycles = findViewById(R.id.Cycles);
        int nbCycles = Integer.parseInt(String.valueOf(NbCycles.getText()));
        EditText TmpsTravail = findViewById(R.id.tmpsTravail);
        int tmpsTravail =Integer.parseInt(String.valueOf(TmpsTravail.getText()));
        EditText TmpsReposCycle = findViewById(R.id.tmpsReposCycle);
        int tmpsReposCycle = Integer.parseInt(String.valueOf(TmpsReposCycle.getText()));
        EditText TmpsReposSequence = findViewById(R.id.tmpsReposSequence);
        int tmpsReposSequence = Integer.parseInt(String.valueOf(TmpsReposSequence.getText()));

        newSeance = new Seance(nom, nbSequences, nbCycles, tmpsTravail, tmpsReposCycle, tmpsReposSequence, tmpsPreparation);
        //Inserer dans database
        class InsertSeanceInDB extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids){

                mDb.getAppDatabase().seanceDao().insert(newSeance);
                Log.i("WOULARDINEMOUK", newSeance.name);
                //Toast.makeText(getApplicationContext() ,"INSERTION ANALE", Toast.LENGTH_SHORT).show();
                return null;
            }

            @Override
            protected void onPostExecute(Void avoid){
                super.onPostExecute(avoid);
            }
        }
        InsertSeanceInDB iDB = new InsertSeanceInDB();
        iDB.execute();
        Intent intent = new Intent(this, Seances.class);
        Log.i("WOULARDINEMOUKBEBEKTARTINEAUFOUTRE", newSeance.name);
        intent.putExtra("Seance", newSeance);
        startActivity(intent);
        Toast.makeText(getApplicationContext() ,"INSERTION ANALE", Toast.LENGTH_SHORT).show();

        //addForm.replace(R.id.bottom_fragment, new Seance());




    }
}