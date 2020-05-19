package com.example.musicpro.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.musicpro.R;

public class MainActivity extends AppCompatActivity {
    private Button venueBtn, mapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        venueBtn = findViewById(R.id.venue_btn);
        mapBtn = findViewById(R.id.map_btn);
        venueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VenueActivity.class));
            }
        });

    }
}
