package com.rafaellroca.moviedb.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rafaellroca.moviedb.models.Filter;
import com.rafaellroca.moviedb.models.VideoData;
import com.rafaellroca.moviedb.repositories.interfaces.VideoDataRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends ViewModel {

    private MutableLiveData<List<VideoData>> videosDataLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();
    private VideoDataRepository videoDataRepository;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MainViewModel(VideoDataRepository videoDataRepository) {
        this.videoDataRepository = videoDataRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void requestVideosData(Filter filter) {
        compositeDisposable.add(videoDataRepository.getVideosData(filter).singleOrError().subscribeOn(Schedulers.io())
                                                   .observeOn(AndroidSchedulers.mainThread())
                                                   .subscribe(this::onListRetrieved, this::onError));
    }

    public void searchVideosData(Filter filter, String searchKeywords) {
        compositeDisposable.add(videoDataRepository.searchVideosData(filter, searchKeywords).singleOrError().subscribeOn(Schedulers.io())
                                                   .observeOn(AndroidSchedulers.mainThread())
                                                   .subscribe(this::onListRetrieved, this::onError));
    }

    public LiveData<List<VideoData>> getVideosDataLiveData() {
        return videosDataLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    private void onListRetrieved(List<VideoData> videos) {
        videosDataLiveData.setValue(videos);
    }

    private void onError(Throwable throwable) {
        getErrorLiveData().setValue(throwable);
    }

    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }
}
