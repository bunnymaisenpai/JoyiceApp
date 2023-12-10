package com.example.myapplication.api;

import com.example.myapplication.CreateReport;
import com.example.myapplication.SignIn.UserResponse;
import com.example.myapplication.SignUp.CreateAccount;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface ApiEndpoints {
    @POST("app/application-0-gvilg/endpoint/SignUp")
    Call<CreateAccount> CreateAccount(@Body RequestBody requestBody);

    @POST("app/application-0-gvilg/endpoint/SignIn")
    Call<UserResponse> signinUser(@Body RequestBody requestBody);

    @POST("app/application-0-gvilg/endpoint/CreateReport")
    Call<CreateReport> CreateReport(@Body RequestBody requestBody);

}

