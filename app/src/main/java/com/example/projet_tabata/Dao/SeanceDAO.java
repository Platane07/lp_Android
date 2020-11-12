package com.example.projet_tabata.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.projet_tabata.Entities.Seance;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static java.nio.charset.CodingErrorAction.REPLACE;

@Dao
public interface SeanceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSeance(Seance... seance);

    @Delete
    public void deleteSeance(Seance... seance);

    @Query("SELECT * FROM seance")
    public Seance[] loadAllSeance();



}
