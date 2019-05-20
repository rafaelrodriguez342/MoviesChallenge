package com.rafaellroca.moviedb.repositories.network;

import com.rafaellroca.moviedb.models.retrofit.MovieVideoDataDTO;
import com.rafaellroca.moviedb.models.retrofit.TvVideoDataDTO;
import com.rafaellroca.moviedb.models.retrofit.VideoDataListResponseDTO;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitVideoDataApiClientDao {
    @GET("/3/movie/{category}")
    Observable<VideoDataListResponseDTO<MovieVideoDataDTO>> getMovieVideosData(@Path("category") String category, @Query("api_key") String apiKey);
    @GET("/3/tv/{category}")
    Observable<VideoDataListResponseDTO<TvVideoDataDTO>> getTvVideosData(@Path("category") String category, @Query("api_key") String apiKey);
}
