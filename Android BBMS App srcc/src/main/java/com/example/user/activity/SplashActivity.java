package com.example.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.user.R;


public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize ImageView and load animation
        imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mobile_anim);
        imageView.setAnimation(animation);
        animation.start();

        // Create a new thread to handle the delay and decide the next activity
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Delay for 2000 milliseconds (2 seconds)
                    Thread.sleep(2000);

                    // Check if the user is already logged in
                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    boolean isLoggedIn = sharedPreferences.getBoolean("login_status", false);

                    // Determine the next activity to launch based on login status
                    Class<?> nextActivity;
                    if (isLoggedIn) {
                        // If the user is logged in, launch MainActivity
                        nextActivity = MainActivity.class;
                    } else {
                        // If the user is not logged in, launch LoginActivity
                        nextActivity = LoginActivity.class;
                    }

                    // Start the next activity and finish the current activity
                    startActivity(new Intent(SplashActivity.this, nextActivity));
                    finish();
                } catch (InterruptedException e) {
                    // Throw a RuntimeException if interrupted
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
