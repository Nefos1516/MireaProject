package ru.mirea.netelev.mireaproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.mirea.netelev.mireaproject.story.Story;
import ru.mirea.netelev.mireaproject.story.StoryDao;
import ru.mirea.netelev.mireaproject.tmtimes.TmTimes;
import ru.mirea.netelev.mireaproject.tmtimes.TmTimesDao;

@Database(entities = {Story.class, TmTimes.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StoryDao storyDao();
    public abstract TmTimesDao timesDao();
}