package com.example.musicpro.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicpro.R;

class VenueListViewHolder extends RecyclerView.ViewHolder {
    private TextView venueName, venueAddress;

    public VenueListViewHolder(@NonNull View itemView) {
        super(itemView);
        venueName = itemView.findViewById(R.id.venue_name);
        venueAddress = itemView.findViewById(R.id.venue_address);
    }

    public TextView getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName.setText(venueName);
    }

    public TextView getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress.setText(venueAddress);
    }
}
