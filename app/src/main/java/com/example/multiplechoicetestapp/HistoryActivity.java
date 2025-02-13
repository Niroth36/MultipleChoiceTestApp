package com.example.multiplechoicetestapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    private TextView historyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Find TextView for displaying test history
        historyText = findViewById(R.id.historyText);

        // Load test history from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("TestHistory", MODE_PRIVATE);
        String history = prefs.getString("history", "No test history found.");

        // Display test history
        historyText.setText(history);
    }
}
