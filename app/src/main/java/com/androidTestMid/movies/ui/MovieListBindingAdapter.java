package com.androidTestMid.movies.ui;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidTestMid.movies.enumeration.SortedType;
import com.androidTestMid.movies.network.MovieFetcher;

import com.androidTestMid.movies.network.model.Movies;
import com.squareup.picasso.Picasso;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MovieListBindingAdapter {

    @BindingAdapter({"movie_fetcher", "movie_event_handler", "sorted_type"})
    public static void setListContent(final RecyclerView recyclerView, final MovieFetcher movieFetcher, final MovieEventHandler movieEventHandler,SortedType sortedType ) {
        if (movieFetcher != null && movieEventHandler != null ) {
            sortedType = sortedType == null ? SortedType.DEFAULT : sortedType;
            movieFetcher.setSortedType(sortedType);
            movieFetcher.getMovieAt(0).subscribe(new Consumer<Movies.Movie>() {
                @Override
                public void accept(@NonNull Movies.Movie movie) throws Exception {
                    if(recyclerView.getAdapter() == null) {
                        final MovieListAdapter movieListAdapter = new MovieListAdapter(movieFetcher, movieEventHandler);
                        recyclerView.setAdapter(movieListAdapter);
                    } else {
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }


    @BindingAdapter({"movie_event_handler", "movie"})
    public static void setOnCLickLinearLayout(LinearLayout lickLinearLayout, final MovieEventHandler movieEventHandler, final Movies.Movie movie) {
        if (movie != null && movieEventHandler != null) {
            lickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieEventHandler.onMovieClicked(movie);
                }
            });
        }
    }

    @BindingAdapter("image_url")
    public static void setImage(ImageView imageView, String url) {
        if (url != null) {
            Picasso.with(imageView.getContext()).load(url).into(imageView);
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView textView, String[] content) {
        if (content != null) {
            String concat = "";
            for (String item : content) {
                concat = concat + item + "\r\n";
            }
            textView.setText(concat);
        }
    }
}
