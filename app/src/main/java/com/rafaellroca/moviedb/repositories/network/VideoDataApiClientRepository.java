package com.rafaellroca.moviedb.repositories.network;

import android.content.Context;

import com.rafaellroca.moviedb.R;
import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataRepository;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import io.reactivex.Observable;

public class VideoDataApiClientRepository implements VideoDataRepository {

    public static final String NAMED_API_CLIENT_KEY = "api_client";
    private static final String URL_HOST_PATH = "https://image.tmdb.org/t/p/w500/";
    private static final Map<Filter.Category, String> CATEGORY_QUERY_STRINGS = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Filter.Category.POPULAR, "popular"),
            new AbstractMap.SimpleImmutableEntry<>(Filter.Category.TOP_RATED, "top_rated"),
            new AbstractMap.SimpleImmutableEntry<>(Filter.Category.UPCOMING, "upcoming")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private RetrofitVideoDataApiClientDao retrofitVideoDataApiClient;
    private final Context context;

    @Inject
    public VideoDataApiClientRepository(RetrofitVideoDataApiClientDao retrofitVideoDataApiClient, Context context) {
        this.retrofitVideoDataApiClient = retrofitVideoDataApiClient;
        this.context = context;
    }

    @Override
    public Observable<List<VideoData>> getVideosData(Filter filter) {
        switch (filter.getType()) {
            case MOVIE:
                return retrofitVideoDataApiClient.getMovieVideosData(CATEGORY_QUERY_STRINGS.get(filter.getCategory()),
                        context.getString(R.string.movie_db_api_key)).map(videoDataDTOS -> videoDataDTOS.getResults().stream()
                                                                                                        .map(videoDataDTO -> new VideoData(videoDataDTO
                                                                                                                .getId(),
                                                                                                                videoDataDTO.getTitle(),
                                                                                                                URL_HOST_PATH + videoDataDTO
                                                                                                                        .getBackdropPath(),
                                                                                                                videoDataDTO.getVoteCount(),
                                                                                                                videoDataDTO.getVoteAverage(),
                                                                                                                videoDataDTO.getOverview()))
                                                                                                        .collect(Collectors.toList()));
            case TV:
                return retrofitVideoDataApiClient.getTvVideosData(CATEGORY_QUERY_STRINGS.get(filter.getCategory()),
                        context.getString(R.string.movie_db_api_key)).map(videoDataDTOS -> videoDataDTOS.getResults().stream()
                                                                                                        .map(videoDataDTO -> new VideoData(videoDataDTO
                                                                                                                .getId(),
                                                                                                                videoDataDTO.getName(),
                                                                                                                URL_HOST_PATH + videoDataDTO
                                                                                                                        .getBackdropPath(),
                                                                                                                videoDataDTO.getVoteCount(),
                                                                                                                videoDataDTO.getVoteAverage(),
                                                                                                                videoDataDTO.getOverview()))
                                                                                                        .collect(Collectors.toList()));

            default:
                return null;
        }
    }

    @Override
    public Observable<List<VideoData>> searchVideosData(Filter filter, String searchKeywords) {
        // TODO: Enable Search from Rest api.
        return null;
    }
}
