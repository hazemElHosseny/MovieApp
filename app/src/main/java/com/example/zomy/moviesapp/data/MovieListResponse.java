package com.example.zomy.moviesapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by
 * ZoMy on 8/13/2016.
 */
public class MovieListResponse implements Serializable {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<MovieItem> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public ArrayList<MovieItem> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "MovieListResponse{" +
                "page= " + page +
                ", results= " + results +
                ", totalResults= " + totalResults +
                ", totalPages= " + totalPages +
                '}';
    }
}
