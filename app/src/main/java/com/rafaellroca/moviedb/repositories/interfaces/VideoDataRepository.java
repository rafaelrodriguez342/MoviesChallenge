package com.rafaellroca.moviedb.repositories.interfaces;

import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;

import java.util.List;

import io.reactivex.Observable;

public interface VideoDataRepository {

    Observable<List<VideoData>> getVideosData(Filter filter);

    Observable<List<VideoData>> searchVideosData(Filter filter, String searchKeywords);
}