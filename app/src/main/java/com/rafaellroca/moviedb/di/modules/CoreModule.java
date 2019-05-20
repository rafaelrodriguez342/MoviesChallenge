package com.rafaellroca.moviedb.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;


import com.rafaellroca.moviedb.ApplicationClass;
import com.rafaellroca.moviedb.ApplicationViewModelFactory;
import com.rafaellroca.moviedb.models.room.VideosDataBase;
import com.rafaellroca.moviedb.repositories.database.FilterJoinVideosDao;
import com.rafaellroca.moviedb.repositories.database.FiltersDao;
import com.rafaellroca.moviedb.repositories.database.RoomVideoDataCacheRepository;
import com.rafaellroca.moviedb.repositories.database.VideoDataDao;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataCacheRepository;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataRepository;
import com.rafaellroca.moviedb.repositories.network.RetrofitApiClient;
import com.rafaellroca.moviedb.repositories.network.VideoDataRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module to provide general application dependencies.
 */
@Module
public class CoreModule {

    @Provides
    Context provideApplicationContext(ApplicationClass applicationClass) {
        return applicationClass;
    }

    @Provides
    @Singleton
    public RetrofitApiClient provideVideosRetrofitApiClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        return retrofit.create(RetrofitApiClient.class);
    }

    @Provides
    @Singleton
    public ViewModelProvider.Factory provideViewModelFactory(ApplicationViewModelFactory applicationViewModelFactory) {
        return applicationViewModelFactory;
    }

    @Provides
    @Singleton
    public VideoDataRepository provideVideosDataRepository(VideoDataRepositoryImpl videoDataRepositoryImpl) {
        return videoDataRepositoryImpl;
    }

    @Provides
    @Singleton
    public VideoDataCacheRepository provideVideosDataCacheRepository(RoomVideoDataCacheRepository roomVideoDataCacheRepository) {
        return roomVideoDataCacheRepository;
    }

    @Singleton
    @Provides
    public VideosDataBase provideVideosDatabase(Context context) {
        return Room.databaseBuilder(context,
                VideosDataBase.class, VideosDataBase.DATABASE_NAME)
                   .fallbackToDestructiveMigration()
                   .build();
    }

    @Singleton
    @Provides
    public VideoDataDao provideShowDao(VideosDataBase videosDataBase) {
        return videosDataBase.videoDataDao();
    }

    @Singleton
    @Provides
    public FilterJoinVideosDao provideJoinVideoDao(VideosDataBase videosDataBase) {
        return videosDataBase.filterJoinVideoDao();
    }

    @Singleton
    @Provides
    public FiltersDao provideFiltersDao(VideosDataBase videosDataBase) {
        return videosDataBase.filterDao();
    }
}

