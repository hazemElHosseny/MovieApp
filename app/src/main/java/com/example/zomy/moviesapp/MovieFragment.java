package com.example.zomy.moviesapp;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zomy.moviesapp.Uitls.DataService;
import com.example.zomy.moviesapp.Uitls.MovieListReceiver;
import com.example.zomy.moviesapp.adapter.MyMovieRecyclerViewAdapter;
import com.example.zomy.moviesapp.data.MovieItem;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<MovieItem> movieItems;
    private MyMovieRecyclerViewAdapter adapter;
    private MovieListReceiver movieListReceiver;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public MovieFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            mGridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(mGridLayoutManager);
            movieItems = new ArrayList<>();
            if(((MainActivity)getActivity()).getAdapter()!=null){
                adapter = ((MainActivity)getActivity()).getAdapter();
            }
            else {
                adapter = new MyMovieRecyclerViewAdapter(movieItems, mListener, getActivity());
            }
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = mGridLayoutManager.getItemCount();
                    firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached

                        Log.i("Yaeye!", "end called");

                        String sortType = ((MainActivity)getActivity()).getSortType();
                        if(sortType.contentEquals(getString(R.string.popular))){
                            Log.d("hazem", "onCreate: pop");
                            DataService.startActionMostPopular(getActivity(),
                                    ((MainActivity) getActivity()).getCurrentPage()+1,false);
                        }
                        else{
                            Log.d("hazem", "onCreate: top");
                            DataService.startActionTopRated(getActivity(),
                                    ((MainActivity) getActivity()).getCurrentPage()+1,true);
                        }

                        loading = true;
                    }
                }
            });

            movieListReceiver = new MovieListReceiver(adapter,getActivity());
            IntentFilter filter = new IntentFilter();
            filter.addAction(getResources().getString(R.string.movie_receiver_action));
            getActivity().registerReceiver(movieListReceiver,filter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(MovieItem item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(movieListReceiver!=null){
            getActivity().unregisterReceiver(movieListReceiver);
        }
    }
}
