package com.example.projet_tabata.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Seance implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int userCreatorId;
    public String name;
    public int sequence;
    public int cycle;
    public int travail;
    public int repos;
    public int reposLong;
    public int preparation;
    @Ignore
    public ArrayList<String> seanceCycles;
    @Ignore
    public int tempsTotal;

    public Seance(String name, int sequence, int cycle, int travail, int repos, int reposLong, int preparation) {
        this.name = name;
        this.sequence = sequence;
        this.cycle = cycle;
        this.travail = travail;
        this.repos = repos;
        this.reposLong = reposLong;
        this.preparation = preparation;
        seanceCycles = new ArrayList<>();
    }

    //Création de la liste que l'on va parcourir pour faire avancer le tabatatimer
    public ArrayList getSeanceCycle() {
        this.seanceCycles.clear();

        this.seanceCycles.add("Preparation");

        for (int i = 0; i < this.sequence; i++) {
            for (int j = 0; j < this.cycle; j++) {
                this.seanceCycles.add("Travail");
                this.seanceCycles.add("Repos");
            }
            this.seanceCycles.add("Repos Long");
        }
        return this.seanceCycles;

    }

    //Récupération du temps total de l'activité
    public int getTempsTotal(){
        this.tempsTotal = 0;

        this.tempsTotal = this.preparation + ((this.travail + this.repos)*this.cycle + this.reposLong)*this.sequence;

        return this.tempsTotal;

    }



    public int getUserCreatorId() {
        return userCreatorId;
    }

    public String getName() {
        return name;
    }

    public int getSequence() {
        return sequence;
    }

    public int getCycle() {
        return cycle;
    }

    public int getRepos() {
        return repos;
    }

    public int getReposLong() {
        return reposLong;
    }

    public int getPreparation() {
        return preparation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

    public void setPreparation(int preparation) {
        this.preparation = preparation;
    }

    public void setReposLong(int reposLong) {
        this.reposLong = reposLong;
    }

    public void setUserCreatorId(int userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public int getTravail() {
        return travail;
    }

    public void setTravail(int travail) {
        this.travail = travail;
    }

}

