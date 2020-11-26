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
        TextView editPrep = view.findViewById(R.id.EditPrep);
        Log.i("tmpsPreparation", String.valueOf(seance.preparation));
        editPrep.setText(convertMinutes(seance.preparation));
        TextView editSeq = view.findViewById(R.id.EditSeq);
        editSeq.setText(convertMinutes(seance.sequence));
        TextView editCycles = view.findViewById(R.id.EditCycles);
        editCycles.setText(convertMinutes((seance.cycle)));
        TextView editRepos = view.findViewById(R.id.EditRepos);
        editRepos.setText(convertMinutes(seance.repos));
        TextView editReposSeq = view.findViewById(R.id.EditReposLong);
        editReposSeq.setText(convertMinutes(seance.reposLong));
        TextView editTravail = view.findViewById(R.id.EditTravail);
        editTravail.setText(convertMinutes(seance.travail));
        TextView tempsTotal = view.findViewById(R.id.TempsTotal);
        tempsTotal.setText(convertMinutes(seance.getTempsTotal()));

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