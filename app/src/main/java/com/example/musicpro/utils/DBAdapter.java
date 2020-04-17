package com.example.musicpro.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
    Context c;

    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }

    //CLOSE DATABASE
    public void closeDB() {
        try {
            helper.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //INSERT
    public long add(String name, String address, String opening_time) {
        try {
            db = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.ADDRESS, address);
            cv.put(Constants.OPENING_TIME, opening_time);

            return db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public long updateData(String row_id, String name, String address, String opening_time) {
        try {
            db = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.ADDRESS, address);
            cv.put(Constants.OPENING_TIME, opening_time);

            return db.update(Constants.TB_NAME, cv, Constants.ROW_ID + "=?", new String[]{row_id});
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public long deleteOneRow(String row_id) {
        try {
            Log.e("Venue ID", row_id);
            db = helper.getWritableDatabase();
            return db.delete(Constants.TB_NAME, "id=?", new String[]{row_id});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    //RETRIEVE
    public Cursor getAllVenueDetails() {
        db = helper.getReadableDatabase();
        String[] columns = {Constants.ROW_ID, Constants.NAME, Constants.ADDRESS, Constants.OPENING_TIME};

        return db.query(Constants.TB_NAME, columns, null, null, null, null, null);

    }
}