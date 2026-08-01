package com.example.vitafit.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitafit.R;

public class spash_screen extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        // Check if user is logged in
        SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        // Delay for splash screen animation, then navigate based on login status
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isLoggedIn) {
                    // If logged in, go directly to the user dashboard
                    intent = new Intent(spash_screen.this, UserDashboard.class);
                } else {
                    // If not logged in, go to the login screen
                    intent = new Intent(spash_screen.this, Login.class);
                }
                startActivity(intent);
                finish();
            }
        };

        // Delay to simulate splash screen duration (adjust time as needed)
        handler.postDelayed(runnable, 3900);  // Adjust the time as needed
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}