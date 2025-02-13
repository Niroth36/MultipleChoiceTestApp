package com.example.multiplechoicetestapp;

import java.util.Arrays;
import java.util.List;

public class Question {
    private String questionText;
    private String correctAnswer;
    private List<String> options;
    private int imageResId; // Optional Image Resource

    public Question(String questionText, String correctAnswer, String[] options, int imageResId) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.options = Arrays.asList(options);
        this.imageResId = imageResId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getImageResId() {
        return imageResId;
    }
}
