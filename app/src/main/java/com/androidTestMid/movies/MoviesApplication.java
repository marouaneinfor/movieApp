package com.androidTestMid.movies;


import android.app.Application;

import com.androidTestMid.movies.dagger.AppComponent;
import com.androidTestMid.movies.dagger.AppModule;
import com.androidTestMid.movies.dagger.DaggerAppComponent;

public class MoviesApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent getComponent() {
        return appComponent;
    }
}
