package com.androidTestMid.movies.ui;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidTestMid.movies.databinding.ListItemMovieBinding;
import com.androidTestMid.movies.network.MovieFetcher;
import com.androidTestMid.movies.network.model.Movies;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import com.androidTestMid.movies.R;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private MovieFetcher movieFetcher;
    private MovieEventHandler movieEventHandler;

    public MovieListAdapter(MovieFetcher movieFetcher, MovieEventHandler movieEventHandler) {
        this.movieFetcher = movieFetcher;
        this.movieEventHandler = movieEventHandler;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemMovieBinding binding;
        private Disposable disposable;

        public ViewHolder(ListItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Observable<Movies.Movie> movie, final MovieEventHandler movieEventHandler) {
            disposable = movie.subscribe(new Consumer<Movies.Movie>() {
                @Override
                public void accept(@NonNull Movies.Movie movie) throws Exception {
                    binding.setMovie(movie);
                    binding.setMovieEventHandler(movieEventHandler);
                    binding.executePendingBindings();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
        }

        public void cleanup() {
            disposable.dispose();
        }

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanup();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemMovieBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_movie, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(movieFetcher.getMovieAt(position), movieEventHandler);
    }

    @Override
    public int getItemCount() {
        return movieFetcher.countMovies();
    }


}
