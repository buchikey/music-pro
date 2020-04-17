package com.example.musicpro.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;

public class VenueDetailsFragment extends Fragment implements View.OnClickListener {
    private EditText venueNameInput, venueAddressInput, openingTimeInput;
    private Button doneVenueBtn, deleteVenueBtn;
    private VenueDetailsListener venueDetailsListener;

    private String venueName, venueAddress, openingTime;
    private Venue venue;

    static VenueDetailsFragment newInstance(String type, Venue venue) {
        VenueDetailsFragment fragment = new VenueDetailsFragment();
        Bundle args = new Bundle();
        if (venue != null) {
            args.putSerializable("VENUE", venue);
        }
        args.putString("TYPE", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.venue_details, container, false);
        venueNameInput = view.findViewById(R.id.venue_name);
        venueAddressInput = view.findViewById(R.id.venue_address);
        openingTimeInput = view.findViewById(R.id.openingTimeInput);
        doneVenueBtn = view.findViewById(R.id.doneVenueBtn);
        deleteVenueBtn = view.findViewById(R.id.deleteVenueBtn);

        doneVenueBtn.setOnClickListener(this);

        deleteVenueBtn.setOnClickListener(this);
        try {
            venue = (Venue) getArguments().getSerializable("VENUE");
            final String type = getArguments().getString("TYPE");
            Log.e("Venue", venue.toString());
            if(type.equals("EDIT")){

                venueNameInput.setText(venue.getName());
                venueAddressInput.setText(venue.getAddress());
                openingTimeInput.setText(venue.getTime());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof VenueDetailsListener) {
            venueDetailsListener = (VenueDetailsListener) getActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneVenueBtn:
                venueName = venueNameInput.getText().toString();
                venueAddress = venueAddressInput.getText().toString();
                openingTime = openingTimeInput.getText().toString();

                int errorId = validateVenueDetailsForm();
                Toast.makeText(getContext(), errorId + "", Toast.LENGTH_SHORT).show();
                if (errorId != 0) {
//                    getView().findViewById(errorId).getBackground().setColorFilter(getResources().getColor(R.color.colorPink),
//                            PorterDuff.Mode.SRC_ATOP);
                    venueDetailsListener.onError("Venue Details are incomplete");
                } else {
                    Toast.makeText(getContext(), errorId + "", Toast.LENGTH_SHORT).show();
                    venue = new Venue(venueName, venueAddress, openingTime);
//                    getView().findViewById(errorId).getBackground().setColorFilter(getResources().getColor(android.R.color.black),
//                            PorterDuff.Mode.SRC_ATOP);
                    venueDetailsListener.onDone(venue);
                }

                break;
            case R.id.deleteVenueBtn:
                venueDetailsListener.onDelete(venue);
                break;

        }
    }

    private int validateVenueDetailsForm() {

        if (TextUtils.isEmpty(venueName)) {
            return R.id.venue_name;
        }

        if (TextUtils.isEmpty(venueAddress)) {
            return R.id.venue_address;
        }

        if (TextUtils.isEmpty(openingTime)) {
            return R.id.openingTimeInput;
        }

        return 0;
    }


    public interface VenueDetailsListener {
        void onDelete(Venue venue);

        void onError(String errorMessage);

        void onDone(Venue venue);
    }
}
