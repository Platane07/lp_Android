package com.example.projet_tabata.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class ReposLong extends Fragment {

    NumberPicker minReposLong;
    NumberPicker secReposLong;

    public ReposLong() {
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
         minReposLong = getView().findViewById(R.id.MinReposLong);
         secReposLong = getView().findViewById(R.id.SecReposLong);

        if (savedInstanceState != null){
            minReposLong.setValue(savedInstanceState.getInt("minReposSeq"));
            secReposLong.setValue(savedInstanceState.getInt("secReposSeq"));
        }

        minReposLong.setMaxValue(30);
        minReposLong.setMinValue(0);
        secReposLong.setMaxValue(60);
        secReposLong.setMinValue(0);
    }

    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("minReposSeq", minReposLong.getValue());
        Save.putInt("secReposSeq", secReposLong.getValue());


    }
}
