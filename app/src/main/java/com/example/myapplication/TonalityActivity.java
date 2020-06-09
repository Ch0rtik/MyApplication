package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TonalityActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SCALE_NUMBER = "com.example.application.example.EXTRA_NUMBER";

    private int reqCode;

    private View[] menuView = new View[7];
    private TextView[] highText = new TextView[7];
    private int[] highScore = new int[7];

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IONIAN_HIGH = "ionianHigh";
    public static final String DORIAN_HIGH = "dorianHigh";
    public static final String PHRYGIAN_HIGH = "phrigianHigh";
    public static final String LYDIAN_HIGH = "lydianHigh";
    public static final String MIXOLYDIAN_HIGH = "mixolydianHigh";
    public static final String AEOLIAN_HIGH = "aeolianHigh";
    public static final String LOCRIAN_HIGH = "locrianHigh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonality);

        menuView[0] = findViewById(R.id.ionianView);
        menuView[1] = findViewById(R.id.dorianView);
        menuView[2] = findViewById(R.id.phrygianView);
        menuView[3] = findViewById(R.id.lydianView);
        menuView[4] = findViewById(R.id.mixolydianView);
        menuView[5] = findViewById(R.id.aeolianView);
        menuView[6] = findViewById(R.id.locrianView);

        highText[0] = findViewById(R.id.ionianHigh);
        highText[1] = findViewById(R.id.dorianHigh);
        highText[2] = findViewById(R.id.phrygianHigh);
        highText[3] = findViewById(R.id.lydianHigh);
        highText[4] = findViewById(R.id.mixolydianHigh);
        highText[5] = findViewById(R.id.aeolianHigh);
        highText[6] = findViewById(R.id.locrianHigh);


        loadData();
        updateViews();

        menuView[0].setOnClickListener(this);
        menuView[1].setOnClickListener(this);
        menuView[2].setOnClickListener(this);
        menuView[3].setOnClickListener(this);
        menuView[4].setOnClickListener(this);
        menuView[5].setOnClickListener(this);
        menuView[6].setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(TonalityActivity.this, QuizActivity.class);
        switch (v.getId()) {
            case R.id.ionianView:

                reqCode = 0;
                break;

            case R.id.dorianView:

                reqCode = 1;
                break;

            case R.id.phrygianView:

                reqCode = 2;
                break;

            case R.id.lydianView:

                reqCode = 3;
                break;

            case R.id.mixolydianView:

                reqCode = 4;
                break;

            case R.id.aeolianView:

                reqCode = 5;
                break;

            case R.id.locrianView:

                reqCode=6;


        }

        intent.putExtra(SCALE_NUMBER, reqCode);
        intent.putExtra(MenuActivity.QUIZ_TYPE, 3);
        startActivityForResult(intent, reqCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String highName = "";

        int result;

        switch (requestCode) {
            case 0:
                highName = IONIAN_HIGH;
                break;
            case 1:
                highName = DORIAN_HIGH;
                break;
            case 2:
                highName = PHRYGIAN_HIGH;
                break;
            case 3:
                highName = LYDIAN_HIGH;
                break;
            case 4:
                highName = MIXOLYDIAN_HIGH;
                break;
            case 5:
                highName = AEOLIAN_HIGH;
                break;
            case 6:
                highName = LOCRIAN_HIGH;
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

        highScore[0] = sharedPreferences.getInt(IONIAN_HIGH, 0);
        highScore[1] = sharedPreferences.getInt(DORIAN_HIGH, 0);
        highScore[2] = sharedPreferences.getInt(PHRYGIAN_HIGH, 0);
        highScore[3] = sharedPreferences.getInt(LYDIAN_HIGH, 0);
        highScore[4] = sharedPreferences.getInt(MIXOLYDIAN_HIGH, 0);
        highScore[5] = sharedPreferences.getInt(AEOLIAN_HIGH, 0);
        highScore[6] = sharedPreferences.getInt(LOCRIAN_HIGH, 0);

    }

    public void updateViews() {

        for (int i = 0; i < 7; i++) {

            highText[i].setText(String.format(getResources().getString(R.string.high_text), String.valueOf(highScore[i])));

        }

    }
}