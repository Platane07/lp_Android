package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.Fragments.Cycles;
import com.example.projet_tabata.Fragments.Sequences;
import com.example.projet_tabata.Fragments.Preparation;
import com.example.projet_tabata.Fragments.Repos;
import com.example.projet_tabata.Fragments.ReposLong;
import com.example.projet_tabata.Fragments.Travail;
import com.example.projet_tabata.R;

public class FirstSeanceCreation extends AppCompatActivity {
    private DatabaseClient mDb;

    String[] etapeFragments  = {"RIEN","tmpsPreparation","nbSequences", "nbCycles", "tmpsTravail", "tmpsReposCycle", "tmpsReposSequence","commencer"};
    String nom;
    Button next;
    public int etape = 0;
    public int preparation;
    public int sequences;
    public int cycles;
    public int travail;
    public int repos;
    public int reposLong;
    Seance newSeance;
    Bundle state;

    NumberPicker cyclesPicker;
    NumberPicker sequencesPicker;
    NumberPicker minPreparationPicker;
    NumberPicker secPreparationPicker;
    NumberPicker minTravailPicker;
    NumberPicker secTravailPicker;
    NumberPicker minReposPicker;
    NumberPicker secReposPicker;
    NumberPicker minReposLongPicker;
    NumberPicker secReposLongPicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_seance_creation);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        state = new Bundle();

        next = findViewById(R.id.Next);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Preparation(), "preparationFrag").commit();
        } else {
                //Restore the fragment's instance
                getSupportFragmentManager().getFragment(savedInstanceState, "travailFrag");
            }
        }

    public void Next(View v){
        nextForm();
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
                //newSeance = new Seance(nom, nbSequences, nbCycles, tmpsTravail, tmpsReposCycle, tmpsReposSequences, tmpsPreparation);
                class InsertSeanceInDB extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids){

                        mDb.getAppDatabase().seanceDao().insert(newSeance);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void avoid){
                        super.onPostExecute(avoid);
                    }
                }
                InsertSeanceInDB iDB = new InsertSeanceInDB();
                iDB.execute();
                Toast.makeText(getApplicationContext() ,"Séance sauvegardée", Toast.LENGTH_SHORT).show();

            }
        });
        builder.show();


    }



    public void nextForm(){
        etape++;
        switch (etapeFragments[etape]){
            case "tmpsPreparation":
                minPreparationPicker = findViewById((R.id.MinPreparation));
                secPreparationPicker = findViewById((R.id.SecPreparation));
                if (minPreparationPicker.getValue() == 0 && secPreparationPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de préparation dans votre séance ? vous risqueriez de vous déchirer un muscle", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                preparation = minPreparationPicker.getValue()*60 + secPreparationPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("sequencesFrag").replace(R.id.fragment, new Sequences(), "sequencesFrag").commit();
                break;
            case "nbSequences":
                sequencesPicker = findViewById((R.id.Sequences));
                if (sequencesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de séquences dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                sequences = sequencesPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("cyclesFrag").replace(R.id.fragment, new Cycles(), "cyclesFrag").commit();
                break;
            case "nbCycles":
                cyclesPicker = findViewById((R.id.Cycles));
                if (cyclesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Cycles dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                cycles = cyclesPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("travailFrag").replace(R.id.fragment, new Travail(), "travailFrag").commit();
                break;
            case "tmpsTravail":
                 minTravailPicker = findViewById((R.id.MinTravail));
                 secTravailPicker = findViewById((R.id.SecTravail));
                if (minTravailPicker.getValue() == 0 && secTravailPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                travail = minTravailPicker.getValue()*60 + secTravailPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("reposFrag").replace(R.id.fragment, new Repos(), "reposFrag").commit();
                break;
            case "tmpsReposCycle":
                minReposPicker = findViewById((R.id.MinRepos));
                secReposPicker = findViewById((R.id.SecRepos));
                if (minReposPicker.getValue() == 0 && secReposPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                repos = minReposPicker.getValue()*60 + secReposPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("reposLongFrag").replace(R.id.fragment, new ReposLong(), "reposLongFrag").commit();
                break;
            case "tmpsReposSequence":
                minReposLongPicker = findViewById((R.id.SecReposLong));
                secReposLongPicker = findViewById((R.id.SecReposLong));
                if (minReposLongPicker.getValue() == 0 && secReposLongPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                reposLong = minReposLongPicker.getValue()*60 + secReposLongPicker.getValue();
                next.setText("Commencer la séance");
                newSeance = new Seance("", sequences, cycles, travail, repos, reposLong, preparation);
                Intent intent = new Intent(this, SeanceCreation.class);
                intent.putExtra("FirstSeance", newSeance);
                startActivity(intent);
                finish();

                break;
        }

    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            Restore();
            etape--;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        state.putInt("preparation", preparation);
        state.putInt("sequences", sequences);
        state.putInt("cycles", cycles);
        state.putInt("travail", travail);
        state.putInt("repos", repos);
        state.putInt("reposLong", reposLong);
        savedInstanceState = state;
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstance){
        state = savedInstance;
        Restore();
    }

    public void Restore(){
        if (state.getInt("preparation") > 60 ) {
            minPreparationPicker.setValue(state.getInt("preparation")/60);
            secPreparationPicker.setValue(state.getInt("preparation")%60);
        }
    }


}