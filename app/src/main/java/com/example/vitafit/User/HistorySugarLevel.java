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
import com.example.vitafit.AdapterClasses.HistorySLAdapter;
import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.Entities.HistorySLModel;
import com.example.vitafit.R;

import java.util.ArrayList;

public class HistorySugarLevel extends AppCompatActivity {

    RecyclerView RVSugarLevel;
    HistorySLAdapter adapter;
    ArrayList<HistorySLModel> arrSL = new ArrayList<>();
    DbHelper dbHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_sugar_level);


        RVSugarLevel = findViewById(R.id.rv_history_sl);

        ImageView backArrow = findViewById(R.id.history_sl_back_btn);

        backArrow.setOnClickListener(v -> onBackPressed());


        arrSL = new ArrayList<>();
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

        arrSL = dbHelper.fetchSLHistory(finalId);

        adapter = new HistorySLAdapter(this, arrSL);
        RVSugarLevel.setLayoutManager(new LinearLayoutManager(this));


        RVSugarLevel.setAdapter(adapter);



    }
}