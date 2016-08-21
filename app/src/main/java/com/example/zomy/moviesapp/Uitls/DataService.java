package com.example.zomy.moviesapp.Uitls;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zomy.moviesapp.data.MovieDetails;
import com.example.zomy.moviesapp.data.MovieListResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class DataService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_MOST_POPULAR = "com.example.zomy.moviesapp.Uitls.action.POPULAR";
    private static final String ACTION_TOP_RATED = "com.example.zomy.moviesapp.Uitls.action.RATED";
    private static final String ACTION_MOVIE = "com.example.zomy.moviesapp.Uitls.action.MOVIE";

    private static final String EXTRA_PAGE_NUMBER = "com.example.zomy.moviesapp.Uitls.extra.PAGE";
    private static final String EXTRA_NEW_TASK = "com.example.zomy.moviesapp.Uitls.extra.TASK";
    private static final String EXTRA_MOVIE_ID = "com.example.zomy.moviesapp.Uitls.extra.MOVIE_ID";

    private static int mostPopularMaxPage = 1;
    private static int topRatedMaxPage = 1;

    public DataService() {
        super("DataService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static boolean startActionMostPopular(Context context, int pageNo, boolean newTask) {
        if(pageNo<=mostPopularMaxPage) {
            Intent intent = new Intent(context, DataService.class);
            intent.setAction(ACTION_MOST_POPULAR);
            intent.putExtra(EXTRA_PAGE_NUMBER, pageNo);
            intent.putExtra(EXTRA_NEW_TASK, newTask);
            context.startService(intent);
            return true;
        }
        return false;
    }

    public static void startActionGetMovie(Context context, int movieID) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(ACTION_MOVIE);
        intent.putExtra(EXTRA_MOVIE_ID, movieID);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static boolean startActionTopRated(Context context, int pageNo, boolean newTask) {
        if(pageNo<=topRatedMaxPage) {
            Intent intent = new Intent(context, DataService.class);
            intent.setAction(ACTION_TOP_RATED);
            intent.putExtra(EXTRA_PAGE_NUMBER, pageNo);
            intent.putExtra(EXTRA_NEW_TASK, newTask);
            context.startService(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_MOST_POPULAR.equals(action)) {
                final int param1 = intent.getIntExtra(EXTRA_PAGE_NUMBER,1);
                final boolean param2 = intent.getBooleanExtra(EXTRA_NEW_TASK,true);
                handleActionPopular(param1,param2);
            } else if (ACTION_TOP_RATED.equals(action)) {
                final int param1 = intent.getIntExtra(EXTRA_PAGE_NUMBER,1);
                final boolean param2 = intent.getBooleanExtra(EXTRA_NEW_TASK,true);
                handleActionTopRated(param1,param2);
            } else if (ACTION_MOVIE.equals(action)){
                final int param1 = intent.getIntExtra(EXTRA_MOVIE_ID,1);
                handleActionMovie(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPopular(int pageNo,final boolean newTask) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.getMostPopularMoviesURL(pageNo), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("hazem", "onResponse: " + response.toString());
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                MovieListResponse movieListResponse = gson.fromJson(response.toString(),MovieListResponse.class);
                mostPopularMaxPage = movieListResponse.getTotalPages();

                Intent intent = new Intent();
                intent.setAction("com.example.zomy.moviesapp.intent.action.MOVIE_LIST");
                intent.putExtra("MovieList", movieListResponse);
                intent.putExtra("newTask", newTask);
                sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("hazem", "onErrorResponse: " + error.getMessage() + error.toString());
            }
        });
        Volley.newRequestQueue(DataService.this).add(jsonObjectRequest);
    }

    private void handleActionMovie(int movieID) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.getMovieData(movieID), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("hazem", "onResponse: " + response.toString());
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                MovieDetails movieDetailsResponse = gson.fromJson(response.toString(),MovieDetails.class);

                Intent intent = new Intent();
                intent.setAction("com.example.zomy.moviesapp.intent.action.MOVIE_DETAILS");
                intent.putExtra("MovieDetails", movieDetailsResponse);
                sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("hazem", "onErrorResponse: " + error.getMessage() + error.toString());
            }
        });
        Volley.newRequestQueue(DataService.this).add(jsonObjectRequest);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionTopRated(int pageNo, final boolean newTask) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL.getTopRatedMoviesURL(pageNo), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("hazem", "onResponse: " + response.toString());
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                MovieListResponse movieListResponse = gson.fromJson(response.toString(),MovieListResponse.class);
                topRatedMaxPage = movieListResponse.getTotalPages();

                Intent intent = new Intent();
                intent.setAction("com.example.zomy.moviesapp.intent.action.MOVIE_LIST");
                intent.putExtra("MovieList", movieListResponse);
                intent.putExtra("newTask", newTask);
                sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("hazem", "onErrorResponse: " + error.getMessage() + error.toString());
            }
        });
        Volley.newRequestQueue(DataService.this).add(jsonObjectRequest);
    }
}
