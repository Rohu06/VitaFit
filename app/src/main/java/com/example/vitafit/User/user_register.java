package com.example.vitafit.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Objects;

public class user_register extends AppCompatActivity {
    TextView registerLogin;
    TextInputEditText edtFullName, edtEmail, edtDOB, edtHeight, edtWeight, edtPassword;
    Button registerBtn;
    private int mYear, mMonth, mDay;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // status bar hiding
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        //Hooks
        edtFullName = findViewById(R.id.register_full_name);
        edtEmail = findViewById(R.id.register_email);
        edtDOB = findViewById(R.id.register_dob);
        edtHeight = findViewById(R.id.register_height);
        edtWeight = findViewById(R.id.register_weight);
        edtPassword = findViewById(R.id.register_password);
        registerBtn = findViewById(R.id.register_btn);
        registerLogin = findViewById(R.id.register_login);
        ImageView backArrow = findViewById(R.id.signup_back_btn);

        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(user_register.this, Login.class);
            startActivity(intent);
        });


        edtHeight.setOnClickListener(v -> {

        });

        edtDOB.setOnClickListener(v -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(user_register.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            edtDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }

                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        edtDOB.setFocusable(false);

        registerBtn.setOnClickListener(v -> userRegister());

        registerLogin.setOnClickListener(v -> {
            Intent intent = new Intent(user_register.this, Login.class);
            startActivity(intent);
        });

    }

    // validations
    private Boolean validateName() {
        String name = Objects.requireNonNull(edtFullName.getText()).toString().trim();
        // Allow letters (including accented characters), spaces, hyphens, apostrophes, and periods
        String namePattern = "^[\\p{L} .'-]+$";

        if (name.isEmpty()) {
            edtFullName.setError("Name is required");
            return false;
        } else if (!name.matches(namePattern)) {
            edtFullName.setError("Invalid Name: Only letters, spaces, hyphens, apostrophes, and periods are allowed");
            return false;
        } else if (name.length() < 2 || name.length() > 50) {
            edtFullName.setError("Name must be between 2 and 50 characters");
            return false;
        } else {
            edtFullName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String email = Objects.requireNonNull(edtEmail.getText()).toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            return false;
        } else if (!email.matches(emailPattern)) {
            edtEmail.setError("Invalid Email");
            return false;
        } else {
            edtEmail.setError(null);
            return true;
        }


    }

    private Boolean validateDOB() {
        String dob = Objects.requireNonNull(edtDOB.getText()).toString().trim();

        if (dob.isEmpty()) {
            edtDOB.setError("DOB is required");
            return false;
        } else {
            // Parse the DOB into a Calendar object
            Calendar dobDate = Calendar.getInstance();
            String[] parts = dob.split("-");
            dobDate.set(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[0]));

            // Get today's date
            Calendar today = Calendar.getInstance();

            // Check if DOB is in the future
            if (dobDate.after(today)) {
                edtDOB.setError("DOB cannot be in the future");
                return false;
            }

            // Check if the user is too young (e.g., at least 13 years old)
            Calendar minAgeDate = Calendar.getInstance();
            minAgeDate.add(Calendar.YEAR, -13); // Subtract 13 years from today's date

            if (dobDate.after(minAgeDate)) {
                edtDOB.setError("You must be at least 13 years old");
                return false;
            }

            // If all checks pass
            edtDOB.setError(null);
            return true;
        }
    }



    private Boolean validateHeight() {
        String height = Objects.requireNonNull(edtHeight.getText()).toString().trim();
        if (height.isEmpty()) {
            edtHeight.setError("Height is required");
            return false;
        } else {
            try {
                double heightValue = Double.parseDouble(height);
                if (heightValue < 50 || heightValue > 250) {
                    edtHeight.setError("Height must be between 50 cm and 250 cm");
                    return false;
                }
            } catch (NumberFormatException e) {
                edtHeight.setError("Invalid height");
                return false;
            }
            edtHeight.setError(null);
            return true;
        }
    }

    private Boolean validateWeight() {
        String weight = Objects.requireNonNull(edtWeight.getText()).toString().trim();
        if (weight.isEmpty()) {
            edtWeight.setError("Weight is required");
            return false;
        } else {
            try {
                double weightValue = Double.parseDouble(weight);
                if (weightValue < 10 || weightValue > 300) {
                    edtWeight.setError("Weight must be between 10 kg and 300 kg");
                    return false;
                }
            } catch (NumberFormatException e) {
                edtWeight.setError("Invalid weight");
                return false;
            }
            edtWeight.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String pass = Objects.requireNonNull(edtPassword.getText()).toString().trim();
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (pass.isEmpty()) {
            edtPassword.setError("Password is required");
            return false;
        } else if (!pass.matches(passwordPattern)) {
            edtPassword.setError("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

    // register function
    public void userRegister() {
        DbHelper dbHelper = new DbHelper(user_register.this);
        String name = Objects.requireNonNull(edtFullName.getText()).toString().trim();
        String email = Objects.requireNonNull(edtEmail.getText()).toString().trim();
        String DOB = Objects.requireNonNull(edtDOB.getText()).toString().trim();
        String height = Objects.requireNonNull(edtHeight.getText()).toString().trim();
        String weight = Objects.requireNonNull(edtWeight.getText()).toString().trim();
        String password = Objects.requireNonNull(edtPassword.getText()).toString().trim();

        if (!validateName() || !validateEmail() || !validateDOB() || !validateHeight() || !validateWeight() || !validatePassword()) {
            return;
        } else {
            boolean checkEmail = dbHelper.checkEmail(email);
            if (checkEmail) {
                edtEmail.setError("User already Exists");
            } else {
                boolean insert = dbHelper.userRegister(name, email, DOB, height, weight, password);
                if (insert) {
                    Toast.makeText(user_register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(user_register.this, register_animation.class);
                    startActivity(intent);
                    edtFullName.setText("");
                    edtEmail.setText("");
                    edtDOB.setText("");
                    edtHeight.setText("");
                    edtWeight.setText("");
                    edtPassword.setText("");
                } else {
                    Toast.makeText(user_register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder alertExitBox = new AlertDialog.Builder(user_register.this);
        alertExitBox.setTitle("Exit?");
        alertExitBox.setIcon(R.drawable.ic_logout);
        alertExitBox.setMessage("Are you sure to Exit?");


        alertExitBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user_register.super.onBackPressed();
            }
        });
        alertExitBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(user_register.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
            }
        });


        alertExitBox.show();
    }



}