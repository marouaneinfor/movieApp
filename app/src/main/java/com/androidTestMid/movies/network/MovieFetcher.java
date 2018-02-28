package com.androidTestMid.movies.network;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import com.androidTestMid.movies.enumeration.SortedType;
import com.androidTestMid.movies.network.model.Movies;
import com.androidTestMid.movies.network.service.MoviesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class MovieFetcher {

    private MoviesService moviesService;
    private int numberMovies = 0;
    private SortedType sortedType ;

    private final List<BehaviorSubject<Movies>> pageIndexRequestCache = new ArrayList<>();

    @Inject
    public MovieFetcher(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    public void setSortedType(SortedType sortedType) {
        this.sortedType = sortedType;
        pageIndexRequestCache.clear();
    }

    public Observable<Movies.Movie> getMovieAt(final int position) {
        Observable<Movies> movieObservable;
        if (position < pageIndexRequestCache.size()) {
            movieObservable = pageIndexRequestCache.get(position);
        } else {
            final BehaviorSubject<Movies> movieSubject = BehaviorSubject.create();
            movieObservable = moviesService.fetchMovies()
                    .doOnNext(new Consumer<Movies>() {
                        @Override
                        public void accept(@NonNull Movies movies) throws Exception {
                            movieSubject.onNext(movies);
                        }
                    }).subscribeOn(Schedulers.io());
            pageIndexRequestCache.add(movieSubject);
        }
        return movieObservable
                .map(new Function<Movies, Movies.Movie>() {
                    @Override
                    public Movies.Movie apply(@NonNull Movies movies) throws Exception {
                        final List<Movies.Movie> movieList = Arrays.asList(movies.getItems());
                        if (!sortedType.equals(SortedType.DEFAULT)) {
                            Collections.sort(movieList, new Comparator<Movies.Movie>() {
                                public int compare(Movies.Movie m1, Movies.Movie m2) {
                                    return sortedType.equals(SortedType.TITLE) ? m1.getTitle().compareTo(m2.getTitle())
                                            : new Integer(m1.getYear()).compareTo(m2.getYear()) ;
                                }
                            });
                        }
                        MovieFetcher.this.numberMovies = movies.getItems().length;
                        return movieList.get(position);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public int countMovies() {
        return numberMovies;
    }
}
