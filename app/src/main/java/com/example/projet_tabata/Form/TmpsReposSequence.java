package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class TmpsReposSequence extends Fragment {

    NumberPicker minReposSeq;
    NumberPicker secReposSeq;

    public TmpsReposSequence() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_tmps_repos_sequence, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
         minReposSeq = getView().findViewById(R.id.minutesTmpsReposSequence);
         secReposSeq = getView().findViewById(R.id.secondesTmpsReposSequence);

        if (savedInstanceState != null){
            minReposSeq.setValue(savedInstanceState.getInt("minReposSeq"));
            secReposSeq.setValue(savedInstanceState.getInt("secReposSeq"));
        }

        minReposSeq.setMaxValue(30);
        minReposSeq.setMinValue(0);
        secReposSeq.setMaxValue(60);
        secReposSeq.setMinValue(0);
    }

    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("minReposSeq", minReposSeq.getValue());
        Save.putInt("secReposSeq", secReposSeq.getValue());


    }
}
