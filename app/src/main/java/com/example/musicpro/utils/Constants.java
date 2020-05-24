package com.example.musicpro.utils;

public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String ADDRESS = "address";
    public static final String OPENING_TIME = "opening_time";
    public static final String BEST_VENUE = "BEST_VENUE";


    //DB PROPERTIES
    static final String DB_NAME="music_pro";
    static final String TB_NAME="venue_details";
    static final int DB_VERSION='2';

    static final String CREATE_TB="CREATE TABLE venue_details(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "name TEXT NOT NULL,address TEXT NOT NULL, opening_time TEXT NULL);";
}

