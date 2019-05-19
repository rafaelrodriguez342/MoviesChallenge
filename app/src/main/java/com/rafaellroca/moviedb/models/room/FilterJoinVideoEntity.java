package com.rafaellroca.moviedb.models.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "filter_video_join",
        primaryKeys = {"videoId", "filterId"},
        indices = {@Index("filterId")},
        foreignKeys = {
                @ForeignKey(entity = FilterEntity.class,
                        parentColumns = "id",
                        childColumns = "filterId"),
                @ForeignKey(entity = VideoDataEntity.class,
                        parentColumns = "id",
                        childColumns = "videoId")
        })
public class FilterJoinVideoEntity {
    @NonNull
    private String filterId;
    @NonNull
    @ColumnInfo(index = true)
    private String videoId;

    public FilterJoinVideoEntity(@NonNull String filterId, @NonNull String videoId) {
        this.filterId = filterId;
        this.videoId = videoId;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
