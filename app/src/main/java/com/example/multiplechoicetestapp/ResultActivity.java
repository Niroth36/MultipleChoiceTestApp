package com.example.multiplechoicetestapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreText, incorrectAnswersText;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreText = findViewById(R.id.scoreText);
        incorrectAnswersText = findViewById(R.id.incorrectAnswersText);
        finishButton = findViewById(R.id.finishButton);

        // Retrieve user name
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = preferences.getString("userName", "User");

        // Retrieve score
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        // Display score
        scoreText.setText(userName + ", you scored " + score + " out of " + total);

        // Retrieve incorrect answers
        ArrayList<String> incorrectAnswers = getIntent().getStringArrayListExtra("incorrectAnswers");

        if (incorrectAnswers != null && !incorrectAnswers.isEmpty()) {
            StringBuilder incorrectText = new StringBuilder("Review Your Mistakes:\n\n");
            for (String incorrect : incorrectAnswers) {
                incorrectText.append(incorrect).append("\n");
            }
            incorrectAnswersText.setText(incorrectText.toString());
        } else {
            incorrectAnswersText.setText("Great job! No mistakes.");
        }

        // Finish button
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
