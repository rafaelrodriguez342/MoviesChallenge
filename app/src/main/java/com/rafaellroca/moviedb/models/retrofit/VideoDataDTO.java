package com.rafaellroca.moviedb.models.retrofit;

import java.util.ArrayList;

public class VideoDataDTO {
    private String vote_count;
    private String id;
    private String vote_average;
    private String poster_path;
    private String popularity;
    private String original_language;
    private ArrayList<String> genre_ids;
    private String backdrop_path;
    private String overview;

    public String getVoteCount() {
        return vote_count;
    }

    public String getId() {
        return id;
    }

    public String getVoteAverage() {
        return vote_average;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public ArrayList<String> getGenreIds() {
        return genre_ids;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

}
