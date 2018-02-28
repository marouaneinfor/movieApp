package com.androidTestMid.movies.viewmodel;

import android.databinding.ObservableField;

import javax.inject.Inject;

import com.androidTestMid.movies.enumeration.SortedType;
import com.androidTestMid.movies.network.MovieFetcher;

public class MovieListViewModel {

    private ObservableField<MovieFetcher> movieFetcher = new ObservableField<>();
    private ObservableField<SortedType> sortedType = new ObservableField<>();

    @Inject
    public MovieListViewModel(MovieFetcher movieFetcher) {
        this.movieFetcher.set(movieFetcher);
    }

    public ObservableField<MovieFetcher> getMovieFetcher() {
        return movieFetcher;
    }

    public void setSortedBy(SortedType type) {
        this.sortedType.set(type);
    }
    public ObservableField<SortedType> getSortedType() {
        return sortedType;
    }
}