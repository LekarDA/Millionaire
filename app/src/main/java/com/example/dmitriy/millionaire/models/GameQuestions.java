package com.example.dmitriy.millionaire.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dmitriy on 17.04.16.
 */
public class GameQuestions {
    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("answers")
    private ArrayList<Answers> answers;

    public ArrayList<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
