package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class Repos extends Fragment {

    NumberPicker minRepos;
    NumberPicker secRepos;

    public Repos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_tmps_repos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        minRepos = getView().findViewById(R.id.MinRepos);
        secRepos = getView().findViewById(R.id.SecRepos);


        if (savedInstanceState != null){
            minRepos.setValue(savedInstanceState.getInt("minRepos"));
            secRepos.setValue(savedInstanceState.getInt("secRepos"));
        }
        minRepos.setMaxValue(30);
        minRepos.setMinValue(0);
        secRepos.setMaxValue(60);
        secRepos.setMinValue(0);
    }

    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("minRepos", minRepos.getValue());
        Save.putInt("secRepos", minRepos.getValue());


    }
}
