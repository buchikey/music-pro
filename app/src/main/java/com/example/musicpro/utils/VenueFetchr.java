package com.example.musicpro.utils;

import android.net.Uri;
import android.util.Log;

import com.example.musicpro.data.Venue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VenueFetchr {

    private static final String TAG = "VenueFetchr";

    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public Venue fetchBestVenue() {
        Venue bestVenue = new Venue();
        try {
            String url = Uri.parse("http://jellymud.com/venues/best_venue.json")
                    .buildUpon()
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            bestVenue = parseJson(jsonBody);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return bestVenue;
    }

    private Venue parseJson(JSONObject jsonBody) throws JSONException, IOException{
        Venue bestVenue = new Venue();
        bestVenue.setName(jsonBody.getString("name"));
        bestVenue.setAddress(jsonBody.getString("address"));
        bestVenue.setLat(jsonBody.getDouble("lat"));
        bestVenue.setLon(jsonBody.getDouble("lon"));
        return bestVenue;
    }

}