package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class Sequences extends Fragment {

    NumberPicker sequences;

    public Sequences() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_nb_sequences, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        sequences = getView().findViewById(R.id.Sequences);


        if (savedInstanceState != null){
            sequences.setValue(savedInstanceState.getInt("sequences"));
        }
        sequences.setMaxValue(1000);
        sequences.setMinValue(0);
    }

    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("sequences", sequences.getValue());


    }


}
