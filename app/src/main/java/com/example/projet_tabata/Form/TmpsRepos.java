package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class TmpsRepos extends Fragment {

    public TmpsRepos() {
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
        NumberPicker minTmpsRepos = getView().findViewById(R.id.minutesTmpsRepos);
        NumberPicker secTmpsRepos = getView().findViewById(R.id.secondesTmpsRepos);

        minTmpsRepos.setMaxValue(30);
        minTmpsRepos.setMinValue(0);
        secTmpsRepos.setMaxValue(60);
        secTmpsRepos.setMinValue(0);
    }
}
