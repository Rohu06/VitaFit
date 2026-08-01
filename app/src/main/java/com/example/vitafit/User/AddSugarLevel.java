package com.example.vitafit.User;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

public class AddSugarLevel extends AppCompatActivity {
    TextView glyTime, glyDate;
    String[] status = new String[]{"Default", "After meal", "Before Meal", "After Sleep", "Before Sleep"};
    NumberPicker glyPicker;
    Button save_SL;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sugar_level);

        // hooks
        glyPicker = findViewById(R.id.gly_picker);
        glyTime = findViewById(R.id.date);
        glyDate = findViewById(R.id.time);
        save_SL = findViewById(R.id.save_sl_btn);

        ImageView backArrow = findViewById(R.id.sl_back_btn);

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
        String date = day + "/" + month + "/" + year;

        glyTime.setText(time);
        glyDate.setText(date);

        Spinner dropdown = findViewById(R.id.dropdown_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, status);
        dropdown.setAdapter(adapter);

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
        save_SL.setOnClickListener(v -> {
            DbHelper dbHelper = new DbHelper(AddSugarLevel.this);
            String gly = String.valueOf(glyPicker.getValue());
            String status = String.valueOf(dropdown.getSelectedItem());
            String date1 = glyDate.getText().toString();
            String time1 = glyTime.getText().toString();

            boolean saveSL = dbHelper.saveSL(gly,status, date1, time1,finalId);
            if (saveSL) {
                // navigating activity into fragment
//                    Fragment fragment = new DynamicsFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().add(R.id.dashboard_container, fragment).commit();

                Intent intent = new Intent(getApplicationContext(), HistorySugarLevel.class);
                startActivity(intent);

                Toast.makeText(AddSugarLevel.this, "Sugar Level  saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddSugarLevel.this, "Saving Failed", Toast.LENGTH_SHORT).show();
            }




        });
    }
}