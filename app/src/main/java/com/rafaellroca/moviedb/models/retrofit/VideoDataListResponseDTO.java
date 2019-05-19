package com.rafaellroca.moviedb.models.retrofit;

import java.util.List;

public class VideoDataListResponseDTO<T> {

    private List<T> results;

    public List<T> getResults() {
        return results;
    }
}
