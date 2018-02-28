package com.androidTestMid.movies.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.androidTestMid.movies.R;
import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment {
    private String image_url;
    private ImageView imageView;

    public ScreenSlidePageFragment(String image_url) {
        this.image_url = image_url;
    }

    public static ScreenSlidePageFragment newInstance(String image_url) {

        return new ScreenSlidePageFragment(image_url);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

      imageView = (ImageView) rootView.findViewById(R.id.image_movie);
        if (this.image_url != null) {
            Picasso.with(imageView.getContext()).load(this.image_url).into(imageView);
        }
        return rootView;
    }


}