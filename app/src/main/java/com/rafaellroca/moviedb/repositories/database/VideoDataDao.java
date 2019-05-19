package com.rafaellroca.moviedb.repositories.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.rafaellroca.moviedb.models.room.VideoDataEntity;

import java.util.List;

@Dao
public interface VideoDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<VideoDataEntity> videoData);
}
