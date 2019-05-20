package com.rafaellroca.moviedb.models.retrofit;

public class MovieVideoDataDTO extends VideoDataDTO {


    private Boolean video;
    private String title;
    private String original_title;
    private boolean adult;
    private String release_date;


    public Boolean getVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getReleaseDate() {
        return release_date;
    }
}
