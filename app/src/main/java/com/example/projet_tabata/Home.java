package com.example.projet_tabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }




    public void commencer (View v){
        Intent intent = new Intent(this, Seance.class);
        intent.putExtra("seance", "nouveau" );
        startActivity(intent);
    }
}