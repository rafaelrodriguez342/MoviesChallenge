package com.rafaellroca.moviedb.di.modules;

import android.arch.lifecycle.ViewModel;

import com.rafaellroca.moviedb.annotations.ViewModelKey;
import com.rafaellroca.moviedb.viewmodels.MainViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

import static com.rafaellroca.moviedb.ApplicationViewModelFactory.VIEW_MODELS_HASH;

/**
 * Module to declare available view models.
 */
@Module
public class ViewModelsModule {

    @Provides
    @IntoMap
    @Named(VIEW_MODELS_HASH)
    @ViewModelKey(MainViewModel.class)
    ViewModel bindMainViewModel(MainViewModel mainViewModel) {
        return mainViewModel;
    }
}
