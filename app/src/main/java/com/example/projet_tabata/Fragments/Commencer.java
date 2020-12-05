package com.example.projet_tabata.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        View view = inflater.inflate(R.layout.from_commencer, container, false);
/*
        Seance seance = (Seance) getArguments().getSerializable("Seance");
        NumberPicker editPrep = view.findViewById(R.id.EditPrep);
        Log.i("tmpsPreparation", String.valueOf(seance.preparation));
        editPrep.setValue(seance.preparation);
        NumberPicker editSeq = view.findViewById(R.id.EditSeq);
        editSeq.setValue(String.valueOf(seance.sequence));
        TextView editCycles = view.findViewById(R.id.EditCycles);
        editCycles.setText(String.valueOf(seance.cycle));
        TextView editRepos = view.findViewById(R.id.EditRepos);
        editRepos.setText(convertMinutes(seance.repos));
        TextView editReposSeq = view.findViewById(R.id.EditReposLong);
        editReposSeq.setText(convertMinutes(seance.reposLong));
        TextView editTravail = view.findViewById(R.id.EditTravail);
        editTravail.setText(convertMinutes(seance.travail));
        TextView tempsTotal = view.findViewById(R.id.TempsTotal);
        tempsTotal.setText(convertMinutes(seance.getTempsTotal()));*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public String convertMinutes(int value){
        String temps;
        if (value > 60) {
        temps = (value/60)+"'"+(value%60)+"''";
        } else {
            temps = value+"'";
        }
        return temps;
    }

}