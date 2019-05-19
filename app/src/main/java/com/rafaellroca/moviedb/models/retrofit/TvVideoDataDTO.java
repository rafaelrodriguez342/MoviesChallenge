package com.rafaellroca.moviedb.models.retrofit;

public class TvVideoDataDTO extends VideoDataDTO {
    private String first_air_date;
    private String name;
    private String original_name;

    public String getFirstAirDate() {
        return first_air_date;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return original_name;
    }
}
