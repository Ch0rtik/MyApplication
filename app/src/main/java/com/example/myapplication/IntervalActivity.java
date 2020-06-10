package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntervalActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INTERVAL_NUMBER = "com.example.application.example.EXTRA_NUMBER";

    private int reqCode;

    private View[] menuView = new View[13];
    private TextView[] highText = new TextView[13];
    private int[] highScore = new int[13];
    private Button theoryButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIFTH_HIGH = "fifthHigh";
    public static final String FOURTH_HIGH = "fourthHigh";
    public static final String MAJOR_THIRD_HIGH = "majorThirdHigh";
    public static final String MINOR_THIRD_HIGH = "minorThirdHigh";
    public static final String MAJOR_SEVENTH_HIGH = "majorSeventhHigh";
    public static final String MINOR_SEVENTH_HIGH = "minorSeventhHigh";
    public static final String INTERVAL_NOTE_HIGH = "intervalNoteHigh";
    public static final String MAJOR_SECOND_HIGH = "majorSecondHigh";
    public static final String MINOR_SECOND_HIGH = "minorSecondHigh";
    public static final String MAJOR_SIXTH_HIGH = "majorSixthHigh";
    public static final String MINOR_SIXTH_HIGH = "minorSixthHigh";
    public static final String DIMINISHED_FIFTH_HIGH = "diminishedFifthHigh";
    public static final String AUGMENTED_FOURTH_HIGH = "augmentedFourthHigh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);

        menuView[0] = findViewById(R.id.perfectFifthView);
        menuView[1] = findViewById(R.id.perfectFourthView);
        menuView[2] = findViewById(R.id.majorThirdView);
        menuView[3] = findViewById(R.id.minorThirdView);
        menuView[4] = findViewById(R.id.majorSeventhView);
        menuView[5] = findViewById(R.id.minorSeventhView);
        menuView[6] = findViewById(R.id.intervalNoteView);
        menuView[7] = findViewById(R.id.majorSecondView);
        menuView[8] = findViewById(R.id.minorSecondView);
        menuView[9] = findViewById(R.id.majorSixthView);
        menuView[10] = findViewById(R.id.minorSixthView);
        menuView[11] = findViewById(R.id.diminishedFifthView);
        menuView[12] = findViewById(R.id.augmentedFourthView);

        theoryButton = findViewById(R.id.theoryButton2);


        highText[0] = findViewById(R.id.perfectFifthHigh);
        highText[1] = findViewById(R.id.perfectFourthHigh);
        highText[2] = findViewById(R.id.majorThirdHigh);
        highText[3] = findViewById(R.id.minorThirdHigh);
        highText[4] = findViewById(R.id.majorSeventhHigh);
        highText[5] = findViewById(R.id.minorSeventhHigh);
        highText[6] = findViewById(R.id.intervalNoteHigh);
        highText[7] = findViewById(R.id.majorSecondHigh);
        highText[8] = findViewById(R.id.minorSecondHigh);
        highText[9] = findViewById(R.id.majorSixthHigh);
        highText[10] = findViewById(R.id.minorSixthHigh);
        highText[11] = findViewById(R.id.diminishedFifthHigh);
        highText[12] = findViewById(R.id.augmentedFourthHigh);


        loadData();
        updateViews();

        menuView[0].setOnClickListener(this);
        menuView[1].setOnClickListener(this);
        menuView[2].setOnClickListener(this);
        menuView[3].setOnClickListener(this);
        menuView[4].setOnClickListener(this);
        menuView[5].setOnClickListener(this);
        menuView[6].setOnClickListener(this);
        menuView[7].setOnClickListener(this);
        menuView[8].setOnClickListener(this);
        menuView[9].setOnClickListener(this);
        menuView[10].setOnClickListener(this);
        menuView[11].setOnClickListener(this);
        menuView[12].setOnClickListener(this);

        theoryButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.theoryButton2) {
            Intent intent = new Intent(IntervalActivity.this, TheoryActivity2.class);
            intent.putExtra(MenuActivity.THEORY_TYPE, 0);
            startActivity(intent);
        } else {

            Intent intent = new Intent(IntervalActivity.this, QuizActivity.class);
            switch (v.getId()) {
                case R.id.perfectFifthView:

                    reqCode = 0;
                    intent.putExtra(INTERVAL_NUMBER, 1);
                    break;

                case R.id.perfectFourthView:

                    reqCode = 1;
                    intent.putExtra(INTERVAL_NUMBER, -1);
                    break;

                case R.id.majorThirdView:

                    reqCode = 2;
                    intent.putExtra(INTERVAL_NUMBER, 4);
                    break;

                case R.id.minorThirdView:

                    reqCode = 3;
                    intent.putExtra(INTERVAL_NUMBER, -3);
                    break;

                case R.id.majorSeventhView:

                    reqCode = 4;
                    intent.putExtra(INTERVAL_NUMBER, 5);
                    break;

                case R.id.minorSeventhView:

                    reqCode = 5;
                    intent.putExtra(INTERVAL_NUMBER, -2);
                    break;

                case R.id.intervalNoteView:

                    reqCode = 6;
                    break;

                case R.id.majorSecondView:

                    reqCode = 7;
                    intent.putExtra(INTERVAL_NUMBER, 2);
                    break;

                case R.id.minorSecondView:

                    reqCode = 8;
                    intent.putExtra(INTERVAL_NUMBER, -5);
                    break;
                case R.id.majorSixthView:

                    reqCode = 9;
                    intent.putExtra(INTERVAL_NUMBER, 3);
                    break;

                case R.id.minorSixthView:

                    reqCode = 10;
                    intent.putExtra(INTERVAL_NUMBER, -4);
                    break;
                case R.id.diminishedFifthView:

                    reqCode = 11;
                    intent.putExtra(INTERVAL_NUMBER, -6);
                    break;

                case R.id.augmentedFourthView:

                    reqCode = 12;
                    intent.putExtra(INTERVAL_NUMBER, 6);


            }

            if (reqCode == 6) {
                intent.putExtra(MenuActivity.QUIZ_TYPE, 2);
            } else {
                intent.putExtra(MenuActivity.QUIZ_TYPE, 0);
            }
            startActivityForResult(intent, reqCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String highName = "";

        int result;

        switch (requestCode) {
            case 0:
                highName = FIFTH_HIGH;
                break;
            case 1:
                highName = FOURTH_HIGH;
                break;
            case 2:
                highName = MAJOR_THIRD_HIGH;
                break;
            case 3:
                highName = MINOR_THIRD_HIGH;
                break;
            case 4:
                highName = MAJOR_SEVENTH_HIGH;
                break;
            case 5:
                highName = MINOR_SEVENTH_HIGH;
                break;
            case 6:
                highName = INTERVAL_NOTE_HIGH;
                break;
            case 7:
                highName = MAJOR_SECOND_HIGH;
                break;
            case 8:
                highName = MINOR_SECOND_HIGH;
                break;
            case 9:
                highName = MAJOR_SIXTH_HIGH;
                break;
            case 10:
                highName = MINOR_SIXTH_HIGH;
                break;
            case 11:
                highName = DIMINISHED_FIFTH_HIGH;
                break;
            case 12:
                highName = AUGMENTED_FOURTH_HIGH;
        }

        if (resultCode == RESULT_OK) {

            result = data.getIntExtra("result", 0);

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

            if (sharedPreferences.getInt(highName, 0) < result) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(highName, result);

                highText[requestCode].setText(String.format(getResources().getString(R.string.high_text), String.valueOf(result)));

                editor.apply();

            }

        }

    }

    public void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        highScore[0] = sharedPreferences.getInt(FIFTH_HIGH, 0);
        highScore[1] = sharedPreferences.getInt(FOURTH_HIGH, 0);
        highScore[2] = sharedPreferences.getInt(MAJOR_THIRD_HIGH, 0);
        highScore[3] = sharedPreferences.getInt(MINOR_THIRD_HIGH, 0);
        highScore[4] = sharedPreferences.getInt(MAJOR_SEVENTH_HIGH, 0);
        highScore[5] = sharedPreferences.getInt(MINOR_SEVENTH_HIGH, 0);
        highScore[6] = sharedPreferences.getInt(INTERVAL_NOTE_HIGH, 0);
        highScore[4] = sharedPreferences.getInt(MAJOR_SECOND_HIGH, 0);
        highScore[5] = sharedPreferences.getInt(MINOR_SECOND_HIGH, 0);
        highScore[4] = sharedPreferences.getInt(MAJOR_SIXTH_HIGH, 0);
        highScore[5] = sharedPreferences.getInt(MINOR_SIXTH_HIGH, 0);
        highScore[4] = sharedPreferences.getInt(DIMINISHED_FIFTH_HIGH, 0);
        highScore[5] = sharedPreferences.getInt(AUGMENTED_FOURTH_HIGH, 0);

    }

    public void updateViews() {

        for (int i = 0; i < 13; i++) {

            highText[i].setText(String.format(getResources().getString(R.string.high_text), String.valueOf(highScore[i])));

        }

    }

}