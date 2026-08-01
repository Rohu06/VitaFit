package com.example.vitafit.User;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vitafit.Database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction())) {
            // Reset step count for the new day
            DbHelper databaseHelper = new DbHelper(context);
            String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            // Retrieve the userId from SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1); // Replace "userId" with your key

            if (userId != -1) {
                // Insert or update step count for the new day
                databaseHelper.insertOrUpdateStepCount(0, todayDate, userId);
                Log.d("DateChangeReceiver", "Step count reset for the new day for user: " + userId);
            } else {
                Log.e("DateChangeReceiver", "User ID not found. Step count not reset.");
            }
        }
    }
}