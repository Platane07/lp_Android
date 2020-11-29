package com.example.projet_tabata.Form;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

    View view;


    public Preparation() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // keep the fragment and all its data across screen rotation

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.form_tmps_preparation, container, false);


        minPreparation = view.findViewById(R.id.MinPreparation);
        secPreparation = view.findViewById(R.id.SecPreparation);



        if (savedInstanceState != null){
            minPreparation.setValue(savedInstanceState.getInt("minPreparation"));
            secPreparation.setValue(savedInstanceState.getInt("secPreparation"));
        }
        minPreparation.setValue(15);
        Log.i("TAG" , String.valueOf(minPreparation.getValue()));

      /*  if (view != null) {
            if ((ViewGroup)view.getParent() != null)
                ((ViewGroup)view.getParent()).removeView(view);
            return view;
        }*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            minPreparation.setValue(savedInstanceState.getInt("minPreparation"));
            secPreparation.setValue(savedInstanceState.getInt("secPreparation"));
        }

        minPreparation.setValue(30);

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
