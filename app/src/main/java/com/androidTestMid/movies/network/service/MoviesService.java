package com.androidTestMid.movies.network.service;


import com.androidTestMid.movies.network.model.Movies;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoviesService {
    @GET("v2/5a8090e42f00001300a4c139")
    Observable<Movies> fetchMovies();
}