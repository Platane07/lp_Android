package com.example.projet_tabata.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.projet_tabata.Entities.Seance;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static java.nio.charset.CodingErrorAction.REPLACE;

@Dao
public interface SeanceDAO {

    @Insert
    long insert(Seance seance);

    @Delete
    public void deleteSeance(Seance... seance);

    @Query("SELECT * FROM seance")
    public List<Seance> loadAllSeance();



}
