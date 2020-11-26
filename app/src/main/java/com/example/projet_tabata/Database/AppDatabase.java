package com.example.projet_tabata.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.projet_tabata.Dao.SeanceDAO;
import com.example.projet_tabata.Entities.Seance;

@Database(entities = {Seance.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SeanceDAO seanceDao();
}