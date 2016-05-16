package com.example.dmitriy.millionaire.rest;

import com.example.dmitriy.millionaire.models.GameQuestions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dmitriy on 17.04.16.
 */
public interface Retrofit2Api {
    @GET("/api/game/")
    Call<ArrayList<GameQuestions>> getGameQuestions();
}
