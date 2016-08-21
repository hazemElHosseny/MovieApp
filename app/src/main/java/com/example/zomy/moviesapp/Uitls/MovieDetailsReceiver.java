package com.example.zomy.moviesapp.Uitls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.zomy.moviesapp.DetailsMovieFragment;
import com.example.zomy.moviesapp.MainActivity;
import com.example.zomy.moviesapp.data.MovieDetails;

public class MovieDetailsReceiver extends BroadcastReceiver{
    private DetailsMovieFragment detailsMovieFragment;
    private MainActivity mainActivity;
    public MovieDetailsReceiver(DetailsMovieFragment detailsMovieFragment, MainActivity mainActivity) {
        this.detailsMovieFragment = detailsMovieFragment;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MovieDetails movieDetails = (MovieDetails)intent.getSerializableExtra("MovieDetails");
        mainActivity.getProgressBar().setVisibility(View.GONE);
        detailsMovieFragment.fillData(movieDetails);
    }
}
