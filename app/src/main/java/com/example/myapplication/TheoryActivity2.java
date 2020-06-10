package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TheoryActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory2);
        TextView theoryText = findViewById(R.id.theoryText);
        Intent intent = getIntent();
        switch (intent.getIntExtra(MenuActivity.THEORY_TYPE, 0)){
            case 0:
                theoryText.setText(R.string.interval_theory);
                break;
            case 1:
                theoryText.setText(R.string.chord_theory);
                break;
            case 2:
                theoryText.setText(R.string.tonality_theory);
        }
    }
}