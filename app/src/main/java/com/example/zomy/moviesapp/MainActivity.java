package com.example.zomy.moviesapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.zomy.moviesapp.Uitls.DataService;
import com.example.zomy.moviesapp.adapter.MyMovieRecyclerViewAdapter;
import com.example.zomy.moviesapp.data.MovieItem;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnListFragmentInteractionListener ,
        DetailsMovieFragment.OnFragmentInteractionListener{

    private ProgressBar progressBar;
    private MovieItem currentMovie;
    MovieFragment movieFragment;
    private MyMovieRecyclerViewAdapter adapter;
    private String sortType;
    private int currentPage = 0;
    private Set<String> favSet;

    public int getCurrentPage() {
        return currentPage;
    }

    public Set<String> getFavSet() {
        return favSet;
    }

    public void addItemToFavSet(String item){
        favSet.add(item);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getSortType() {
        return sortType;
    }

    public void setAdapter(MyMovieRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public MyMovieRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setMovieListLoading (boolean loading){
        movieFragment.setLoading(loading);
    }

    public MovieItem getCurrentMovie() {
        return currentMovie;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        SharedPreferences sharedPrefs = getSharedPreferences("Movie",MODE_PRIVATE);
        favSet = sharedPrefs.getStringSet("FavSet",null);
        if(favSet == null){
            favSet = new HashSet<>();
        }
        sortType = sharedPrefs.getString(getString(R.string.sort),getString(R.string.popular));
        Log.d("hazem", "onCreate: sortType " + sortType);
        if(sortType.contentEquals(getString(R.string.popular))){
            Log.d("hazem", "onCreate: pop");
            DataService.startActionMostPopular(MainActivity.this,1,true);
        }
        else{
            Log.d("hazem", "onCreate: top");
            DataService.startActionTopRated(MainActivity.this,1,true);
        }

        movieFragment = new MovieFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,movieFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.sort){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.sort)
                    .setItems(R.array.sort_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            String selectedSort = getString(R.string.top_rated);
                            if(which==0){
                                selectedSort = getString(R.string.popular);
                            }
                            SharedPreferences.Editor editor = getSharedPreferences("Movie",MODE_APPEND).edit();
                            editor.putString(getString(R.string.sort),selectedSort).apply();
                            if(which==0){
                                DataService.startActionMostPopular(MainActivity.this,1,true);
                            }
                            else{
                                DataService.startActionTopRated(MainActivity.this,1,true);
                            }
                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(MovieItem item) {
        this.currentMovie = item;
        Log.d("hazem", "onListFragmentInteraction: item clicked " +item.getTitle());
        DataService.startActionGetMovie(MainActivity.this,item.getId());
        getSupportFragmentManager().beginTransaction().remove(movieFragment)
                .add(R.id.container,new DetailsMovieFragment(),"movieDetails").commit();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("movieDetails")!=null){
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentByTag("movieDetails"))
                    .add(R.id.container,movieFragment).commit();
        }
        else {
            SharedPreferences.Editor editor = getSharedPreferences("Movie",MODE_APPEND).edit();
            editor.putStringSet("FavSet",favSet).commit();
            super.onBackPressed();
        }
    }
}
