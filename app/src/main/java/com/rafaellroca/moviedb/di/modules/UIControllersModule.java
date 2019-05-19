package com.rafaellroca.moviedb.di.modules;

import com.rafaellroca.moviedb.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module to declare activities to be injected.
 */
@Module
public abstract class UIControllersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
