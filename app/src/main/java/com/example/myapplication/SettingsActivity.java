package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Button noteNameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button deleteButton = findViewById(R.id.deleteButton);
        noteNameButton = findViewById(R.id.noteNameButton);

        setNoteText();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(IntervalActivity.FIFTH_HIGH, 0);
                editor.putInt(IntervalActivity.FOURTH_HIGH, 0);
                editor.putInt(IntervalActivity.MAJOR_THIRD_HIGH, 0);
                editor.putInt(IntervalActivity.MINOR_THIRD_HIGH, 0);
                editor.putInt(IntervalActivity.MAJOR_SEVENTH_HIGH, 0);
                editor.putInt(IntervalActivity.MINOR_SEVENTH_HIGH, 0);

                editor.apply();

                Toast.makeText(SettingsActivity.this, R.string.high_score_deleted_toast, Toast.LENGTH_LONG).show();

            }

        });

        noteNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (sharedPreferences.getBoolean(QuizActivity.NOTE_NAMES, false)) {

                    noteNameButton.setText(R.string.fixed_do);
                    editor.putBoolean(QuizActivity.NOTE_NAMES, false);

                } else {

                    noteNameButton.setText(R.string.standard_notation);
                    editor.putBoolean(QuizActivity.NOTE_NAMES, true);

                }

                editor.apply();

                Toast.makeText(SettingsActivity.this, R.string.note_names_toast, Toast.LENGTH_LONG).show();

            }
        });

    }

    private void setNoteText() {

        SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(QuizActivity.NOTE_NAMES, false)) {

            noteNameButton.setText(R.string.standard_notation);

        } else {

            noteNameButton.setText(R.string.fixed_do);

        }
    }
}