package ru.mirea.netelev.mireaproject.tmtimes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class TmTimes {
    @PrimaryKey(autoGenerate = true)

    public long id;
    public String number;
    public String name;
    public String time;
}
