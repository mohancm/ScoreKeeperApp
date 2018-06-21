package com.mohan.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;

    private Timer timersplash;
    private ProgressBar progressBar;
    private int i = 0;
    TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Calling layouts

        progressBar = findViewById(R.id.SplashprogressBar);
        progressBar.setProgress(0);
        splashText = findViewById(R.id.splashTimer);
        splashText.setText("");

        final long period = 50;
        timersplash = new Timer();
        timersplash.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            splashText.setText(String.valueOf(i) + "%");
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                } else {
                    //closing the timer
                    timersplash.cancel();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    finish();
                }
            }
        }, 0, period);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
