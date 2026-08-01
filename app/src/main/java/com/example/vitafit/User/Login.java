package com.example.vitafit.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.example.vitafit.Sessions.LoginSessionManagement;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextView loginSignupTxt;
    Button loginBtn;
    private DbHelper dbHelper;
    TextInputEditText edtLoginEmail, edtLoginPassword;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is already logged in
        LoginSessionManagement session = new LoginSessionManagement(getApplicationContext());
        if (session.isLoggedIn()) {
            // Redirect to the UserDashboard if logged in
            Intent intent = new Intent(Login.this, UserDashboard.class);
            startActivity(intent);
            finish(); // Close the Login activity
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // Hooks
        loginSignupTxt = findViewById(R.id.login_signup_txt);
        loginBtn = findViewById(R.id.user_login);
        edtLoginEmail = findViewById(R.id.login_email);
        edtLoginPassword = findViewById(R.id.login_password);

        // Initialize DbHelper
        dbHelper = new DbHelper(this);

        // Login button click listener
        loginBtn.setOnClickListener(v -> userLogin());

        // Signup text click listener
        loginSignupTxt.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, user_register.class);
            startActivity(intent);
        });
    }

    // Validate email input
    private Boolean validateEmail() {
        String email = Objects.requireNonNull(edtLoginEmail.getText()).toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            edtLoginEmail.setError("Email is required");
            return false;
        } else if (!email.matches(emailPattern)) {
            edtLoginEmail.setError("Invalid Email");
            return false;
        } else {
            edtLoginEmail.setError(null);
            return true;
        }
    }

    // Validate password input
    private Boolean validatePassword() {
        String pass = Objects.requireNonNull(edtLoginPassword.getText()).toString();
        if (pass.isEmpty()) {
            edtLoginPassword.setError("Please Enter Your Password");
            return false;
        } else {
            edtLoginPassword.setError(null);
            return true;
        }
    }

    // Handle user login
    public void userLogin() {
        String email = Objects.requireNonNull(edtLoginEmail.getText()).toString();
        String pass = Objects.requireNonNull(edtLoginPassword.getText()).toString();
        LoginSessionManagement session = new LoginSessionManagement(this);

        if (!validateEmail() || !validatePassword()) {
            return; // Stop if validation fails
        }

        // Check if the email exists in the database
        boolean checkEmail = dbHelper.checkEmail(email);
        if (checkEmail) {
            // Check if the email and password match
            boolean checkEmailPassword = dbHelper.checkEmailPassword(email, pass);
            if (checkEmailPassword) {
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Set login state to true and store user email
//                session.setLogin(true, email);

                // Navigate to UserDashboard
                Intent intent = new Intent(Login.this, UserDashboard.class);
                startActivity(intent);
                finish(); // Close the Login activity
            } else {
                edtLoginPassword.setError("Incorrect Password");
            }
        } else {
            edtLoginEmail.setError("No User Found");
        }
    }
}