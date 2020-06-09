package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private boolean[] chordNoteTaken = new boolean[4];
    private boolean[] intervalTaken = new boolean[11];
    private boolean[] buttonTaken = new boolean[9];
    private boolean[] buttonPressed = new boolean[9];
    private boolean answeredWrong;
    private int buttonPressedAmount = 0;

    private int noteNumber;
    private int noteSkip;

    private final int questNumb = 10;
    private final int intervalSkip = 7;

    private int prevNote;
    private int score;

    private int quizType;
    private int chordQuizType;

    private int mInterval;

    private int chordLevel;
    private boolean thirdType;
    private boolean seventhType;
    private boolean any;

    private int[] buttonValue = new int[4];
    private int[] noteValue = new int[4];

    private Button[] ansButton = new Button[9];
    private Button submitButton;
    private LinearLayout submitLayout;
    private boolean[] rightChordButton = new boolean[9];

    private String[] noteNames;
    private String[] intervalNames;
    private LinearLayout[] ansLayout = new LinearLayout[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ansButton[0] = findViewById(R.id.answerButton0);
        ansButton[1] = findViewById(R.id.answerButton1);
        ansButton[2] = findViewById(R.id.answerButton2);
        ansButton[3] = findViewById(R.id.answerButton3);
        ansButton[4] = findViewById(R.id.answerButton4);
        ansButton[5] = findViewById(R.id.answerButton5);
        ansButton[6] = findViewById(R.id.answerButton6);
        ansButton[7] = findViewById(R.id.answerButton7);
        ansButton[8] = findViewById(R.id.answerButton8);

        submitButton = findViewById(R.id.submitButton);

        ansLayout[0] = findViewById(R.id.answerLayout0);
        ansLayout[1] = findViewById(R.id.answerLayout1);
        ansLayout[2] = findViewById(R.id.answerLayout2);
        ansLayout[3] = findViewById(R.id.answerLayout3);
        ansLayout[4] = findViewById(R.id.answerLayout4);
        ansLayout[5] = findViewById(R.id.answerLayout5);
        ansLayout[6] = findViewById(R.id.answerLayout6);
        ansLayout[7] = findViewById(R.id.answerLayout7);
        ansLayout[8] = findViewById(R.id.answerLayout8);

        submitLayout = findViewById(R.id.submitLayout);

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

        for (int i = 4; i < 9; i++) {
            ansButton[i].setVisibility(View.INVISIBLE);
            ansButton[i].setClickable(false);
            ansLayout[i].setVisibility(View.INVISIBLE);
        }

        submitButton.setVisibility(View.INVISIBLE);
        submitButton.setClickable(false);
        submitLayout.setVisibility(View.INVISIBLE);

        chordQuizType = 0;

        switch (quizType) {
            case 0:

                mInterval = intent.getIntExtra(IntervalActivity.INTERVAL_NUMBER, 0);
                startIntervalQuiz();
                break;

            case 1:
                any = false;

                chordQuizType = intent.getIntExtra(ChordActivity.CHORD_TYPE, 0);
                switch (chordQuizType) {
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
                        any = true;
                    default:
                        chordLevel = 1;
                }
                if (intent.getIntExtra(ChordActivity.CHORD_TYPE, 0) == 7) {
                    startChordQuiz2();
                } else {
                    startChordQuiz();
                }
                break;
            case 2:
                startIntervalQuiz2();
        }


        ansButton[0].setOnClickListener(this);
        ansButton[1].setOnClickListener(this);
        ansButton[2].setOnClickListener(this);
        ansButton[3].setOnClickListener(this);
        ansButton[4].setOnClickListener(this);
        ansButton[5].setOnClickListener(this);
        ansButton[6].setOnClickListener(this);
        ansButton[7].setOnClickListener(this);
        ansButton[8].setOnClickListener(this);
        submitButton.setOnClickListener(this);

    }

    private void setNoteNames() {

        SharedPreferences sharedPreferences = getSharedPreferences(IntervalActivity.SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(NOTE_NAMES, false)) {

            noteNames = getResources().getStringArray(R.array.note_names_do);

        } else {

            noteNames = getResources().getStringArray(R.array.note_names);

        }

    }

    private void startIntervalQuiz2() {

        for (int i = 0; i < 11; i++) {
            intervalTaken[i] = false;
        }

        intervalNames = getResources().getStringArray(R.array.interval_names_2);
        int note;
        int note2;
        do note = new Random().nextInt(noteNumber - 1) + noteSkip; while (note == prevNote);

        mInterval = new Random().nextInt(11) - 5;
        intervalTaken[5 + mInterval] = true;
        intervalTaken[5 - mInterval] = true;

        note2 = note + mInterval;

        noteText.setText(String.format(getResources().getString(R.string.two_notes), noteNames[note], noteNames[note2]));
        intervalText.setText(R.string.interval);
        questText.setText(getResources().getString(R.string.choose));

        prevNote = note;

        rightButton = new Random().nextInt(4);

        ansButton[rightButton].setText(intervalNames[mInterval + intervalSkip]);

        for (int i = 0; i < 4; i++) {
            if (i != rightButton) {
                do {
                    buttonValue[i] = new Random().nextInt(11) - 5;
                } while (intervalTaken[buttonValue[i] + 5]);
                intervalTaken[buttonValue[i] + 5] = true;
                ansButton[i].setText(intervalNames[buttonValue[i] + intervalSkip]);
            }
        }

    }

    private void startChordQuiz() {

        intervalText.setText(R.string.chord);

        for (int i = 0; i < 4; i++) {
            chordNoteTaken[i] = false;
        }

        for (int i = 0; i < 4; i++) {
            noteValue[i] = 0;
        }

        int note;
        do note = new Random().nextInt(noteNumber) + noteSkip; while (note == prevNote);

        if (any) {
            thirdType = new Random().nextBoolean();
            seventhType = new Random().nextBoolean();
        }

        noteValue[0] = note;
        prevNote = note;

        String chordType = "";
        String[] chordTypes = getResources().getStringArray(R.array.chord_types);


        if (thirdType) {
            noteValue[1] = noteValue[0] + 4;

        } else {
            noteValue[1] = noteValue[0] - 3;
            chordType += "m";
            if (chordLevel > 0) {
                chordType += "in";
            }
        }

        noteValue[2] = noteValue[0] + 1;

        if (chordLevel > 0) {
            if (seventhType) {
                noteValue[3] = noteValue[0] + 5;
                chordType += "Maj";
            } else {
                noteValue[3] = noteValue[0] - 2;
            }
            chordType += "7";
        } else {
            ansButton[3].setVisibility(View.INVISIBLE);
            ansButton[3].setClickable(false);
            ansLayout[3].setVisibility(View.INVISIBLE);
        }

        String taskName;
        taskName = "";
        int noteNumber;

        for (int i = 0; i < 3 + chordLevel; i++) {

            do {

                noteNumber = new Random().nextInt(3 + chordLevel);

            } while (chordNoteTaken[noteNumber]);

            taskName += noteNames[noteValue[noteNumber]];
            chordNoteTaken[noteNumber] = true;

            if (i < 2 + chordLevel) {

                taskName += ", ";

            }

        }

        noteText.setText(taskName);

        for (int i = 0; i < 4; i++) {
            chordNoteTaken[i] = false;
        }

        for (int i = 0; i < 3 + chordLevel; i++) {

            do {

                noteNumber = new Random().nextInt(3 + chordLevel);

            } while (chordNoteTaken[noteNumber]);

            chordNoteTaken[noteNumber] = true;
            if (any) {
                if (noteValue[noteNumber] == note) {

                    ansButton[i].setText(noteNames[noteValue[noteNumber]] + chordType);

                } else {

                    int randomChordType = new Random().nextInt(4);
                    ansButton[i].setText(noteNames[noteValue[noteNumber]] + chordTypes[randomChordType]);

                }
            } else {
                ansButton[i].setText(noteNames[noteValue[noteNumber]] + chordType);
            }


            if (noteValue[noteNumber] == note) {
                rightButton = i;
            }

        }
    }

    private void startIntervalQuiz() {

        for (int i = 0; i < 21; i++) {
            noteTaken[i] = false;
        }


        int note;
        do note = new Random().nextInt(noteNumber - 1) + noteSkip; while (note == prevNote);

        noteTaken[note] = true;

        if (note > 6) {
            noteTaken[note - 7] = true;
            if (note > 13) {
                noteTaken[note - 13] = true;
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
                    buttonValue[i] = new Random().nextInt(21);
                } while (noteTaken[buttonValue[i]]);
                noteTaken[buttonValue[i]] = true;
                ansButton[i].setText(noteNames[buttonValue[i]]);
            }
        }
    }

    private void startChordQuiz2() {
        for (int i = 4; i < 9; i++) {
            ansButton[i].setVisibility(View.VISIBLE);
            ansButton[i].setClickable(true);
            ansLayout[i].setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < 21; i++) {
            noteTaken[i] = false;
        }

        for (int i = 0; i < 9; i++) {
            buttonTaken[i] = false;
            rightChordButton[i] = false;
        }

        for (int i = 0; i < 4; i++) {
            chordNoteTaken[i] = false;
        }

        for (int i = 0; i < 4; i++) {
            noteValue[i] = 0;
        }

        int note;
        do note = new Random().nextInt(noteNumber) + noteSkip; while (note == prevNote);

        thirdType = new Random().nextBoolean();
        seventhType = new Random().nextBoolean();


        noteValue[0] = note;
        prevNote = note;

        String chordType = "";


        if (thirdType) {
            noteValue[1] = noteValue[0] + 4;

        } else {
            noteValue[1] = noteValue[0] - 3;
            chordType += "m";
            if (chordLevel > 0) {
                chordType += "in";
            }
        }

        noteValue[2] = noteValue[0] + 1;

        if (chordLevel > 0) {
            if (seventhType) {
                noteValue[3] = noteValue[0] + 5;
                chordType += "Maj";
            } else {
                noteValue[3] = noteValue[0] - 2;
            }
            chordType += "7";
        }

        questText.setText(R.string.build);
        intervalText.setText(String.format(getResources().getString(R.string.chord_note), chordType));

        noteText.setText(noteNames[note]);

        for (int i = 0; i < 4; i++) {
            chordNoteTaken[i] = false;
        }

        if (noteValue[0] > 6) {
            noteTaken[noteValue[0] - 7] = true;
            if (noteValue[0] > 13) {
                noteTaken[noteValue[0] - 14] = true;
            }
        }

        int buttonNumber;

        for (int i = 0; i < 4; i++) {
            do {

                buttonNumber = new Random().nextInt(9);

            } while (buttonTaken[buttonNumber]);

            noteTaken[noteValue[i]] = true;

            buttonTaken[buttonNumber] = true;
            ansButton[buttonNumber].setText(noteNames[noteValue[i]]);

            rightChordButton[buttonNumber] = true;
        }

        int randomNoteNumber;

        for (int i = 0; i < 9; i++) {
            if (!buttonTaken[i]) {
                do {
                    randomNoteNumber = new Random().nextInt(21);
                } while (noteTaken[randomNoteNumber]);

                noteTaken[randomNoteNumber] = true;

                ansButton[i].setText(noteNames[randomNoteNumber]);
            }
        }


    }

    private void Check(int playerButton) {

        if (chordQuizType == 7) {

            if (buttonPressed[playerButton]) {

                ansButton[playerButton].setTextColor(Color.BLACK);
                ansButton[playerButton].setBackgroundColor(Color.WHITE);
                ansLayout[playerButton].setBackgroundResource(R.color.colorPrimary);

                buttonPressed[playerButton] = false;

            } else {
                if (buttonPressedAmount < 4) {
                    ansButton[playerButton].setTextColor(Color.WHITE);
                    ansButton[playerButton].setBackgroundResource(R.color.colorPrimary);
                    ansLayout[playerButton].setBackgroundColor(Color.WHITE);

                    buttonPressed[playerButton] = true;

                    buttonPressedAmount++;
                }
            }

            if (buttonPressedAmount == 4) {
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setClickable(true);
                submitLayout.setVisibility(View.VISIBLE);
            } else {
                submitButton.setVisibility(View.INVISIBLE);
                submitButton.setClickable(false);
                submitLayout.setVisibility(View.INVISIBLE);
            }

        } else {


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
                switch (quizType) {
                    case 0:
                        startIntervalQuiz();
                        break;
                    case 1:
                        startChordQuiz();
                        break;
                    case 2:
                        startIntervalQuiz2();

                }

            }
        }

    }

    private void Check2() {
        for (int i = 0; i < 9; i++) {

            answeredWrong = false;

            if (rightChordButton[i] != buttonPressed[i]) {
                answeredWrong = true;
            }
        }

        if (!answeredWrong) {

            score++;
        }
        currentQuestNumb++;

        if (currentQuestNumb > questNumb - 1) {

            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", score);
            setResult(RESULT_OK, resultIntent);
            finish();

        } else {

            for (int i = 0; i < 9; i++) {

                ansButton[i].setTextColor(Color.BLACK);
                ansButton[i].setBackgroundColor(Color.WHITE);
                ansLayout[i].setBackgroundResource(R.color.colorPrimary);

                buttonPressed[i] = false;

            }

            submitButton.setVisibility(View.INVISIBLE);
            submitButton.setClickable(false);
            submitLayout.setVisibility(View.INVISIBLE);
            buttonPressedAmount = 0;

            numText.setText(String.format(getResources().getString(R.string.quest_num), String.valueOf(currentQuestNumb), String.valueOf(questNumb)));
            scoreText.setText(String.format(getResources().getString(R.string.score_num), String.valueOf(score)));
            startChordQuiz2();
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
                break;

            case R.id.answerButton4:

                Check(4);
                break;

            case R.id.answerButton5:

                Check(5);
                break;

            case R.id.answerButton6:

                Check(6);
                break;

            case R.id.answerButton7:

                Check(7);
                break;

            case R.id.answerButton8:

                Check(8);
                break;

            case R.id.submitButton:

                Check2();

        }
    }
}