package com.rafaellroca.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoData implements Parcelable {
    private String id;
    private String title;
    private String imagePath;
    private String voteCount;
    private String voteAverage;
    private String description;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public VideoData createFromParcel(Parcel source) {
            return new VideoData(source);
        }

        @Override
        public VideoData[] newArray(int size) {
            return new VideoData[size];
        }
    };

    public VideoData(String id, String title, String imagePath, String voteCount, String voteAverage, String description) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.description = description;
    }

    public VideoData(Parcel source) {
        this.id = source.readString();
        this.title = source.readString();
        this.imagePath = source.readString();
        this.voteCount = source.readString();
        this.voteAverage = source.readString();
        this.description = source.readString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imagePath);
        dest.writeString(this.voteCount);
        dest.writeString(this.voteAverage);
        dest.writeString(this.description);
    }
}
