package ru.mirea.netelev.mireaproject.story;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Story.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StoryDao storyDao();
}