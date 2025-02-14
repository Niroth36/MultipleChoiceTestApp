package com.example.multiplechoicetestapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class TestActivity extends AppCompatActivity {

    private TextView questionText, timerText;
    private ImageView questionImage;
    private RadioGroup optionsGroup;
    private Button submitButton;
    private CountDownTimer countDownTimer;

    private String userName, userid;
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private ArrayList<String> incorrectAnswers = new ArrayList<>();
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Get data from MainActivity
        Intent intent = getIntent();
        userName = intent.getStringExtra("name");
        userid = intent.getStringExtra("id");

        // Initialize UI elements
        questionText = findViewById(R.id.questionText);
        questionImage = findViewById(R.id.questionImage);
        timerText = findViewById(R.id.timerText);
        optionsGroup = findViewById(R.id.optionsGroup);
        submitButton = findViewById(R.id.submitButton);

        // Start tracking time
        startTime = System.currentTimeMillis();

        // Set up the questions
        questions = generateQuestions();
        Collections.shuffle(questions);

        // Display the first question
        displayQuestion();

        // Start the countdown timer
        startTimer();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton selectedOption = findViewById(optionsGroup.getCheckedRadioButtonId());
                if (selectedOption != null) {
                    String selectedAnswer = selectedOption.getText().toString();
                    Question currentQuestion = questions.get(currentQuestionIndex);

                    if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                        score++; // Correct answer
                    } else {
                        // Store incorrect question and correct answer
                        incorrectAnswers.add("Q: " + currentQuestion.getQuestionText() +
                                "\nYour Answer: " + selectedAnswer +
                                "\nCorrect Answer: " + currentQuestion.getCorrectAnswer() + "\n");
                    }
                }

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    // End the test and show the result
                    endTest();
                }
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(5 * 60 * 1000, 1000) { // 5 minutes countdown
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerText.setText(String.format("Time left: %02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                endTest(); // Auto-submit when time runs out
            }
        };
        countDownTimer.start();
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionText.setText(question.getQuestionText());

        // Display image if available
        if (question.getImageResId() != 0) {
            questionImage.setImageResource(question.getImageResId());
            questionImage.setVisibility(View.VISIBLE);
        } else {
            questionImage.setVisibility(View.GONE);
        }

        // Clear previous options
        optionsGroup.removeAllViews();

        // Add options dynamically
        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsGroup.addView(radioButton);
        }
    }


    private void endTest() {
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000;

        // Save test history using SharedPreferences
        SharedPreferences prefs = getSharedPreferences("TestHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String history = prefs.getString("history", ""); // Load existing history
        String newEntry = "Name: " + userName +
                "\nTime Taken: " + timeTaken + " sec" +
                "\nScore: " + score + "/" + questions.size() + "\n\n";

        editor.putString("history", newEntry + history); // Append new record
        editor.apply();


        // Pass score and incorrect answers to ResultActivity
        Intent resultIntent = new Intent(TestActivity.this, ResultActivity.class);
        resultIntent.putExtra("score", score);
        resultIntent.putExtra("total", questions.size());
        resultIntent.putStringArrayListExtra("incorrectAnswers", incorrectAnswers);
        startActivity(resultIntent);
        finish();
    }

    private ArrayList<Question> generateQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();

        questionList.add(new Question("Who won the 2022 FIFA World Cup?", "Argentina",
                new String[]{"Brazil", "Argentina", "France"}, 0));

        questionList.add(new Question("Who is this footballer?", "Lionel Messi",
                new String[]{"Cristiano Ronaldo", "Lionel Messi", "Neymar"}, R.drawable.messi));

        questionList.add(new Question("What is the capital of France?", "Paris",
                new String[]{"Berlin", "Paris", "Madrid"}, 0));

        questionList.add(new Question("Which planet is closest to the Sun?", "Mercury",
                new String[]{"Venus", "Mercury", "Mars"}, 0));

        questionList.add(new Question("Which is the largest ocean?", "Pacific Ocean",
                new String[]{"Indian Ocean", "Atlantic Ocean", "Pacific Ocean"}, 0));

        questionList.add(new Question("What is the chemical symbol for Gold?", "Au",
                new String[]{"Ag", "Au", "Pb"}, 0));

        questionList.add(new Question("What year did World War 2 end?", "1945",
                new String[]{"1918", "1945", "1939"}, 0));

        questionList.add(new Question("What is the capital of Japan?", "Tokyo",
                new String[]{"Seoul", "Tokyo", "Beijing"}, 0));

        questionList.add(new Question("Which programming language is used for Android?", "Java",
                new String[]{"Python", "Java", "C++"}, 0));

        questionList.add(new Question("Which is the smallest prime number?", "2",
                new String[]{"1", "2", "3"}, 0));

        // Shuffle and pick 5 questions
        Collections.shuffle(questionList);
        return new ArrayList<>(questionList.subList(0, 5));
    }
}
