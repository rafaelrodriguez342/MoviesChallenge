package com.rafaellroca.moviedb.repositories.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.rafaellroca.moviedb.models.room.FilterJoinVideoEntity;
import com.rafaellroca.moviedb.models.room.VideoDataEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FilterJoinVideosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertList(List<FilterJoinVideoEntity> filterJoinVideoEntity);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM video_data INNER JOIN filter_video_join ON video_data.id=filter_video_join.videoId WHERE filter_video_join" +
            ".filterId=:filterId")
    Single<List<VideoDataEntity>> getVideosForFilter(final String filterId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM video_data INNER JOIN filter_video_join ON video_data.id=filter_video_join.videoId WHERE filter_video_join" +
            ".filterId=:filterId AND video_data.title LIKE :keywords")
    Single<List<VideoDataEntity>> searchVideosForFilter(final String filterId, final String keywords);
}
