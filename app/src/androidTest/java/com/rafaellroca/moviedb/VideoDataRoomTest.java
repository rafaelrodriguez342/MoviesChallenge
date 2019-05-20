package com.rafaellroca.moviedb;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.room.FilterEntity;
import com.rafaellroca.moviedb.models.room.FilterJoinVideoEntity;
import com.rafaellroca.moviedb.models.room.VideoDataEntity;
import com.rafaellroca.moviedb.repositories.database.VideosDataBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;

@RunWith(AndroidJUnit4.class)
public class VideoDataRoomTest {

    private VideosDataBase videoDatabase;

    @Before
    public void setup() {
        videoDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), VideosDataBase.class).build();
    }

    @After
    public void closeDb() {
        videoDatabase.close();
    }

    @Test
    public void testVideoStateRetrieveAndInsertion() {
        final String searchString = "Captain Marvel";
        final Filter testFilter = new Filter(Filter.Category.POPULAR, Filter.Type.MOVIE);
        final String filterId = testFilter.toString();
        final String videoId1 = "fakeId123";
        final String videoId2 = "test456";
        VideoDataEntity videoDataEntity1 = new VideoDataEntity(videoId1, "Captain Marvel", "https://imagetest.com", "890", "8.5",
                "testDescription");

        VideoDataEntity videoDataEntity2 = new VideoDataEntity(videoId2, "Avengers End game", "https://imagetest.com", "2130", "9",
                "testDescription2");

        List<VideoDataEntity> videoTestList = new ArrayList<>();
        videoTestList.add(videoDataEntity1);
        videoTestList.add(videoDataEntity2);

        videoDatabase.videoDataDao().insertList(videoTestList);
        videoDatabase.filterDao().insert(new FilterEntity(filterId));
        videoDatabase.filterJoinVideoDao().insertList(videoTestList.stream()
                                                                   .map(videoData -> new FilterJoinVideoEntity(filterId,
                                                                           videoData.getId()))
                                                                   .collect(Collectors.toList()));

        Single<List<VideoDataEntity>> videoListObserver = videoDatabase.filterJoinVideoDao().getVideosForFilter(filterId);
        Single<List<VideoDataEntity>> searchListObserver = videoDatabase.filterJoinVideoDao().searchVideosForFilter(filterId,
                searchString);

        videoListObserver.test().assertValueAt(0, videoDataEntities -> videoDataEntities.size() == 2 && videoDataEntities.get(0)
                                                                                                                         .getId()
                                                                                                                         .equals(videoId1) && videoDataEntities
                .get(1)
                .getId()
                .equals(videoId2));

        searchListObserver.test().assertValueAt(0,
                videoDataEntities -> videoDataEntities.size() == 1 && videoDataEntities.get(0).getId().equals(videoId1));
    }
}
