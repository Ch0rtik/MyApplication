package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INTERVAL_NUMBER = "com.example.application.example.EXTRA_NUMBER";

    private int reqCode;

    private View[] menuView = new View[6];
    private TextView[] highText = new TextView[6];
    private int[] highScore = new int[6];

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIFTH_HIGH = "fifthHigh";
    public static final String FOURTH_HIGH = "fourthHigh";
    public static final String MAJOR_THIRD_HIGH = "majorThirdHigh";
    public static final String MINOR_THIRD_HIGH = "minorThirdHigh";
    public static final String MAJOR_SEVENTH_HIGH = "MajorSeventhHigh";
    public static final String MINOR_SEVENTH_HIGH = "MinorSeventhHigh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuView[0] = findViewById(R.id.perfectFifthView);
        menuView[1] = findViewById(R.id.perfectFourthView);
        menuView[2] = findViewById(R.id.majorThirdView);
        menuView[3] = findViewById(R.id.minorThirdView);
        menuView[4] = findViewById(R.id.majorSeventhView);
        menuView[5] = findViewById(R.id.minorSeventhView);

        highText[0] = findViewById(R.id.perfectFifthHigh);
        highText[1] = findViewById(R.id.perfectFourthHigh);
        highText[2] = findViewById(R.id.majorThirdHigh);
        highText[3] = findViewById(R.id.minorThirdHigh);
        highText[4] = findViewById(R.id.majorSeventhHigh);
        highText[5] = findViewById(R.id.minorSeventhHigh);


        loadData();
        updateViews();

        menuView[0].setOnClickListener(this);
        menuView[1].setOnClickListener(this);
        menuView[2].setOnClickListener(this);
        menuView[3].setOnClickListener(this);
        menuView[4].setOnClickListener(this);
        menuView[5].setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MenuActivity.this, QuizActivity.class);
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


        }

        startActivityForResult(intent, reqCode);

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

    }

    public void updateViews() {

        for (int i = 0; i < 6; i++) {
            highText[i].setText(String.format(getResources().getString(R.string.high_text), String.valueOf(highScore[i])));
        }

    }

}