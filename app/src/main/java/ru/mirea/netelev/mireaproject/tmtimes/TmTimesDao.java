package ru.mirea.netelev.mireaproject.tmtimes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TmTimesDao {
    @Query("SELECT * FROM tmtimes")
    LiveData<List<TmTimes>> getAll();
    @Query("SELECT * FROM tmtimes WHERE id = :id")
    TmTimes getById(long id);
    @Insert
    void insert(TmTimes time);
    @Update
    void update(TmTimes time);
    @Delete
    void delete(TmTimes time);
}
