package com.androidTestMid.movies.ui;



import com.androidTestMid.movies.network.model.Movies;

public interface MovieEventHandler {
    void onMovieClicked(Movies.Movie movie);
}
