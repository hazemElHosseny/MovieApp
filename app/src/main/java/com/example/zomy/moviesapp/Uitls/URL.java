package com.example.zomy.moviesapp.Uitls;

import android.net.Uri;
import android.util.Log;

/**
 * Created by
 * ZoMy on 8/13/2016.
 */
public class URL {
    private static final String API_KEY_VALUE = "4bd3dc7edbd1fed6f43443fb9f2ebc1f";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String MOVIE = "movie";
    private static final String PAGE = "page";
    private static final String API_KEY = "api_key";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String YOUTUBE_IMAGE_BASE_URL = "http://img.youtube.com/vi/";
    private static final String YOUTUBE_VIDEO_BASE_URL = "http://www.youtube.com/watch";

    public static String getMostPopularMoviesURL(int pageNo){
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath(MOVIE).appendPath(POPULAR).appendQueryParameter(API_KEY,API_KEY_VALUE)
                .appendQueryParameter(PAGE,String.valueOf(pageNo));
        return builder.build().toString();
    }

    public static String getMovieData(int movieID){
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath(MOVIE).appendPath(String.valueOf(movieID))
                .appendQueryParameter(API_KEY,API_KEY_VALUE)
                .appendQueryParameter("append_to_response","trailers");
        return builder.build().toString();
    }

    public static String getTopRatedMoviesURL(int pageNo){
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath(MOVIE).appendPath(TOP_RATED).appendQueryParameter(API_KEY,API_KEY_VALUE)
                .appendQueryParameter(PAGE,String.valueOf(pageNo));
        return builder.build().toString();
    }

    public static String getImageURL(String imageSize, String imagePath){
        imagePath = imagePath.substring(1);
        Uri.Builder builder = Uri.parse(IMAGE_BASE_URL).buildUpon();
        builder.appendPath(imageSize).appendPath(imagePath);
        Log.d("hazem", "getImageURL: " + builder.build().toString());
        return builder.build().toString();
    }

    public static String getYoutubeImageURL(String imagePath){
        Uri.Builder builder = Uri.parse(YOUTUBE_IMAGE_BASE_URL).buildUpon();
        builder.appendPath(imagePath).appendPath("0.jpg");
        Log.d("hazem", "getYoutubeImageURL: " + builder.build().toString());
        return builder.build().toString();
    }

    public static String getYoutubeVideoURL(String videoPath){
        Uri.Builder builder = Uri.parse(YOUTUBE_VIDEO_BASE_URL).buildUpon();
        builder.appendQueryParameter("v",videoPath);
        Log.d("hazem", "getYoutubeVideoURL: " + builder.build().toString());
        return builder.build().toString();
    }
}
