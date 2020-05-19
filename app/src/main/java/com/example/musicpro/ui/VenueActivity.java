package com.example.musicpro.ui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;
import com.example.musicpro.utils.Constants;
import com.example.musicpro.utils.DBAdapter;

import java.util.ArrayList;


public class VenueActivity extends AppCompatActivity implements VenueListAdapter.OnClickListener, VenueDetailsFragment.VenueDetailsListener{
    private FragmentManager fragmentManager;
    private AlertDialog.Builder builder;
    private VenueDetailsFragment venueDetailsFragment;
    private VenueListFragment venueListFragment;
    private DBAdapter dbAdapter;
    private Cursor cursor;
    private ArrayList<Venue> venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        fragmentManager = getSupportFragmentManager();
        builder = new AlertDialog.Builder(this);
        dbAdapter = new DBAdapter(this);
        fetchDBData();
        venueListFragment = VenueListFragment.newInstance(venueList);
        venueDetailsFragment = VenueDetailsFragment.newInstance("ADD", null);
        fragmentManager.beginTransaction().setReorderingAllowed(true).add(R.id.fragmentContainer, venueListFragment, "VENUE_LIST").addToBackStack(null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.venue_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_venue) {
            venueDetailsFragment = VenueDetailsFragment.newInstance("ADD", null);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, venueDetailsFragment, "VENUE_DETAILS")
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVenueItemClick(Venue venue) {
        venueDetailsFragment = VenueDetailsFragment.newInstance("EDIT", venue);
        fragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, venueDetailsFragment, "VENUE_DETAILS")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDelete(final Venue venue) {
        if (venue.getId() != null) {
            builder.setMessage(R.string.delete_venue)
                    .setCancelable(false)
                    .setPositiveButton(Html.fromHtml("<font color='#ff00bf'>YES</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dbAdapter.deleteOneRow(venue.getId());
                            fetchDBData();
                            venueListFragment = VenueListFragment.newInstance(venueList);
                            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, venueListFragment, "VENUE_LIST").addToBackStack(null).commit();
                        }
                    })
                    .setNegativeButton(Html.fromHtml("<font color='#ff00bf'>NO</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Confirm");
            alert.show();
        }
    }

    @Override
    public void onError(String errorMessage) {
        builder.setMessage(R.string.venue_details_warning)
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#ff00bf'>OK</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Warning");
        alert.show();
    }

    private void fetchDBData() {
        try {
            cursor = dbAdapter.getAllVenueDetails();
            venueList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getCount() > 0) {
                        final String venueId = String.valueOf(cursor.getInt(cursor.getColumnIndex(Constants.ID)));
                        final String venueName = cursor.getString(cursor.getColumnIndex(Constants.NAME));
                        final String venueAddress = cursor.getString(cursor.getColumnIndex(Constants.ADDRESS));
                        final String openingTime = cursor.getString(cursor.getColumnIndex(Constants.OPENING_TIME));
                        venueList.add(new Venue(venueId, venueName, venueAddress, openingTime));
                    }

                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            dbAdapter.closeDB();
        }
    }

    @Override
    public void onDone(Venue venue) {
        try {
            if (venue.getId() == null)
                dbAdapter.add(venue.getName(), venue.getAddress(), venue.getTime());
            else
                dbAdapter.updateData(venue.getId(), venue.getName(), venue.getAddress(), venue.getTime());
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            dbAdapter.closeDB();
        }
        fetchDBData();
        venueListFragment = VenueListFragment.newInstance(venueList);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, venueListFragment, "VENUE_LIST").addToBackStack(null).commit();

    }
}
