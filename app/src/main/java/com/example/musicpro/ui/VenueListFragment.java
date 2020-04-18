package com.example.musicpro.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;

import java.util.ArrayList;
import java.util.List;


public class VenueListFragment extends Fragment {
    private RecyclerView venueRecyclerView;
    private List<Venue> venueList;
    private VenueListAdapter venueListAdapter;
    private Listener fetchListener;

    public VenueListFragment() {

    }

    public static VenueListFragment newInstance(ArrayList<Venue> venueList) {
        VenueListFragment fragment = new VenueListFragment();
        Bundle args = new Bundle();
        if (venueList != null) {
            args.putSerializable("VENUE_LIST", venueList);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchListener.fetchVenues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        this.venueList = new ArrayList<>();
        venueRecyclerView = view.findViewById(R.id.venue_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        venueListAdapter = new VenueListAdapter(getActivity(), venueList, (VenueListAdapter.OnClickListener) getActivity());
        venueRecyclerView.setLayoutManager(layoutManager);
        venueRecyclerView.setAdapter(venueListAdapter);
        List<Venue> venues = (ArrayList<Venue>) getArguments().getSerializable("VENUE_LIST");
        venueList.addAll(venues);
        venueListAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof Listener) {
            fetchListener = (Listener) getActivity();
        }
    }

    public void setVenueList(List<Venue> venueList) {
        this.venueList = new ArrayList<>(venueList);
        Log.e("Venue List", this.venueList.toString());
        venueListAdapter.notifyDataSetChanged();
    }

    public interface Listener {
        void fetchVenues();
    }
}
