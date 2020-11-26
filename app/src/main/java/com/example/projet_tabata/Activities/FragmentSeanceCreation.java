package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    Button next;
    public int etape = 0;
    public int tmpsPreparation;
    public int nbSequences;
    public int nbCycles;
    public int tmpsTravail;
    public int tmpsReposCycle;
    public int tmpsReposSequences;
    Seance newSeance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_seance_creation);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        next = findViewById(R.id.Next);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().addToBackStack("preparationFrag").replace(R.id.fragment, new TmpsPreparation(), "preparationFrag").commit();
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
                getSupportFragmentManager().beginTransaction().addToBackStack("sequencesFrag").replace(R.id.fragment, new NbSequences(), "sequencesFrag").commit();
                break;
            case "nbSequences":
                NumberPicker nbSequencesPicker = findViewById((R.id.nbSequences));
                if (nbSequencesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de séquences dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                nbSequences = nbSequencesPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("cyclesFrag").replace(R.id.fragment, new NbCycles(), "cyclesFrag").commit();
                break;
            case "nbCycles":
                NumberPicker nbCyclesPicker = findViewById((R.id.nbCycles));
                if (nbCyclesPicker.getValue() == 0){
                    Toast.makeText(getApplicationContext(),"Pas de Cycles dans votre séance ? vous êtes cons", Toast.LENGTH_SHORT);
                    etape--;
                    break;
                }
                nbCycles = nbCyclesPicker.getValue();
                getSupportFragmentManager().beginTransaction().addToBackStack("travailFrag").replace(R.id.fragment, new TmpsTravail(), "travailFrag").commit();
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
                getSupportFragmentManager().beginTransaction().addToBackStack("reposFrag").replace(R.id.fragment, new TmpsRepos(), "reposFrag").commit();
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
                getSupportFragmentManager().beginTransaction().addToBackStack("reposLongFrag").replace(R.id.fragment, new TmpsReposSequence(), "reposLongFrag").commit();
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
                next.setText("Commencer la séance");
                newSeance = new Seance("", nbSequences, nbCycles, tmpsTravail, tmpsReposCycle, tmpsReposSequences, tmpsPreparation);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Seance", newSeance);
                Fragment commencer = new Commencer();
                commencer.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().addToBackStack("commencer").replace(R.id.fragment, commencer, "commencer").commit();
                break;
            case "commencer":
                if (newSeance == null) {
                    newSeance = new Seance("", nbSequences, nbCycles, tmpsTravail, tmpsReposCycle, tmpsReposSequences, tmpsPreparation);
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
        Log.i("tmpsPreparation", String.valueOf(tmpsPreparation));
        editPrep.setText(String.valueOf(tmpsPreparation));
        TextView editSeq =  findViewById(R.id.EditSeq);
        editSeq.setText(nbSequences);
        TextView editCycles =  findViewById(R.id.EditCycles);
        editCycles.setText(nbCycles);
        TextView editRepos =  findViewById(R.id.EditRepos);
        editRepos.setText(tmpsReposCycle);
        TextView editReposSeq =  findViewById(R.id.EditReposLong);
        editReposSeq.setText(tmpsReposSequences);
        TextView editTravail =  findViewById(R.id.EditTravail);
        editTravail.setText(nbSequences);
        TextView tempsTotal =  findViewById(R.id.TempsTotal);
        tempsTotal.setText("oui");

    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0){
            etape--;
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void passDataIn(){}

}