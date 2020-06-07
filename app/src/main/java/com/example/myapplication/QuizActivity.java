package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questText;
    private TextView scoreText;
    private TextView intervalText;
    private TextView noteText;
    private TextView numText;


    private int rightButton;
    private int currentQuestNumb;
    private boolean[] noteTaken = new boolean[21];

    private int noteNumber;
    private int noteSkip;

    private final int questNumb = 10;
    private final int intervalSkip = 7;

    private int prevNote;
    private int score;

    private int mInterval;

    private Button[] ansButton = new Button[4];

    private String[] noteNames;
    private String[] intervalNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ansButton[0] = findViewById(R.id.answerButton0);
        ansButton[1] = findViewById(R.id.answerButton1);
        ansButton[2] = findViewById(R.id.answerButton2);
        ansButton[3] = findViewById(R.id.answerButton3);

        questText = findViewById(R.id.questText);
        scoreText = findViewById(R.id.scoreText);

        intervalText = findViewById(R.id.intervalText);
        noteText = findViewById(R.id.noteText);
        numText = findViewById(R.id.numText);
        scoreText.setText(String.format(getResources().getString(R.string.score_num), "0"));

        noteNames = getResources().getStringArray(R.array.note_names);
        intervalNames = getResources().getStringArray(R.array.interval_names);

        score = 0;
        currentQuestNumb = 0;
        prevNote = -1;

        Intent intent = getIntent();

        mInterval = intent.getIntExtra(MenuActivity.INTERVAL_NUMBER, 0);
        noteNumber = 7;
        noteSkip = 7;

        numText.setText(String.format(getResources().getString(R.string.quest_num), String.valueOf(currentQuestNumb), String.valueOf(questNumb)));
        scoreText.setText(String.format(getResources().getString(R.string.score_num), String.valueOf(score)));

        startQuiz();

        ansButton[0].setOnClickListener(this);
        ansButton[1].setOnClickListener(this);
        ansButton[2].setOnClickListener(this);
        ansButton[3].setOnClickListener(this);

    }

    private void startQuiz() {

        for (int i = 0; i < 21; i++) {
            noteTaken[i] = false;
        }

        int[] buttonValue = new int[4];

        int note;
        do note = new Random().nextInt(noteNumber - 1) + noteSkip; while (note == prevNote);

        noteTaken[note] = true;

        if (note > 7) {
            noteTaken[note - 7] = true;
            if (note > 14) {
                noteTaken[note - 14] = true;
            }
        }

        noteText.setText(noteNames[note]);
        intervalText.setText(intervalNames[mInterval + intervalSkip]);
        questText.setText(getResources().getString(R.string.choose));
        prevNote = note;

        int rightAnswer = (note + mInterval);
        rightButton = new Random().nextInt(4);

        noteTaken[rightAnswer] = true;

        ansButton[rightButton].setText(noteNames[rightAnswer]);

        for (int i = 0; i < 4; i++) {
            if (i != rightButton) {
                do {
                    buttonValue[i] = new Random().nextInt(noteNumber) + noteSkip;
                } while (noteTaken[buttonValue[i]]);
                noteTaken[buttonValue[i]] = true;
                ansButton[i].setText(noteNames[buttonValue[i]]);
            }
        }

    }

    private void Check(int playerButton) {

        if (playerButton == rightButton) {
            score++;
        }

        currentQuestNumb++;

        if (currentQuestNumb > questNumb - 1) {

            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", score);
            setResult(RESULT_OK, resultIntent);
            finish();

        } else {

            numText.setText(String.format(getResources().getString(R.string.quest_num), String.valueOf(currentQuestNumb), String.valueOf(questNumb)));
            scoreText.setText(String.format(getResources().getString(R.string.score_num), String.valueOf(score)));
            startQuiz();

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.answerButton0:

                Check(0);
                break;

            case R.id.answerButton1:

                Check(1);
                break;

            case R.id.answerButton2:

                Check(2);
                break;

            case R.id.answerButton3:

                Check(3);

        }
    }
}
