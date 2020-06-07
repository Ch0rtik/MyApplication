package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(MenuActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(MenuActivity.FIFTH_HIGH, 0);
                editor.putInt(MenuActivity.FOURTH_HIGH, 0);
                editor.putInt(MenuActivity.MAJOR_THIRD_HIGH, 0);
                editor.putInt(MenuActivity.MINOR_THIRD_HIGH, 0);
                editor.putInt(MenuActivity.MAJOR_SEVENTH_HIGH, 0);
                editor.putInt(MenuActivity.MINOR_SEVENTH_HIGH, 0);

                editor.apply();

                Toast.makeText(SettingsActivity.this, R.string.high_score_deleted_toast, Toast.LENGTH_LONG).show();

            }

        });

    }
}