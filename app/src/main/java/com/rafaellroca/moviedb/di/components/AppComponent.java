package com.rafaellroca.moviedb.di.components;


import com.rafaellroca.moviedb.ApplicationClass;
import com.rafaellroca.moviedb.di.modules.CoreModule;
import com.rafaellroca.moviedb.di.modules.UIControllersModule;
import com.rafaellroca.moviedb.di.modules.ViewModelsModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Dagger application component.
 */
@Singleton
@Component(modules = {CoreModule.class, AndroidInjectionModule.class, UIControllersModule.class, AndroidSupportInjectionModule.class, ViewModelsModule.class})
public interface AppComponent extends AndroidInjector<ApplicationClass> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<ApplicationClass> {
    }
}
