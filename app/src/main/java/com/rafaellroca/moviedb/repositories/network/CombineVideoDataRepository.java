package com.rafaellroca.moviedb.repositories.network;

import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataCacheRepository;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;

import static com.rafaellroca.moviedb.repositories.network.VideoDataApiClientRepository.NAMED_API_CLIENT_KEY;

public class CombineVideoDataRepository implements VideoDataRepository {
    private VideoDataCacheRepository videoDataCacheRepository;
    private VideoDataRepository apiClientRepository;

    @Inject
    public CombineVideoDataRepository(@Named(NAMED_API_CLIENT_KEY) VideoDataRepository apiClientRepository, VideoDataCacheRepository videoDataCacheRepository) {
        this.apiClientRepository = apiClientRepository;
        this.videoDataCacheRepository = videoDataCacheRepository;
    }

    @Override
    public Observable<List<VideoData>> getVideosData(Filter filter) {
        return getVideosFromApi(filter)
                .doOnNext(videoDataList -> videoDataCacheRepository.insertVideosData(videoDataList, filter))
                .onErrorResumeNext(getVideosFromDb(filter));
    }

    @Override
    public Observable<List<VideoData>> searchVideosData(Filter filter, String searchKeywords) {
        return videoDataCacheRepository.searchVideosData(filter, searchKeywords);
    }

    private Observable<List<VideoData>> getVideosFromApi(Filter filter) {
        return apiClientRepository.getVideosData(filter);
    }

    private Observable<List<VideoData>> getVideosFromDb(Filter filter) {
        return videoDataCacheRepository.getVideosData(filter);
    }
}
