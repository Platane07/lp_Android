package com.example.projet_tabata.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity
public class Seance {
    @PrimaryKey (autoGenerate = true)
    public int userCreatorId;
    public String name;
    public int sequence;
    public int cycle;
    public int repos;
    public int reposSeq;
    public int preparation;

    public Seance(int userCreatorId, String name, int sequence, int cycle, int repos, int reposSeq, int preparation) {
        this.userCreatorId = userCreatorId;
        this.name = name;
        this.sequence = sequence;
        this.cycle = cycle;
        this.repos = repos;
        this.reposSeq = reposSeq;
        this.preparation = preparation;
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

    public int getReposSeq() {
        return reposSeq;
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

    public void setReposSeq(int reposSeq) {
        this.reposSeq = reposSeq;
    }

}

