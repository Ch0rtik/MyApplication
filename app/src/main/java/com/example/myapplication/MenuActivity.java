package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    public static final String QUIZ_TYPE = "com.example.application.example.QUIZ_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button intervalButton = findViewById(R.id.intervalButton);
        Button chordButton = findViewById(R.id.chordButton);
        Button tonalityButton = findViewById(R.id.tonalityButton);

        intervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, IntervalActivity.class);
                startActivity(intent);
            }
        });

        chordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChordActivity.class);
                startActivity(intent);
            }
        });
        tonalityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TonalityActivity.class);
                startActivity(intent);
            }
        });
    }
}