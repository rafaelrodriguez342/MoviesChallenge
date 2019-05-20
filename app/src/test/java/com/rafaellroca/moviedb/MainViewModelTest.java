package com.rafaellroca.moviedb;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.models.retrofit.MovieVideoDataDTO;
import com.rafaellroca.moviedb.models.retrofit.VideoDataListResponseDTO;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataRepository;
import com.rafaellroca.moviedb.viewmodels.MainViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainViewModelTest {
    private static final String TEST_VIDEOS_PATH = "videos.json";

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    VideoDataRepository videoRepository;

    private MainViewModel mainViewModel;

    @Before
    public void setup() {
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        MockitoAnnotations.initMocks(this);
        mainViewModel = new MainViewModel(videoRepository);
    }

    @Test
    public void testRequestVideos() throws IOException {
        final List<VideoData> videosResponse = new ArrayList<>();
        videosResponse.addAll(getTestVideos(TEST_VIDEOS_PATH));

        final Filter testFilter = new Filter(Filter.Category.POPULAR, Filter.Type.MOVIE);
        when(videoRepository.getVideosData(testFilter)).thenReturn(Observable.just(videosResponse));
        Observer<List<VideoData>> listObserver = videos -> {
            assertNotNull(videos);
            assertEquals(20, videos.size());
        };

        mainViewModel.getVideosDataLiveData().observeForever(listObserver);
        mainViewModel.requestVideosData(testFilter);
        verify(videoRepository).getVideosData(testFilter);
    }

    @Test
    public void testSearchVideos() {
        final Filter testFilter = new Filter(Filter.Category.TOP_RATED, Filter.Type.MOVIE);
        final List<VideoData> videosResponse = new ArrayList<>();
        videosResponse.add(new VideoData("fakeId123", "Captain Marvel", "https://imagetest.com", "890", "8.5", "testDescription"));
        final String searchKeywords = "captain";
        when(videoRepository.searchVideosData(testFilter, searchKeywords)).thenReturn(Observable.just(videosResponse));
        Observer<List<VideoData>> listObserver = videos -> {
            assertNotNull(videos);
            assertEquals(1, videos.size());
        };

        mainViewModel.getVideosDataLiveData().observeForever(listObserver);
        mainViewModel.searchVideosData(testFilter, searchKeywords);
        verify(videoRepository).searchVideosData(testFilter, searchKeywords);
    }

    private List<VideoData> getTestVideos(String fileName) throws IOException {
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<VideoDataListResponseDTO<MovieVideoDataDTO>>() {
        }.getType();
        VideoDataListResponseDTO<MovieVideoDataDTO> responseDTO = gson.fromJson(SampleDataHelper.loadSampleDataFromLocalJSON(fileName),
                type);
        return responseDTO.getResults()
                          .stream()
                          .map(movieVideoDataDTO -> new VideoData(movieVideoDataDTO.getId(),
                                  movieVideoDataDTO.getTitle(), movieVideoDataDTO
                                  .getBackdropPath(),
                                  movieVideoDataDTO.getVoteCount(), movieVideoDataDTO.getVoteAverage(),
                                  movieVideoDataDTO
                                          .getOverview()))
                          .collect(Collectors.toList());
    }
    // TODO: add more unit tests for error cases and other scenarios.
}
