package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.Form.Commencer;
import com.example.projet_tabata.Form.NbCycles;
import com.example.projet_tabata.Form.NbSequences;
import com.example.projet_tabata.Form.TmpsPreparation;
import com.example.projet_tabata.Form.TmpsRepos;
import com.example.projet_tabata.Form.TmpsReposSequence;
import com.example.projet_tabata.Form.TmpsTravail;
import com.example.projet_tabata.R;

public class FragmentSeanceCreation extends AppCompatActivity {
    private DatabaseClient mDb;

    String[] etapeFragments  = {"RIEN","tmpsPreparation","nbSequences", "nbCycles", "tmpsTravail", "tmpsReposCycle", "tmpsReposSequence","commencer"};
    String nom;
    int etape = 0;
    int tmpsPreparation;
    int nbSequences;
    int nbCycles;
    int tmpsTravail;
    int tmpsReposCycle;
    int tmpsReposSequences;
    Seance newSeance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_seance_creation);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        Fragment tmpsPreparation = new TmpsPreparation();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, tmpsPreparation);
        transaction.commit();




    }

    public void Next(View v){
        nextForm();
    }


    public void Commencer(View v){
        if (newSeance == null) {
            newSeance = new Seance("", nbSequences, nbCycles, tmpsTravail, tmpsReposCycle, tmpsReposSequences, tmpsPreparation);
        }
        Intent intent = new Intent(this, Seances.class);
        Log.i("WOULARDINEMOUKBEBEKTARTINEAUFOUTRE", newSeance.name);
        intent.putExtra("Seance", newSeance);
        startActivity(intent);


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
                newSeance = new Seance(nom, nbSequences, nbCycles, tmpsTravail, tmpsReposCycle, tmpsReposSequences, tmpsPreparation);
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
                Toast.makeText(getApplicationContext() ,"Séance sauvegardée", Toast.LENGTH_SHORT).show();

            }
        });
        builder.show();


    }



    public void nextForm(){
        etape++;
        switch (etapeFragments[etape]){
            case "tmpsPreparation":
                NumberPicker tmpsMinPreparationPicker = findViewById((R.id.minutesTmpsPreparation));
                NumberPicker tmpsSecPreparationPicker = findViewById((R.id.secondesTmpsPreparation));
                if (tmpsMinPreparationPicker.getValue() == 0 && tmpsSecPreparationPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de préparation dans votre séance ? vous risqueriez de vous déchirer un muscle", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                tmpsPreparation = tmpsMinPreparationPicker.getValue()*60 + tmpsSecPreparationPicker.getValue();
                Fragment nbSequencesFragment = new NbSequences();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, nbSequencesFragment);
                transaction.commit();
                break;
            case "nbSequences":
                NumberPicker nbSequencesPicker = findViewById((R.id.nbSequences));
                if (nbSequencesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de séquences dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                nbSequences = nbSequencesPicker.getValue();
                Fragment nbCyclesFragment = new NbCycles();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.fragment, nbCyclesFragment);
                transaction2.commit();
                break;
            case "nbCycles":
                NumberPicker nbCyclesPicker = findViewById((R.id.nbCycles));
                if (nbCyclesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Cycles dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                nbCycles = nbCyclesPicker.getValue();
                Fragment tmpsTravailFragment = new TmpsTravail();
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment, tmpsTravailFragment);
                transaction3.commit();
                break;
            case "tmpsTravail":
                NumberPicker tmpsMinTravailPicker = findViewById((R.id.minutesTmpsTravail));
                NumberPicker tmpsSecTravailPicker = findViewById((R.id.secondesTmpsTravail));
                if (tmpsMinTravailPicker.getValue() == 0 && tmpsSecTravailPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                tmpsTravail = tmpsMinTravailPicker.getValue()*60 + tmpsSecTravailPicker.getValue();
                Fragment tmpsReposCyclesFragment = new TmpsRepos();
                FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                transaction4.replace(R.id.fragment, tmpsReposCyclesFragment);
                transaction4.commit();
                break;
            case "tmpsReposCycle":
                NumberPicker tmpsMinReposPicker = findViewById((R.id.minutesTmpsRepos));
                NumberPicker tmpsSecReposPicker = findViewById((R.id.secondesTmpsRepos));
                if (tmpsMinReposPicker.getValue() == 0 && tmpsSecReposPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                tmpsTravail = tmpsMinReposPicker.getValue()*60 + tmpsSecReposPicker.getValue();
                Fragment tmpsReposSequenceFragment = new TmpsReposSequence();
                FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                transaction5.replace(R.id.fragment, tmpsReposSequenceFragment);
                transaction5.commit();
                break;
            case "tmpsReposSequence":
                NumberPicker tmpsMinReposSequencePicker = findViewById((R.id.secondesTmpsReposSequence));
                NumberPicker tmpsSecReposSequencePicker = findViewById((R.id.secondesTmpsReposSequence));
                if (tmpsMinReposSequencePicker.getValue() == 0 && tmpsSecReposSequencePicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                tmpsReposSequences = tmpsMinReposSequencePicker.getValue()*60 + tmpsSecReposSequencePicker.getValue();
                Fragment commencerFragment = new Commencer();
                FragmentTransaction transaction6 = getSupportFragmentManager().beginTransaction();
                transaction6.replace(R.id.fragment, commencerFragment);
                transaction6.commit();
                break;
        }

    }


}