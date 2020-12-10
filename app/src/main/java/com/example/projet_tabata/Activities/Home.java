package com.example.projet_tabata.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.projet_tabata.Database.DatabaseClient;
import com.example.projet_tabata.Entities.Seance;
import com.example.projet_tabata.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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



    @Override
    protected void onRestart() {
        super.onRestart();
        AfficheSeances();
    }




    //Créer nouvel entrainement (si déjà la base de données ne contient pas d'entrainements alors la création se fait en mode tuto (avec des fragments)
    public void creer (View v){
        if (seances.size() > 0 ) {
            Intent intent = new Intent(this, SeanceCreation.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Tutoriel.class);
            startActivity(intent);
        }
    }

    //Remplissage de la list des séances de l'utilisateur
    public void AfficheSeances() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer les séances
        class GetSeances extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground(Void... voids) {

                // Récupération des séances dans la base de données
                seances = mDb
                        .getAppDatabase()
                        .seanceDao()
                        .loadAllSeance();


                return seances;
            }

            @Override
            protected void onPostExecute(List<Seance> seances) {
                super.onPostExecute(seances);


                ArrayList<HashMap<String, String>> mapSeances = new ArrayList<>();

                // Affichage des séances sous forme de liste;
                for (Seance seance : seances) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", seance.name);
                    hashMap.put("tempsTotal", seance.getTempsTotal()/60 + ":" + seance.getTempsTotal()%60);
                    mapSeances.add(hashMap);
                }
                String[] from = {"name", "tempsTotal"};
                int[] to = {R.id.seanceName, R.id.tempsTotal};
                SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), mapSeances, R.layout.affichage_seance,from, to);
                listView.setAdapter(simpleAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            afficherAlertDialog(seances.get(i));

                    }
                });
            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetSeances gs = new GetSeances();
        gs.execute();

    }

    //Suppression de la séance lorsque'on clique sur supprimer dans la boite de dialogue et refresh de la liste
    public void supprimerSeance(Seance seance){

        class DeleteDB extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                mDb.getAppDatabase().seanceDao().deleteSeance(seance);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                AfficheSeances();
            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        DeleteDB dDB = new DeleteDB();
        dDB.execute();
    }

    //Fonction qui affiche la boite de dialogue pour chaque séance de la liste
    public void afficherAlertDialog(Seance seance){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Home.this );

        alertDialogBuilder.setTitle(seance.name);

        alertDialogBuilder
                .setMessage("Que souhaitez-vous faire ?")
                .setCancelable(true)
                .setPositiveButton("Commencer",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(getApplicationContext(), Timer.class);
                        intent.putExtra("Seance",seance);
                        startActivity(intent);

                    }
                })
                .setNeutralButton("Modifier",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(getApplicationContext(), SeanceCreation.class);
                        intent.putExtra("SeanceUpdate",seance);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Supprimer",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        supprimerSeance(seance);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}