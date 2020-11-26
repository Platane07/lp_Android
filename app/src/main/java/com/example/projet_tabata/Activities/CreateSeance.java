package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.Form.Commencer;
import com.example.projet_tabata.Form.Cycles;
import com.example.projet_tabata.Form.Sequences;
import com.example.projet_tabata.Form.Preparation;
import com.example.projet_tabata.Form.Repos;
import com.example.projet_tabata.Form.ReposLong;
import com.example.projet_tabata.Form.Travail;
import com.example.projet_tabata.R;

public class CreateSeance extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_seance_creation);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        next = findViewById(R.id.Next);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().addToBackStack("preparationFrag").replace(R.id.fragment, new Preparation(), "preparationFrag").commit();
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
                NumberPicker tmpsMinPreparationPicker = findViewById((R.id.MinPreparation));
                NumberPicker tmpsSecPreparationPicker = findViewById((R.id.SecPreparation));
                if (tmpsMinPreparationPicker.getValue() == 0 && tmpsSecPreparationPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de préparation dans votre séance ? vous risqueriez de vous déchirer un muscle", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                preparation = tmpsMinPreparationPicker.getValue()*60 + tmpsSecPreparationPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("sequencesFrag").replace(R.id.fragment, new Sequences(), "sequencesFrag").commit();
                break;
            case "nbSequences":
                NumberPicker nbSequencesPicker = findViewById((R.id.Sequences));
                if (nbSequencesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de séquences dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                sequences = nbSequencesPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("cyclesFrag").replace(R.id.fragment, new Cycles(), "cyclesFrag").commit();
                break;
            case "nbCycles":
                NumberPicker nbCyclesPicker = findViewById((R.id.Cycles));
                if (nbCyclesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Cycles dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                cycles = nbCyclesPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("travailFrag").replace(R.id.fragment, new Travail(), "travailFrag").commit();
                break;
            case "tmpsTravail":
                NumberPicker tmpsMinTravailPicker = findViewById((R.id.MinTravail));
                NumberPicker tmpsSecTravailPicker = findViewById((R.id.SecTravail));
                if (tmpsMinTravailPicker.getValue() == 0 && tmpsSecTravailPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                travail = tmpsMinTravailPicker.getValue()*60 + tmpsSecTravailPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("reposFrag").replace(R.id.fragment, new Repos(), "reposFrag").commit();
                break;
            case "tmpsReposCycle":
                NumberPicker tmpsMinReposPicker = findViewById((R.id.MinRepos));
                NumberPicker tmpsSecReposPicker = findViewById((R.id.SecRepos));
                if (tmpsMinReposPicker.getValue() == 0 && tmpsSecReposPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                travail = tmpsMinReposPicker.getValue()*60 + tmpsSecReposPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("reposLongFrag").replace(R.id.fragment, new ReposLong(), "reposLongFrag").commit();
                break;
            case "tmpsReposSequence":
                NumberPicker tmpsMinReposSequencePicker = findViewById((R.id.SecReposLong));
                NumberPicker tmpsSecReposSequencePicker = findViewById((R.id.SecReposLong));
                if (tmpsMinReposSequencePicker.getValue() == 0 && tmpsSecReposSequencePicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Temps de travail dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                reposLong = tmpsMinReposSequencePicker.getValue()*60 + tmpsSecReposSequencePicker.getValue();
                next.setText("Commencer la séance");
                newSeance = new Seance("", sequences, cycles, travail, repos, reposLong, preparation);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Seance", newSeance);
                Fragment commencer = new Commencer();
                commencer.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().addToBackStack("commencer").replace(R.id.fragment, commencer, "commencer").commit();
                break;
            case "commencer":
                if (newSeance == null) {
                    newSeance = new Seance("", sequences, cycles, travail, repos, reposLong, preparation);
                }
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Intent intent = new Intent(this, Seances.class);
                Log.i("WOULARDINEMOUKBEBEKTARTINEAUFOUTRE", newSeance.name);
                intent.putExtra("Seance", newSeance);
                startActivity(intent);
                break;

        }

    }

    public void recapitulatif(){
        TextView editPrep =  findViewById(R.id.EditPrep);
        Log.i("tmpsPreparation", String.valueOf(preparation));
        editPrep.setText(String.valueOf(preparation));
        TextView editSeq =  findViewById(R.id.EditSeq);
        editSeq.setText(sequences);
        TextView editCycles =  findViewById(R.id.EditCycles);
        editCycles.setText(cycles);
        TextView editRepos =  findViewById(R.id.EditRepos);
        editRepos.setText(repos);
        TextView editReposSeq =  findViewById(R.id.EditReposLong);
        editReposSeq.setText(reposLong);
        TextView editTravail =  findViewById(R.id.EditTravail);
        editTravail.setText(sequences);
        TextView tempsTotal =  findViewById(R.id.TempsTotal);
        tempsTotal.setText("oui");

    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            etape--;
        } else {
            super.onBackPressed();
        }
    }

}