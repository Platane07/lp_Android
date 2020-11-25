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
        NumberPicker minRepos = getView().findViewById(R.id.minutesTmpsReposSequence);
        NumberPicker secRepos = getView().findViewById(R.id.secondesTmpsReposSequence);

        minRepos.setMaxValue(30);
        minRepos.setMinValue(0);
        secRepos.setMaxValue(60);
        secRepos.setMinValue(0);
    }
}
