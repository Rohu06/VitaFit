package com.example.vitafit.User;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

public class AddBloodPressure extends AppCompatActivity {
    TextView textDate,textTime;
    NumberPicker systolicPicker, diastolicPicker, pulsePicker;
    Button saveBtn;
    ImageView backArrow;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_blood_pressure);

        //Hooks
        textDate = findViewById(R.id.date);
        textTime = findViewById(R.id.time);
        systolicPicker = findViewById(R.id.systolic_picker);
        diastolicPicker = findViewById(R.id.diastolic_picker);
        pulsePicker = findViewById(R.id.pulse_picker);
        saveBtn = findViewById(R.id.save_bp_btn);
        ImageView backArrow = findViewById(R.id.bp_back_btn);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        String date = day + "/" + month + "/" + year;

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


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(AddBloodPressure.this);
                String systolic = String.valueOf(systolicPicker.getValue());
                String diastolic = String.valueOf(diastolicPicker.getValue());
                String pulse = String.valueOf(pulsePicker.getValue());
                String date = textDate.getText().toString();
                String time = textTime.getText().toString();



                boolean saveBP = dbHelper.saveBP(systolic,diastolic,pulse,date,time, finalId);
                if (saveBP) {

                    // navigating activity into fragment
//                    Fragment fragment = new DynamicsFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().add(R.id.dashboard_container, fragment).commit();

                    Toast.makeText(AddBloodPressure.this, "blood pressure saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HistoryBloodPressure.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(AddBloodPressure.this, "Saving Failed", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }
}