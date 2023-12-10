package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.SignIn.login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isUserLoggedIn()) {
            // Redirect to the dashboard
            startActivity(new Intent(this, dashboard.class));
            overridePendingTransition( 0, R.anim.fade_out);

            finish();
        }

    }
    public void goToLogin(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        overridePendingTransition( 0, R.anim.splash_fade_out);

    }
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

}