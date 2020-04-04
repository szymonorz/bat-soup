package com.simon.android.batsoup.interfaces;

import com.simon.android.batsoup.model.JwtResponse;
import com.simon.android.batsoup.model.LoginForm;
import com.simon.android.batsoup.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("test")
    Call<List<User>> findUsers();

    @POST("loginUser")
    Call<JwtResponse> loginUser(@Body LoginForm loginForm);
}
