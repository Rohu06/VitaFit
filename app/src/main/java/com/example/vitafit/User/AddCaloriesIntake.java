package com.example.vitafit.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

public class AddCaloriesIntake extends AppCompatActivity {

    TextView textDate,textTime;
    NumberPicker caloriesPicker;
    Button saveBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_calories_intake);

        // Hooks
        textDate = findViewById(R.id.date);
        textTime = findViewById(R.id.time);
        caloriesPicker = findViewById(R.id.calories_picker);
        saveBtn = findViewById(R.id.save_ci_btn);


        ImageView backArrow = findViewById(R.id.ci_back_btn);

        backArrow.setOnClickListener(v -> onBackPressed());

        // getting current Date and time
        Calendar c = Calendar.getInstance();

        //time
        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        String time = hour + ":" + minutes + ":" + seconds;

        //date
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = year + "-" + month + "-" + day;

        textDate.setText(date);
        textTime.setText(time);


        // getting email of the user logged in
        SharedPreferences sharedPreferences = getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();

        // getting the id of the user logged in
        DbHelper helper = new DbHelper(this);
        Cursor cursor = helper.getUserData(email);
        int id = 0;

        while(cursor.moveToNext()){
            id = cursor.getInt(0);
        }


        int finalId = id;

        saveBtn.setOnClickListener(v -> {
            DbHelper dbHelper = new DbHelper(AddCaloriesIntake.this);
            String calories = String.valueOf(caloriesPicker.getValue());

            String date1 = textDate.getText().toString();
            String time1 = textTime.getText().toString();

            boolean saveBP = dbHelper.saveCI(calories, date1, time1, finalId);
            if (saveBP) {

                // navigating activity into fragment
//                    Fragment fragment = new DynamicsFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().add(R.id.dashboard_container, fragment).commit();

                Toast.makeText(AddCaloriesIntake.this, "Calories Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HistoryCaloriesIntake.class);
                startActivity(intent);

            }else{
                Toast.makeText(AddCaloriesIntake.this, "Saving Failed", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
