package com.example.dmitriy.millionaire.rest;

import com.example.dmitriy.millionaire.rest.Retrofit2Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dmitriy on 17.04.16.
 */
public class Retrofit2Config {
    private static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-37-184-161.us-west-2.compute.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit2Api getService(){
        return getRetrofit().create(Retrofit2Api.class);
    }
}
