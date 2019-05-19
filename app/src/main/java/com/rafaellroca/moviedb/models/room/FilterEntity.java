package com.rafaellroca.moviedb.models.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "filter")
public class FilterEntity {
    @NonNull
    @PrimaryKey
    private String id;

    public FilterEntity(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
