package com.rafaellroca.moviedb.repositories.interfaces;

import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;

import java.util.List;

public interface VideoDataCacheRepository extends VideoDataRepository {

    void insertVideosData(List<VideoData> videoDataList, Filter filter);
}
