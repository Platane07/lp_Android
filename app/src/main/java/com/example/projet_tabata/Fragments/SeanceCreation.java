package com.example.projet_tabata.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.projet_tabata.Database.AppDatabase;
import com.example.projet_tabata.R;

public class SeanceCreation extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seance_creation, container, false);
    }
    public void commencer(View v){
        EditText Nom = getView().findViewById(R.id.nom);
        String nom = Nom.getText().toString();
        EditText TmpsPreparation = getView().findViewById(R.id.tmpsPreparation);
        int tmpsPreparation = Integer.parseInt(String.valueOf(TmpsPreparation.getText()));
        EditText NbSequences = getView().findViewById(R.id.nbSequences);
        int nbSequences = Integer.parseInt(String.valueOf(NbSequences.getText()));
        EditText NbCycles = getView().findViewById(R.id.nbCycles);
        int nbCycles = Integer.parseInt(String.valueOf(NbCycles.getText()));
        EditText TmpsTravail = getView().findViewById(R.id.tmpsTravail);
        int tmpsTravail =Integer.parseInt(String.valueOf(TmpsTravail.getText()));
        EditText TmpsReposCycle = getView().findViewById(R.id.tmpsReposCycle);
        int tmpsReposCycle = Integer.parseInt(String.valueOf(TmpsReposCycle.getText()));
        EditText TmpsReposSequence = getView().findViewById(R.id.tmpsReposSequence);
        int tmpsReposSequence = Integer.parseInt(String.valueOf(TmpsReposSequence.getText()));

        //Inserer dans database
        class InsertSeanceInDB extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids){

            }
        }




    }



}
