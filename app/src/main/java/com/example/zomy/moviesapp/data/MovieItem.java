package com.example.zomy.moviesapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by
 * ZoMy on 8/13/2016.
 */
public class MovieItem implements Serializable{
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "posterPath= '" + posterPath + '\'' +
                ", adult= " + adult +
                ", overview= '" + overview + '\'' +
                ", releaseDate= " + releaseDate +
                ", id= " + id +
                ", originalTitle= '" + originalTitle + '\'' +
                ", originalLanguage= '" + originalLanguage + '\'' +
                ", title= '" + title + '\'' +
                ", backdropPath= '" + backdropPath + '\'' +
                ", popularity= " + popularity +
                ", voteCount= " + voteCount +
                ", video= " + video +
                ", voteAverage= " + voteAverage +
                '}';
    }
}
