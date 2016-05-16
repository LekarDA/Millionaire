package com.example.dmitriy.millionaire.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmitriy on 17.04.16.
 */
public class Answers {
    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("is_correct")
    private boolean is_correct;

    public boolean is_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
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
