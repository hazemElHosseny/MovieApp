package com.example.zomy.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zomy.moviesapp.R;
import com.example.zomy.moviesapp.Uitls.URL;
import com.example.zomy.moviesapp.data.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by
 * ZoMy on 8/20/2016.
 */
public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {
    private List<MovieDetails.Trailer.Youtube> mValues;
    private View.OnClickListener clickListener;
    private Context mContext;

    public TrailerRecyclerViewAdapter(List<MovieDetails.Trailer.Youtube> items,View.OnClickListener clickListener, Context context) {
        mValues = items;
        mContext = context;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailers_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        Picasso.with(mContext).
                load(URL.getYoutubeImageURL(mValues.get(position).getSource()))
                .placeholder(R.drawable.trailer_placeholder).into(holder.mImageView);

        holder.mView.setTag(mValues.get(position).getSource());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    clickListener.onClick(v);
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
        public MovieDetails.Trailer.Youtube mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.trailer_text);
            mImageView = (ImageView) view.findViewById(R.id.trailer_image);
        }
    }
}
