package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class TmpsPreparation extends Fragment {

    public TmpsPreparation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_tmps_preparation, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        NumberPicker minPrep = getView().findViewById(R.id.minutesTmpsPreparation);
        NumberPicker secPrep = getView().findViewById(R.id.secondesTmpsPreparation);

        minPrep.setMaxValue(30);
        minPrep.setMinValue(0);
        secPrep.setMaxValue(60);
        secPrep.setMinValue(0);
    }

}
