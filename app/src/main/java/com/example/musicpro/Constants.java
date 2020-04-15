package com.example.musicpro;

public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME = "name";
    static final String ADDRESS = "address";
    static final String OPENING_TIME = "opening_time";


    //DB PROPERTIES
    static final String DB_NAME="music_pro";
    static final String TB_NAME="venue_details";
    static final int DB_VERSION='1';

    static final String CREATE_TB="CREATE TABLE d_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,address TEXT NOT NULL, opening_time TEXT NULL);";
}

