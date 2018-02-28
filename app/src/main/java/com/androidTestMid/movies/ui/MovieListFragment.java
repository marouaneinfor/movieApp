package com.androidTestMid.movies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import com.androidTestMid.movies.MoviesApplication;

import com.androidTestMid.movies.R;
import com.androidTestMid.movies.databinding.FragmentMovieListBinding;
import com.androidTestMid.movies.enumeration.SortedType;
import com.androidTestMid.movies.network.model.Movies;
import com.androidTestMid.movies.viewmodel.MovieListViewModel;


public class MovieListFragment extends Fragment implements MovieEventHandler {
    FragmentMovieListBinding movieListBindingAdapter;

    @Inject
    MovieListViewModel movieListViewModel;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MoviesApplication) getActivity().getApplication()).getComponent().inject(this);
        movieListBindingAdapter.setMovieEventHandler(this);
        movieListBindingAdapter.setMovieListViewModel(movieListViewModel);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movieListBindingAdapter = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);
        setHasOptionsMenu(true);

        return movieListBindingAdapter.getRoot();
    }


    @Override
    public void onMovieClicked(Movies.Movie movie) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(movie.getTitle())
                .add(R.id.fragment_container, MovieDetailFragment.newInstance(movie))
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sortByYear) {
            movieListViewModel.setSortedBy(SortedType.YEAR);
            return true;
        } else if (item.getItemId() == R.id.action_sortByTitle) {
            movieListViewModel.setSortedBy(SortedType.TITLE);
            return true;
        } else if (item.getItemId() == R.id.action_sortByDefault) {
            movieListViewModel.setSortedBy(SortedType.DEFAULT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    }
