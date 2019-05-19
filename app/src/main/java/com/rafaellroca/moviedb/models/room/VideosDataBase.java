package com.rafaellroca.moviedb.models.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rafaellroca.moviedb.repositories.database.FilterJoinVideosDao;
import com.rafaellroca.moviedb.repositories.database.FiltersDao;
import com.rafaellroca.moviedb.repositories.database.VideoDataDao;

@Database(entities = {VideoDataEntity.class, FilterEntity.class, FilterJoinVideoEntity.class}, version = 1, exportSchema = false)
public abstract class VideosDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "videos_db";

    public abstract VideoDataDao videoDataDao();
    public abstract FiltersDao filterDao();
    public abstract FilterJoinVideosDao filterJoinVideoDao();
}
