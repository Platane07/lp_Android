package com.example.projet_tabata.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey (autoGenerate = true)
    public String userName;
    public String password;

}
