package com.rafaellroca.moviedb.repositories.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.rafaellroca.moviedb.models.room.FilterEntity;

@Dao
public interface FiltersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FilterEntity filterEntity);
}
