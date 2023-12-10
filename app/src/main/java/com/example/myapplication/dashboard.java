package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.myapplication.R;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class dashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    homefragment homefragment = new homefragment();
    reportfragment reportfragment = new reportfragment();
    orderfragment orderfragment = new orderfragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homefragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homenav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homefragment).commit();
                        return true;
                    case R.id.reportnav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, reportfragment).commit();
                        return true;
                    case R.id.ordernav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, orderfragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}