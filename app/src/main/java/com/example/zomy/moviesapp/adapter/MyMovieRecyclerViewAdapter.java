package com.example.zomy.moviesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zomy.moviesapp.MainActivity;
import com.example.zomy.moviesapp.MovieFragment.OnListFragmentInteractionListener;
import com.example.zomy.moviesapp.R;
import com.example.zomy.moviesapp.Uitls.URL;
import com.example.zomy.moviesapp.data.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private List<MovieItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Activity mActivity;

    public MyMovieRecyclerViewAdapter(List<MovieItem> items, OnListFragmentInteractionListener listener, Activity context) {
        mValues = items;
        mListener = listener;
        mActivity = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(((MainActivity)mActivity).getFavSet().contains(String.valueOf(mValues.get(position).getId()))){
            holder.mFavIcon.setVisibility(View.VISIBLE);
        }
        else{
            holder.mFavIcon.setVisibility(View.INVISIBLE);
        }
        holder.mIdView.setText(mValues.get(position).getTitle());
        Picasso.with(mActivity).
                load(URL.getImageURL("w500",mValues.get(position).getPosterPath()))
                .placeholder(R.drawable.movie_placeholder).into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public ImageView mImageView;
        public MovieItem mItem;
        public ImageView mFavIcon;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mImageView = (ImageView) view.findViewById(R.id.movie_poster);
            mFavIcon = (ImageView) view.findViewById(R.id.fav_icon);
        }
    }

    public void setValues(List<MovieItem> mValues) {
        this.mValues = mValues;
    }

    public void addValues(List<MovieItem> mValues){
        this.mValues.addAll(mValues);
    }
}
