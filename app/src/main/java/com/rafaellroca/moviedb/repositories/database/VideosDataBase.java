package com.rafaellroca.moviedb.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rafaellroca.moviedb.models.room.FilterEntity;
import com.rafaellroca.moviedb.models.room.FilterJoinVideoEntity;
import com.rafaellroca.moviedb.models.room.VideoDataEntity;

@Database(entities = {VideoDataEntity.class, FilterEntity.class, FilterJoinVideoEntity.class}, version = 1, exportSchema = false)
public abstract class VideosDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "videos_db";

    public abstract VideoDataDao videoDataDao();
    public abstract FiltersDao filterDao();
    public abstract FilterJoinVideosDao filterJoinVideoDao();
}
