package com.example.multiplechoicetestapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText enterName, enterAge;
    private Button startTestButton, historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterName = findViewById(R.id.enterName);
        enterAge = findViewById(R.id.enterid);
        startTestButton = findViewById(R.id.startTestButton);
        historyButton = findViewById(R.id.historyButton);

        // Load previously entered name (if available)
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedName = preferences.getString("userName", "");
        enterName.setText(savedName);

        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = enterName.getText().toString().trim();
                String age = enterAge.getText().toString().trim();

                if (name.isEmpty() || age.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name and id", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save name for later retrieval
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userName", name);
                editor.apply();

                // Start TestActivity
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open HistoryActivity to view past test records
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
