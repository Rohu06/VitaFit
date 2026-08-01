package com.example.vitafit.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitafit.R;
import com.example.vitafit.Sessions.LoginSessionManagement;

public class welcome_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Check if the user is logged in
        LoginSessionManagement session = new LoginSessionManagement(this);
        if (session.isLoggedIn()) {
            // User is already logged in, redirect to the home page
            Intent intent = new Intent(welcome_screen.this, UserDashboard.class); // Replace with your home page activity
            startActivity(intent);
            finish(); // Close the welcome screen activity
            return; // Stop further execution
        }

        // If the user is not logged in, show the login and signup options
        Button login = findViewById(R.id.welcome_login);
        TextView signup = findViewById(R.id.welcome_signup);

        login.setOnClickListener(v -> {
            Intent intent = new Intent(welcome_screen.this, Login.class);
            startActivity(intent);
            finish(); // Close the welcome screen activity
        });

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(welcome_screen.this, user_register.class);
            startActivity(intent);
            finish(); // Close the welcome screen activity
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is logged in when the activity starts
        LoginSessionManagement session = new LoginSessionManagement(this);
        if (session.isLoggedIn()) {
            // User is already logged in, redirect to the home page
            Intent intent = new Intent(welcome_screen.this, UserDashboard.class); // Replace with your home page activity
            startActivity(intent);
            finish(); // Close the welcome screen activity
        }
    }
}