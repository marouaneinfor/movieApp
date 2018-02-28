package com.androidTestMid.movies.network.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;

public class Movies {
    public static final class Movie implements Parcelable {

        protected Movie(Parcel in) {
            images = in.createStringArrayList();
            title = in.readString();
            intro = in.readString();
            year = in.readInt();
            text = in.readString();
        }

        public static final Creator<Movie> CREATOR = new Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringList(images);
            dest.writeString(title);
            dest.writeString(intro);
            dest.writeInt(year);
            dest.writeString(text);

        }

        private List<String> images;
        private String title;
        private String intro;
        private int year;
        private String text;


        public String getTitle() {
            return title;
        }

        public String getIntro() {
            return intro;
        }

        public int getYear() {
            return year;
        }

        public String getText() {
            return text;
        }

        public List<String> getImages() {
            return Collections.unmodifiableList(images);
        }


    }

    private Movie[] items;

    public Movie[] getItems() {
        return items;
    }

}
