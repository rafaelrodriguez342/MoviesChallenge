package com.rafaellroca.moviedb.repositories.network;

import android.content.Context;

import com.rafaellroca.moviedb.R;
import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataCacheRepository;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataRepository;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import io.reactivex.Observable;

public class VideoDataCacheApiClient implements VideoDataRepository {
    private RetrofitApiClient retrofitApiClient;
    private VideoDataCacheRepository videoDataCacheRepository;
    private final Context context;

    private static final Map<Filter.Category, String> CATEGORY_QUERY_STRINGS = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Filter.Category.POPULAR, "popular"),
            new AbstractMap.SimpleImmutableEntry<>(Filter.Category.TOP_RATED, "top_rated"),
            new AbstractMap.SimpleImmutableEntry<>(Filter.Category.UPCOMING, "upcoming")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Inject
    public VideoDataCacheApiClient(RetrofitApiClient retrofitApiClient, Context context, VideoDataCacheRepository videoDataCacheRepository) {
        this.retrofitApiClient = retrofitApiClient;
        this.videoDataCacheRepository = videoDataCacheRepository;
        this.context = context;
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
        switch (filter.getType()) {
            case MOVIE:
                return retrofitApiClient.getMovieVideosData(CATEGORY_QUERY_STRINGS.get(filter.getCategory()),
                        context.getString(R.string.movie_db_api_key)).map(videoDataDTOS -> videoDataDTOS.getResults().stream()
                                                                                                        .map(videoDataDTO -> new VideoData(videoDataDTO
                                                                                                                .getId(),
                                                                                                                videoDataDTO.getTitle(),
                                                                                                                "https://image.tmdb.org/t/p/w500/" + videoDataDTO
                                                                                                                        .getBackdropPath(),
                                                                                                                videoDataDTO.getVoteCount(),
                                                                                                                videoDataDTO.getVoteAverage(),
                                                                                                                videoDataDTO.getOverview()))
                                                                                                        .collect(Collectors.toList()));
            case TV:
                return retrofitApiClient.getTvVideosData(CATEGORY_QUERY_STRINGS.get(filter.getCategory()),
                        context.getString(R.string.movie_db_api_key)).map(videoDataDTOS -> videoDataDTOS.getResults().stream()
                                                                                                        .map(videoDataDTO -> new VideoData(videoDataDTO
                                                                                                                .getId(),
                                                                                                                videoDataDTO.getName(),
                                                                                                                "https://image.tmdb.org/t/p/w500/" + videoDataDTO
                                                                                                                        .getBackdropPath(),
                                                                                                                videoDataDTO.getVoteCount(),
                                                                                                                videoDataDTO.getVoteAverage(),
                                                                                                                videoDataDTO.getOverview()))
                                                                                                        .collect(Collectors.toList()));

            default:
                return null;
        }

    }

    private Observable<List<VideoData>> getVideosFromDb(Filter filter) {
        return videoDataCacheRepository.getVideosData(filter);
    }
}
