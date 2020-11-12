package com.example.projet_tabata.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithSeances {
    @Embedded public User user;

    @Relation (
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    public List<Seance> seances;
}
