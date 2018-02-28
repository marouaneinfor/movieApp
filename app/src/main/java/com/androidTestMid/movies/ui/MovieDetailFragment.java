package com.androidTestMid.movies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.androidTestMid.movies.R;
import com.androidTestMid.movies.databinding.FragmentMovieDetailBinding;
import com.androidTestMid.movies.network.model.Movies;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class MovieDetailFragment extends Fragment {

    private static final String ARG_MOVIE = "movie";

    private ViewPagerCustomDuration mPager;
    private static int page = 0;

    private ScreenSlidePagerAdapter mPagerAdapter;
    private static final long INTERVAL_DELAY = 3;
    private Movies.Movie movie;
    private FragmentMovieDetailBinding fragmentMovieDetailBinding;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    public static MovieDetailFragment newInstance(Movies.Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
        setHasOptionsMenu(true);
        ((MainActivity) getActivity())
                .setActionBarTitle(movie.getTitle() + " (" + movie.getYear() + ")");
        ((MainActivity) getActivity()).setBackButton(true);

    }


    @Override
    public void onDetach() {
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.app_name));
        ((MainActivity) getActivity()).setBackButton(false);

        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentMovieDetailBinding.setMovie(movie);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentMovieDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        return fragmentMovieDetailBinding.getRoot();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPager = (ViewPagerCustomDuration) getView().findViewById(R.id.pager);
        mPager.setScrollDurationFactor(40); // make the animation twice as slow
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), movie.getImages());
        mPager.setAdapter(mPagerAdapter);
        startPagerAutoPlay();
    }    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }
   private void startPagerAutoPlay() {
        if (mPager != null) {
            Observable
                    .interval(INTERVAL_DELAY, TimeUnit.SECONDS)
                    .take(movie.getImages().size())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Long aLong) {
                            if (mPagerAdapter != null && page < mPagerAdapter.getCount()) {
                                mPager.setCurrentItem(page++);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<String> images;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<String> images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            return   ScreenSlidePageFragment.newInstance(images.get(position));

        }



        @Override
        public int getCount() {
            return movie.getImages().size();
        }

    }
}
