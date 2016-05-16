package com.example.dmitriy.millionaire.controller;

import android.util.Log;

import com.example.dmitriy.millionaire.models.GameQuestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by dmitriy on 17.04.16.
 */
public class Controller {
    private ArrayList<GameQuestions> gameQuestions;

    public Controller(ArrayList<GameQuestions> gameQuestions) {
        this.gameQuestions = gameQuestions;
        sort();
        for (int i = 0; i < gameQuestions.size(); i++)
            Log.d("QUESTIONS", "Controller() called with: " + "gameQuestionses = [" + gameQuestions.get(i).getBody() + gameQuestions.get(i).getId() + "]");
    }

    public void sort() {
        for (GameQuestions question : gameQuestions)
            if (question.getId() == 16) {
                gameQuestions.remove(question);
                break;
            }

        Collections.sort(gameQuestions, new Comparator<GameQuestions>() {
            @Override
            public int compare(GameQuestions lhs, GameQuestions rhs) {
                return lhs.getId() - rhs.getId();
            }
        });
    }

    public GameQuestions getQuestion(int index) {
        if(index>=gameQuestions.size())return null;
        return gameQuestions.get(index);
    }
}
