package com.example.projet_tabata.Form;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;


public class Commencer extends Fragment {


    public Commencer() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.form_commencer, container, false);


        Seance seance = (Seance) getArguments().getSerializable("Seance");
        //TextView editPrep = getView().findViewById(R.id.EditPrep);
        //Log.i("tmpsPreparation", String.valueOf(seance.preparation));
        //editPrep.setText(String.valueOf(seance.preparation));
        TextView editSeq = getView().findViewById(R.id.EditSeq);
        editSeq.setText(seance.sequence);
        TextView editCycles = getView().findViewById(R.id.EditCycles);
        editCycles.setText(seance.cycle);
        TextView editRepos = getView().findViewById(R.id.EditRepos);
        editRepos.setText(seance.repos);
        TextView editReposSeq = getView().findViewById(R.id.EditReposLong);
        editReposSeq.setText(seance.reposSeq);
        TextView editTravail = getView().findViewById(R.id.EditTravail);
        editTravail.setText(seance.travail);
        TextView tempsTotal = getView().findViewById(R.id.TempsTotal);
        tempsTotal.setText(seance.getTempsTotal());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

}