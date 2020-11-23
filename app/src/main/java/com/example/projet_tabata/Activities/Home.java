package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projet_tabata.Activities.SeanceCreation;
import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDb = DatabaseClient.getInstance(getApplicationContext());

    }




    public void creer (View v){
        Intent intent = new Intent(this, SeanceCreation.class);
        startActivity(intent);
    }
    private void miseAJour() {

        ///////////////////////
        // Classe asynchrone permettant de récupérer les users et les books
        class GetUsers extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground(Void... voids) {

                // Liste des users
                List<Seance> seances = mDb
                        .getAppDatabase()
                        .seanceDao()
                        .loadAllSeance();

                // Rechercher les books à partir des users


                return seances;
            }

            @Override
            protected void onPostExecute(List<Seance> seances) {
                super.onPostExecute(seances);

                ArrayList<LinearLayout> tableForListView = new ArrayList<LinearLayout>();
                ListView listSeance = findViewById(R.id.listSeances);
                LinearLayout linearLayout = new LinearLayout(getApplicationContext());

                // Affichage des utilisateurs;
                for (Seance seance : seances) {
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.weight = 1;
                    linearLayout.setLayoutParams(lp);

                    TextView nameSeance = new TextView(getApplicationContext());
                    nameSeance.setText(seance.name);
                    linearLayout.addView(nameSeance);
                    tableForListView.add(linearLayout);

                }
                ArrayAdapter<LinearLayout> itemsAdapter =
                        new ArrayAdapter<LinearLayout>(this, R.layout.affichage_seance, tableForListView);


            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetUsers gu = new GetUsers();
        gu.execute();

    }
}