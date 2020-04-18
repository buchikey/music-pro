package com.example.musicpro.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {


    DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //TABLE CREATION
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_TB);
            Log.e("TABLE","Created");

        }catch (Exception ex)
        {Log.e("TABLE","Failed");
            ex.printStackTrace();
        }

    }

    //TABLE UPGRADE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TB_NAME);
        onCreate(db);

    }
}

