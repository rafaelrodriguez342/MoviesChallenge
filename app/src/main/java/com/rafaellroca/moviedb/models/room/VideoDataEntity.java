package com.rafaellroca.moviedb.models.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.rafaellroca.moviedb.models.room.VideoDataEntity.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class VideoDataEntity {
    static final String TABLE_NAME = "video_data";
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String imagePath;
    private String voteCount;
    private String voteAverage;
    private String description;
    private String type;

    public VideoDataEntity(@NonNull String id, String title, String imagePath, String voteCount, String voteAverage, String description) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
