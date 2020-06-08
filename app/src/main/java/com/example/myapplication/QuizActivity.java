package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String NOTE_NAMES = "noteNames";


    private int rightButton;
    private int currentQuestNumb;
    private boolean[] noteTaken = new boolean[21];
    private boolean[] buttonTaken = new boolean[4];
    private boolean[] chordNoteTaken = new boolean[4];

    private int noteNumber;
    private int noteSkip;

    private final int questNumb = 10;
    private final int intervalSkip = 7;

    private int prevNote;
    private int score;

    private int quizType;

    private int mInterval;

    private int chordLevel;
    private boolean thirdType;
    private boolean seventhType;


    private int[] buttonValue = new int[4];
    private int[] noteValue = new int[4];

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

        intervalNames = getResources().getStringArray(R.array.interval_names);

        score = 0;
        currentQuestNumb = 0;
        prevNote = -1;

        Intent intent = getIntent();


        noteNumber = 7;
        noteSkip = 7;

        numText.setText(String.format(getResources().getString(R.string.quest_num), String.valueOf(currentQuestNumb), String.valueOf(questNumb)));
        scoreText.setText(String.format(getResources().getString(R.string.score_num), String.valueOf(score)));

        setNoteNames();

        quizType = intent.getIntExtra(MenuActivity.QUIZ_TYPE, 0);

        switch (quizType) {
            case 0:

                mInterval = intent.getIntExtra(IntervalActivity.INTERVAL_NUMBER, 0);
                startIntervalQuiz();
                break;

            case 1:

                switch (intent.getIntExtra(ChordActivity.CHORD_TYPE, 0)) {
                    case 0:
                        chordLevel = 0;
                        thirdType = true;
                        break;
                    case 1:
                        chordLevel = 0;
                        thirdType = false;
                        break;
                    case 2:
                        chordLevel = 1;
                        thirdType = true;
                        seventhType = true;
                        break;
                    case 3:
                        chordLevel = 1;
                        thirdType = false;
                        seventhType = false;
                        break;
                    case 4:
                        chordLevel = 1;
                        thirdType = true;
                        seventhType = false;
                        break;
                    case 5:
                        chordLevel = 1;
                        thirdType = false;
                        seventhType = true;
                        break;
                    case 6:
                        chordLevel = 1;
                }

                startChordQuiz();
                break;

        }


        ansButton[0].setOnClickListener(this);
        ansButton[1].setOnClickListener(this);
        ansButton[2].setOnClickListener(this);
        ansButton[3].setOnClickListener(this);

    }

    private void setNoteNames() {

        SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(NOTE_NAMES, false)) {

            noteNames = getResources().getStringArray(R.array.note_names_do);

        } else {

            noteNames = getResources().getStringArray(R.array.note_names);

        }

    }

    private void startIntervalQuiz() {

        for (int i = 0; i < 21; i++) {
            noteTaken[i] = false;
        }


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

    private void startChordQuiz() {

        for (int i = 0; i < 4; i++) {
            buttonTaken[i] = false;
        }

        for (int i = 0; i < 4; i++) {
            chordNoteTaken[i] = false;
        }

        int note;
        do note = new Random().nextInt(noteNumber - 1) + noteSkip; while (note == prevNote);

        noteValue[0] = note;
        prevNote = noteValue[0];

        String chordType = "";

        if (thirdType) {
            noteValue[1] = noteValue[0] + 4;

        } else {
            noteValue[1] = noteValue[0] - 3;
            if(chordLevel>0){
                chordType+="min";
                if(!seventhType){
                    chordType+="7";
                }
            }else{
                chordType+="m";
            }
        }

        noteValue[2] = noteValue[0] + 1;
        if (chordLevel > 0) {
            if (seventhType) {
                noteValue[3] = noteValue[0] + 5;
                chordType+="maj7";
            } else {
                noteValue[3] = noteValue[0] - 2;
                if(thirdType){
                    chordType+=7;
                }
            }
        }

        String taskName = "";
        int noteNumber;

        for (int i = 0; i < 3 + chordLevel; i++) {

            do {

                noteNumber = new Random().nextInt(4);

            } while (chordNoteTaken[noteNumber]);

            taskName += String.valueOf(noteValue[noteNumber]);

            if (i < 2 + chordLevel) {

                taskName += ", ";

            }

        }

        noteText.setText(taskName);

        int buttonNumber;
        for (int i = 0; i < 3 + chordLevel; i++) {

            do {

                buttonNumber = new Random().nextInt(4);

            } while (buttonTaken[buttonNumber]);

            ansButton[buttonNumber].setText(noteNames[noteValue[i]]+chordType);

            if (noteValue[i] == note) {

                rightButton = buttonNumber;

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
            startIntervalQuiz();

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
