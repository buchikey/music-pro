package com.example.musicpro.ui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;
import com.example.musicpro.utils.Constants;
import com.example.musicpro.utils.DBAdapter;

import java.util.ArrayList;


public class VenueActivity extends AppCompatActivity implements VenueListAdapter.OnClickListener, VenueDetailsFragment.VenueDetailsListener, VenueListFragment.Listener {
    private FragmentManager fragmentManager;
    private AlertDialog.Builder builder;
    private Fragment currentFragment;
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
        currentFragment = venueListFragment;
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, currentFragment, "venueList").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDBData();
        venueListFragment.setVenueList(venueList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.venue_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_venue) {
            currentFragment = venueDetailsFragment;
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment, "venueDetails")
                    .addToBackStack("venueList")
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVenueItemClick(Venue venue) {
        currentFragment = VenueDetailsFragment.newInstance("EDIT", venue);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment, "venueDetails").addToBackStack("venueList").commit();
    }

    @Override
    public void onDelete(final Venue venue) {
        if (venue.getId() != null) {
            builder.setMessage("Delete Venue ?")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dbAdapter.deleteOneRow(venue.getId());
                            fetchDBData();
                            fragmentManager.popBackStack();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
        builder.setMessage("Venue details are incomplete")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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
                        Log.e("Cursor", "" + cursor.getColumnCount());
                        final String venueId = String.valueOf(cursor.getColumnIndex(Constants.ID));
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
            Log.e("VVVVV", venue.toString());
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
        fragmentManager.popBackStack();
        venueListFragment.setVenueList(venueList);
    }

    @Override
    public void fetchVenues() {
        //fetchDBData();
        venueListFragment.setVenueList(venueList);
    }
}
