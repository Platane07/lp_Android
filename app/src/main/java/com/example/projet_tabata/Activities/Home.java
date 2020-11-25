package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_tabata.Activities.SeanceCreation;
import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private DatabaseClient mDb;
    ListView listView;
    List<Seance> seances;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDb = DatabaseClient.getInstance(getApplicationContext());

        listView = findViewById(R.id.listSeances);

        AfficheSeances();
    }




    public void creer (View v){
        Intent intent = new Intent(this, SeanceCreation.class);
        startActivity(intent);
    }
    public void AfficheSeances() {

        ///////////////////////
        // Classe asynchrone permettant de récupérer les users et les books
        class GetSeances extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground(Void... voids) {

                // Liste des users
                seances = mDb
                        .getAppDatabase()
                        .seanceDao()
                        .loadAllSeance();

                // Rechercher les books à partir des users


                return seances;
            }

            @Override
            protected void onPostExecute(List<Seance> seances) {
                super.onPostExecute(seances);


                ArrayList<HashMap<String, String>> mapSeances = new ArrayList<>();

                // Affichage des utilisateurs;
                for (Seance seance : seances) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", seance.name);
                    hashMap.put("id", String.valueOf(seance.userCreatorId));
                    mapSeances.add(hashMap);
                }
                String[] from = {"name", "id"};
                int[] to = {R.id.seanceName, R.id.supprimer};
                SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), mapSeances, R.layout.affichage_seance,from, to);
                listView.setAdapter(simpleAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), Seances.class);
                        intent.putExtra("Seance",seances.get(i));
                        startActivity(intent);
                    }
                });

            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetSeances gs = new GetSeances();
        gs.execute();

    }
}