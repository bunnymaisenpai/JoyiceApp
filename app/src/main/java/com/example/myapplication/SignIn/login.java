package com.example.myapplication.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiEndpoints;
import com.example.myapplication.dashboard;
import com.example.myapplication.SignUp.register;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    private AppCompatButton signIn;
    private TextView signUp;
    private EditText username, password;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isUserLoggedIn()) {
            // Redirect to the dashboard
            startActivity(new Intent(this, dashboard.class));
            finish();
        }


        String formattedText = getString(R.string.text_with_colors);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(formattedText);

        ForegroundColorSpan grayColorSpan = new ForegroundColorSpan(Color.parseColor("#818183"));
        spannableStringBuilder.setSpan(grayColorSpan, 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan greenColorSpan = new ForegroundColorSpan(Color.parseColor("#1C360D"));
        spannableStringBuilder.setSpan(greenColorSpan, 23, formattedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView myTextView = findViewById(R.id.textView6);
        myTextView.setText(spannableStringBuilder);

        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        signIn = findViewById(R.id.login);
        signUp = findViewById(R.id.textView6);
        progress = findViewById(R.id.progressbar);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUsername = username.getText().toString().trim();
                String getPassword = password.getText().toString().trim();
                checkUsernameAndPassword(getUsername, getPassword);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.splash_fade_out);

            }
        });
    }


    private void checkUsernameAndPassword(String username, String password) {
        if (username.isEmpty()) {
            Toast.makeText(login.this, "Please enter your username", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } else if (!username.isEmpty() && !password.isEmpty()) {
            validateCredentials(username, password);
        }
    }

    private void validateCredentials(String username, String password) {
        loading(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-east-1.aws.data.mongodb-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints apiEndpoints = retrofit.create(ApiEndpoints.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<UserResponse> call = apiEndpoints.signinUser(requestBody);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse createResponse = response.body();
                if (response.code() == 200) {
                    String userId = createResponse.getId();
                    String firstname = createResponse.getFirstname();
                    String lastname = createResponse.getLastname();
                    String address = createResponse.getAddress();
                    String username = createResponse.getUsername();
                    String contactNumber = createResponse.getContactNumber();

                    setLoggedInStatus(true);
                    loading(false);
                    storeUserData(userId, firstname, lastname, address, username, contactNumber);
                    Toast.makeText(login.this, "Successfully login", Toast.LENGTH_SHORT).show();

                    // Finish the current SignIn activity
                    finish();
                    startActivity(new Intent(login.this, dashboard.class));
                    overridePendingTransition(0, R.anim.splash_fade_out);

                } else {
                    setLoggedInStatus(false);
                    Toast.makeText(login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(login.this, "Server failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeUserData(String userId, String firstname, String lastname, String address, String username, String contactNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userId", userId);
        editor.putString("fname", firstname);
        editor.putString("lname", lastname);
        editor.putString("address", address);
        editor.putString("username", username);
        editor.putString("cnumber", contactNumber);

        editor.apply();
    }

    private void setLoggedInStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            signIn.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.INVISIBLE);
        }
    }
}
