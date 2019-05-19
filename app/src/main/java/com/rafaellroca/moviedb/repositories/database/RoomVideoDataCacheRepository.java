package com.rafaellroca.moviedb.repositories.database;

import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.models.room.FilterEntity;
import com.rafaellroca.moviedb.models.room.FilterJoinVideoEntity;
import com.rafaellroca.moviedb.models.room.VideoDataEntity;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataCacheRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RoomVideoDataCacheRepository implements VideoDataCacheRepository {
    private VideoDataDao videoDataDao;
    private FilterJoinVideosDao filterJoinVideosDao;
    private FiltersDao filtersDao;

    @Inject
    RoomVideoDataCacheRepository(VideoDataDao videoDataDao, FiltersDao filtersDao, FilterJoinVideosDao filterJoinVideosDao) {
        this.videoDataDao = videoDataDao;
        this.filterJoinVideosDao = filterJoinVideosDao;
        this.filtersDao = filtersDao;
    }

    @Override
    public Observable<List<VideoData>> getVideosData(Filter filter) {
        return filterJoinVideosDao.getVideosForFilter(filter.toString()).toObservable().map(videoDataEntities -> videoDataEntities.stream()
                                                                                                                                  .map(videoDataEntity -> new VideoData(videoDataEntity
                                                                                                                                          .getId(),
                                                                                                                                          videoDataEntity
                                                                                                                                                  .getTitle(),
                                                                                                                                          videoDataEntity
                                                                                                                                                  .getImagePath(), videoDataEntity
                                                                                                                                          .getVoteCount(),
                                                                                                                                          videoDataEntity
                                                                                                                                                  .getVoteAverage(),
                                                                                                                                          videoDataEntity.getDescription()))
                                                                                                                                  .collect(Collectors.toList()));

    }

    @Override
    public Observable<List<VideoData>> searchVideosData(Filter filter, String searchKeywords) {
        return filterJoinVideosDao.searchVideosForFilter(filter.toString(), "%" + searchKeywords + "%")
                                  .toObservable()
                                  .map(videoDataEntities -> videoDataEntities.stream()
                                                                             .map(videoDataEntity -> new VideoData(videoDataEntity.getId(),
                                                                                     videoDataEntity.getTitle(),
                                                                                     videoDataEntity.getImagePath(), videoDataEntity.getVoteCount(),
                                                                                     videoDataEntity.getVoteAverage(), videoDataEntity.getDescription()))
                                                                             .collect(Collectors.toList()));
    }

    @Override
    public void insertVideosData(List<VideoData> videoDataList, Filter filter) {
        videoDataDao.insertList(videoDataList.stream()
                                             .map(videoData -> new VideoDataEntity(videoData.getId(), videoData.getTitle(),
                                                     videoData.getImagePath(), videoData.getVoteCount(), videoData.getVoteAverage(),
                                                     videoData.getDescription()))
                                             .collect(Collectors.toList()));

        filtersDao.insert(new FilterEntity(filter.toString()));

        filterJoinVideosDao.insertList(videoDataList.stream()
                                                    .map(videoData -> new FilterJoinVideoEntity(filter.toString(), videoData.getId()))
                                                    .collect(Collectors.toList()));

    }
}
