package com.rafaellroca.moviedb;

import com.rafaellroca.moviedb.di.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.HasActivityInjector;

/**
 * The Application class.
 */
public class ApplicationClass extends DaggerApplication implements HasActivityInjector {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
