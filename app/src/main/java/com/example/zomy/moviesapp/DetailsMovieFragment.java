package com.example.zomy.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zomy.moviesapp.Uitls.MovieDetailsReceiver;
import com.example.zomy.moviesapp.Uitls.MovieListReceiver;
import com.example.zomy.moviesapp.Uitls.URL;
import com.example.zomy.moviesapp.adapter.TrailerRecyclerViewAdapter;
import com.example.zomy.moviesapp.data.MovieDetails;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsMovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailsMovieFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView movieYearTextView;
    private TextView movieDurationTextView;
    private TextView movieRateTextView;
    private TextView movieDetailsTextView;
    private MovieDetailsReceiver movieDetailsReceiver;
    private Button markFavButton;
    private RecyclerView mRecyclerView;
    public DetailsMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_movie, container, false);
        moviePosterImageView = (ImageView) view.findViewById(R.id.movie_poster);
        movieTitleTextView = (TextView) view.findViewById(R.id.movie_title);
        movieYearTextView = (TextView) view.findViewById(R.id.movie_year);
        movieDurationTextView = (TextView) view.findViewById(R.id.movie_duration);
        movieRateTextView = (TextView) view.findViewById(R.id.movie_rate);
        movieDetailsTextView = (TextView) view.findViewById(R.id.movie_details);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.trailers_recycler_view);
        markFavButton = (Button) view.findViewById(R.id.mark_fav);
        markFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity())
                        .addItemToFavSet(String.valueOf(((MainActivity)getActivity()).getCurrentMovie().getId()));
            }
        });
        Log.d("hazem", "onCreateView: details");

        Picasso.with(getActivity()).
                load(URL.getImageURL("w500",((MainActivity)getActivity()).getCurrentMovie().getPosterPath()))
                .placeholder(R.drawable.movie_placeholder).into(moviePosterImageView);
        movieTitleTextView.setText(((MainActivity)getActivity()).getCurrentMovie().getTitle());
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        movieYearTextView.setText(df.format(((MainActivity)getActivity()).getCurrentMovie().getReleaseDate()));
        String temp = String.valueOf(((MainActivity)getActivity()).getCurrentMovie().getVoteAverage())+"/10";
        movieRateTextView.setText(temp);
        movieDetailsTextView.setText(((MainActivity)getActivity()).getCurrentMovie().getOverview());

        movieDetailsReceiver = new MovieDetailsReceiver(DetailsMovieFragment.this,(MainActivity) getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction(getResources().getString(R.string.movie_details_receiver_action));
        getActivity().registerReceiver(movieDetailsReceiver,filter);

        return view;
    }

    public void fillData(MovieDetails movieDetails){
        movieDurationTextView.setText(movieDetails.getRuntime());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        TrailerRecyclerViewAdapter trailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(movieDetails.getTrailers().getYoutubes(),this,getActivity());
        mRecyclerView.setAdapter(trailerRecyclerViewAdapter);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(movieDetailsReceiver!=null){
            getActivity().unregisterReceiver(movieDetailsReceiver);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        Log.d("hazem", "onClick: trailer clicked " + view.getTag());
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL.getYoutubeVideoURL(view.getTag().toString()))));
    }
}
