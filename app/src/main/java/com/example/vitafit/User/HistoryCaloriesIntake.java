package com.example.vitafit.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.vitafit.AdapterClasses.HistoryCIAdapter;
import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.Entities.HistoryCIModel;
import com.example.vitafit.R;

import java.util.ArrayList;

public class HistoryCaloriesIntake extends AppCompatActivity {

    private RecyclerView RVCaloriesIntake;
    private HistoryCIAdapter adapter;
    private ArrayList<HistoryCIModel> arrCI;
    private DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_calories_intake);

        RVCaloriesIntake = findViewById(R.id.rv_history_ci);
        ImageView backArrow = findViewById(R.id.history_calories_back_btn);

        dbHelper = new DbHelper(this);

        backArrow.setOnClickListener(v -> finish());

        // Getting email of the user logged in
        SharedPreferences sharedPreferences = getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", null);
        if (email == null || email.isEmpty()) {
            // Handle missing email case
            return;
        }

        // Getting the ID of the user logged in
        int userId = getUserId(email);
        if (userId == -1) {
            // Handle invalid user ID
            return;
        }

        // Fetching calorie intake history
        arrCI = dbHelper.fetchCIHistory(userId);
        if (arrCI == null) arrCI = new ArrayList<>();

        // Setting up RecyclerView
        adapter = new HistoryCIAdapter(this, arrCI);
        RVCaloriesIntake.setLayoutManager(new LinearLayoutManager(this));
        RVCaloriesIntake.setAdapter(adapter);
    }

    private int getUserId(String email) {
        int userId = -1;
        Cursor cursor = dbHelper.getUserData(email);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                userId = cursor.getInt(0);
            }
            cursor.close();
        }
        return userId;
    }
}
