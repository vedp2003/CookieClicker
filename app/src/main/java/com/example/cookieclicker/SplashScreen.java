package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen splashScreen = new EasySplashScreen(SplashScreen.this)
                .withFullScreen().withTargetActivity(MainActivity.class).withSplashTimeOut(4000)
                .withBackgroundColor(Color.parseColor("#0000FF")).withBeforeLogoText("Ved's App").withAfterLogoText("Cookie Bakery").withLogo(R.drawable.back);

        splashScreen.getBeforeLogoTextView().setTextSize(30);
        splashScreen.getBeforeLogoTextView().setTextColor(Color.MAGENTA);
        splashScreen.getAfterLogoTextView().setTextSize(30);
        splashScreen.getAfterLogoTextView().setTextColor(Color.CYAN);

        View splash =  splashScreen.create();
        setContentView(splash);
    }
}