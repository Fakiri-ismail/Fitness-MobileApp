package com.example.fitnessapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(com.example.fitnessapp.SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(Login.class)
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#ff751a"))
                .withAfterLogoText("FAKIRI Ismail / M'GHARI Tariq")
                .withLogo(R.drawable.gym);

        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
