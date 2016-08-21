package com.example.zomy.moviesapp.Uitls;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.zomy.moviesapp.MainActivity;
import com.example.zomy.moviesapp.adapter.MyMovieRecyclerViewAdapter;
import com.example.zomy.moviesapp.data.MovieListResponse;

public class MovieListReceiver extends BroadcastReceiver {
    private MyMovieRecyclerViewAdapter adapter;
    private Activity activity;


    public MovieListReceiver(MyMovieRecyclerViewAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MovieListResponse movieListResponse = (MovieListResponse)intent.getSerializableExtra("MovieList");
        boolean newTask = intent.getBooleanExtra("newTask",true);
        if(movieListResponse.getPage() == ((MainActivity)activity).getCurrentPage() && !newTask){
            Log.d("hazem", "onReceive: nafs el page");
        }
        else {
            if (newTask) {
                adapter.setValues(movieListResponse.getResults());
            } else {
                adapter.addValues(movieListResponse.getResults());
            }
            adapter.notifyDataSetChanged();
            Log.d("hazem", "onReceive: hal main");
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).getProgressBar().setVisibility(View.GONE);
                ((MainActivity) activity).setAdapter(adapter);
                ((MainActivity) activity).setMovieListLoading(false);
                ((MainActivity) activity).setCurrentPage(movieListResponse.getPage());
            }
        }
    }
}
