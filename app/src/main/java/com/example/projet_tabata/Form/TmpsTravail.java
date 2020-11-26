package com.example.projet_tabata.Form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet_tabata.R;

public class TmpsTravail extends Fragment {
;
    NumberPicker minTravail;
    NumberPicker secTravail;

    public TmpsTravail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_tmps_travail, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
         minTravail = getView().findViewById(R.id.minutesTmpsTravail);
         secTravail = getView().findViewById(R.id.secondesTmpsTravail);

        if (savedInstanceState != null){
            minTravail.setValue(savedInstanceState.getInt("minTravail"));
            secTravail.setValue(savedInstanceState.getInt("secTravai"));
        }

        minTravail.setMaxValue(30);
        minTravail.setMinValue(0);
        secTravail.setMaxValue(60);
        secTravail.setMinValue(0);
    }

    @Override
    public void onSaveInstanceState(Bundle Save) {
        super.onSaveInstanceState(Save);

        Save.putInt("minTravail", minTravail.getValue());
        Save.putInt("secTravail", secTravail.getValue());


    }
}
