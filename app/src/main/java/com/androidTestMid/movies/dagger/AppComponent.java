package com.androidTestMid.movies.dagger;

import javax.inject.Singleton;

import com.androidTestMid.movies.ui.MovieListFragment;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MovieListFragment fragement);
}