package com.example.musicpro.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;
import com.example.musicpro.utils.Constants;
import com.example.musicpro.utils.VenueFetchr;

public class MainActivity extends AppCompatActivity {
    private Button venueBtn, mapBtn;
    private Venue bestVenue;

    public Venue getBestVenue() {
        return bestVenue;
    }

    public void setBestVenue(Venue bestVenue) {
        this.bestVenue = bestVenue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fetching Best Venue in seperate thread
        new FetchBestVenueTask().execute();

        venueBtn = findViewById(R.id.venue_btn);
        mapBtn = findViewById(R.id.map_btn);

        venueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VenueActivity.class));
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                try {
                    mapsIntent.putExtra(Constants.BEST_VENUE, getBestVenue());
                    startActivity(mapsIntent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }



    @SuppressLint("StaticFieldLeak")
    private class FetchBestVenueTask extends AsyncTask<Void,Void, Venue> {
        @Override
        protected Venue doInBackground(Void... params) {
            return new VenueFetchr().fetchBestVenue();
        }

        @Override
        protected void onPostExecute(Venue bestVenue) {
            setBestVenue(bestVenue);
        }

    }
}
