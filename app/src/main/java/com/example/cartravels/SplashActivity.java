package com.example.cartravels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cartravels.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userPreferences", MODE_PRIVATE);

        int SPLASH_TIME_OUT = 2000;

        if (pref.getBoolean("LoggedIn",false)) {
            //It means User is already Logged in so I will take the user to Select_College Screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity( new Intent(SplashActivity.this, HomePage.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);

        } else {
            //It means User is not Logged in so I will take the user to Login Screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }
    }
}
