package com.example.zomy.moviesapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by
 * ZoMy on 8/20/2016.
 */
public class MovieDetails implements Serializable{
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("budget")
    private long budget;
    @SerializedName("genres")
    private ArrayList<Genre> genres;
    @SerializedName("id")
    private int id;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("imdb_id")
    private String imdbID;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_Title")
    private String originalTitle;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("revenue")
    private String revenue;
    @SerializedName("runtime")
    private String runtime;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("title")
    private String title;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("trailers")
    private Trailer trailers;

    public class Trailer implements Serializable{
        @SerializedName("youtube")
        private ArrayList<Youtube> youtubes;

        public ArrayList<Youtube> getYoutubes() {
            return youtubes;
        }

        public class Youtube implements Serializable{
            @SerializedName("name")
            private String name;
            @SerializedName("size")
            private String size;
            @SerializedName("source")
            private String source;
            @SerializedName("type")
            private String type;

            public String getName() {
                return name;
            }

            public String getSize() {
                return size;
            }

            public String getSource() {
                return source;
            }

            public String getType() {
                return type;
            }

            @Override
            public String toString() {
                return "Youtube{" +
                        "name='" + name + '\'' +
                        ", size='" + size + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Trailer{" +
                    "youtubes=" + youtubes +
                    '}';
        }
    }

    private class Genre implements Serializable{
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Genre{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public long getBudget() {
        return budget;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public Trailer getTrailers() {
        return trailers;
    }

    @Override
    public String toString() {
        return "MovieDetails{" +
                "adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", id=" + id +
                ", homepage='" + homepage + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate=" + releaseDate +
                ", revenue='" + revenue + '\'' +
                ", runtime='" + runtime + '\'' +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", voteCount='" + voteCount + '\'' +
                ", trailers=" + trailers +
                '}';
    }
}
