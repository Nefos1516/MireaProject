package ru.mirea.netelev.mireaproject.story;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Story {
    @PrimaryKey(autoGenerate = true)

    public long id;
    public String number;
    public String name;
    public String age;
}