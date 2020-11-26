package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class NbCycles extends Fragment {

    NumberPicker nbCycles;


    public NbCycles() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_nb_cycles, container, false);


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        nbCycles = getView().findViewById(R.id.nbCycles);

        if (savedInstanceState != null){
            nbCycles.setValue(savedInstanceState.getInt("nbCycles"));
        }
        nbCycles.setMaxValue(100);
        nbCycles.setMinValue(0);
    }

    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("nbCycles", nbCycles.getValue());


    }


}
