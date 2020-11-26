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

    NumberPicker minPrep;
    NumberPicker secPrep;



    public TmpsPreparation() {
        /*TmpsPreparation tmpsPreparation = new TmpsPreparation();
        return tmpsPreparation;*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_tmps_preparation, container, false);
        // Inflate the layout for this fragment
        minPrep = view.findViewById(R.id.minutesTmpsPreparation);
        secPrep = view.findViewById(R.id.secondesTmpsPreparation);

        if (savedInstanceState != null){
            minPrep.setValue(savedInstanceState.getInt("minPrep"));
            secPrep.setValue(savedInstanceState.getInt("secPrep"));
        }


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment

        minPrep.setMaxValue(30);
        minPrep.setMinValue(0);
        secPrep.setMaxValue(60);
        secPrep.setMinValue(0);
    }


    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("minPrep", minPrep.getValue());
        Save.putInt("secPrep", secPrep.getValue());


    }

}
