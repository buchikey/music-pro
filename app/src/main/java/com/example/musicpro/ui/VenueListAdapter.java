package com.example.musicpro.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;

import java.util.List;

public class VenueListAdapter extends RecyclerView.Adapter<VenueListViewHolder> {

    private Context context;
    private List<Venue> venueList;
    private OnClickListener onClickListener;

    public VenueListAdapter(Context context, List<Venue> venueList, OnClickListener onClickListener) {
        this.context = context;
        this.venueList = venueList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public VenueListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_layout_item,parent,false);
        return new VenueListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueListViewHolder holder, int position) {
        final Venue venue = venueList.get(position);
        holder.setVenueName(venue.getName());
        holder.setVenueAddress(venue.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onVenueItemClick(venue);
            }
        });
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public interface OnClickListener{
        void onVenueItemClick(Venue venue);
    }
}
