package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class Preparation extends Fragment {

    NumberPicker minPreparation;
    NumberPicker secPreparation;



    public Preparation() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_tmps_preparation, container, false);
        // Inflate the layout for this fragment
        minPreparation = view.findViewById(R.id.MinPreparation);
        secPreparation = view.findViewById(R.id.SecPreparation);

        if (savedInstanceState != null){
            minPreparation.setValue(savedInstanceState.getInt("minPreparation"));
            secPreparation.setValue(savedInstanceState.getInt("secPreparation"));
        }

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment

        minPreparation.setMaxValue(30);
        minPreparation.setMinValue(0);
        secPreparation.setMaxValue(60);
        secPreparation.setMinValue(0);
    }


    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("minPreparation", minPreparation.getValue());
        Save.putInt("secPreparation", secPreparation.getValue());


    }

}
