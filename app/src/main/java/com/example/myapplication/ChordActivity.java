package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView[] highText = new TextView[7];
    private RelativeLayout[] chordView = new RelativeLayout[7];

    private int reqCode;

    private int[] highScore= new int[7];

    public static final String CHORD_TYPE= "com.example.application.example.CHORD_TYPE";
    public static final String MAJOR_CHORD_HIGH="majorChordHigh";
    public static final String MINOR_CHORD_HIGH="minorChordHigh";
    public static final String MAJOR_SEVENTH_CHORD_HIGH="majorSeventhChordHigh";
    public static final String MINOR_SEVENTH_CHORD_HIGH="minorSeventhChordHigh";
    public static final String DOMINANT_CHORD_HIGH="dominantChordHigh";
    public static final String MIN_MAJ_CHORD_HIGH_HIGH="minMajChordHigh";
    public static final String ANY_CHORD_HIGH="anyChordHigh";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chord);

        chordView[0] = findViewById(R.id.majorChordView);
        chordView[1] = findViewById(R.id.minorChordView);
        chordView[2] = findViewById(R.id.majorSeventhChordView);
        chordView[3] = findViewById(R.id.minorSeventhChordView);
        chordView[4] = findViewById(R.id.dominantChordView);
        chordView[5] = findViewById(R.id.minMajChordView);
        chordView[6] = findViewById(R.id.anyChordView);

        highText[0] = findViewById(R.id.majorChordHigh);
        highText[1] = findViewById(R.id.minorChordHigh);
        highText[2] = findViewById(R.id.majorSeventhChordHigh);
        highText[3] = findViewById(R.id.minorSeventhChordHigh);
        highText[4] = findViewById(R.id.dominantChordHigh);
        highText[5] = findViewById(R.id.minMajChordhHigh);
        highText[6] = findViewById(R.id.anyChordhHigh);

        chordView[0].setOnClickListener(this);
        chordView[1].setOnClickListener(this);
        chordView[2].setOnClickListener(this);
        chordView[3].setOnClickListener(this);
        chordView[4].setOnClickListener(this);
        chordView[5].setOnClickListener(this);
        chordView[6].setOnClickListener(this);

        loadData();
        updateViews();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChordActivity.this, QuizActivity.class);
        switch (v.getId()) {
            case R.id.majorChordView:

                reqCode = 0;
                break;

            case R.id.minorChordView:

                reqCode = 1;
                break;

            case R.id.majorSeventhChordView:

                reqCode = 2;
                break;

            case R.id.minorSeventhChordView:

                reqCode = 3;
                break;

            case R.id.dominantChordView:

                reqCode = 4;
                break;

            case R.id.minMajChordView:

                reqCode = 5;
                break;

            case R.id.anyChordView:

                reqCode = 6;

        }

        intent.putExtra(CHORD_TYPE, reqCode);
        intent.putExtra(MenuActivity.QUIZ_TYPE, 1);

        startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String highName = "";

        int result;

        switch (requestCode) {
            case 0:
                highName = MAJOR_CHORD_HIGH;
                break;
            case 1:
                highName = MINOR_CHORD_HIGH;
                break;
            case 2:
                highName = MAJOR_SEVENTH_CHORD_HIGH;
                break;
            case 3:
                highName = MINOR_SEVENTH_CHORD_HIGH;
                break;
            case 4:
                highName = DOMINANT_CHORD_HIGH;
                break;
            case 5:
                highName = MIN_MAJ_CHORD_HIGH_HIGH;
                break;
            case 6:
                highName = ANY_CHORD_HIGH;
        }

        if (resultCode == RESULT_OK) {

            result = data.getIntExtra("result", 0);

            SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);

            if (sharedPreferences.getInt(highName, 0) < result) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(highName, result);

                highText[requestCode].setText(String.format(getResources().getString(R.string.high_text), String.valueOf(result)));

                editor.apply();

            }

        }

    }

    public void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);

        highScore[0] = sharedPreferences.getInt(MAJOR_CHORD_HIGH, 0);
        highScore[1] = sharedPreferences.getInt(MINOR_CHORD_HIGH, 0);
        highScore[2] = sharedPreferences.getInt(MAJOR_SEVENTH_CHORD_HIGH, 0);
        highScore[3] = sharedPreferences.getInt(MINOR_SEVENTH_CHORD_HIGH, 0);
        highScore[4] = sharedPreferences.getInt(DOMINANT_CHORD_HIGH, 0);
        highScore[5] = sharedPreferences.getInt(MIN_MAJ_CHORD_HIGH_HIGH, 0);
        highScore[6] = sharedPreferences.getInt(ANY_CHORD_HIGH, 0);

    }

    public void updateViews() {

        for (int i = 0; i < 7; i++) {

            highText[i].setText(String.format(getResources().getString(R.string.high_text), String.valueOf(highScore[i])));

        }

    }
}