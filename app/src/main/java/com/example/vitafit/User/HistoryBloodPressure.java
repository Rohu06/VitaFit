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

import com.example.vitafit.AdapterClasses.HistoryBPAdapter;
import com.example.vitafit.AdapterClasses.InfoRVAdapter;
import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.Entities.HistoryBPModel;
//import com.example.vitafit.Entities.InfoModel;
import com.example.vitafit.R;

import java.util.ArrayList;

public class HistoryBloodPressure extends AppCompatActivity {

    RecyclerView RVBloodPressure;
    HistoryBPAdapter adapter;
    ArrayList<HistoryBPModel> arrBP = new ArrayList<>();
    DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_blood_pressure);


        RVBloodPressure = findViewById(R.id.rv_history_bp);

        ImageView backArrow = findViewById(R.id.history_bp_back_btn);

        backArrow.setOnClickListener(v -> onBackPressed());


        arrBP = new ArrayList<>();
        dbHelper = new DbHelper(this);

        // for id
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

        arrBP = dbHelper.fetchBPHistory(finalId);

        adapter = new HistoryBPAdapter(this, arrBP);
        RVBloodPressure.setLayoutManager(new LinearLayoutManager(this));


        RVBloodPressure.setAdapter(adapter);



    }
}