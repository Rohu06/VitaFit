package com.example.vitafit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.vitafit.Entities.HistoryBPModel;
import com.example.vitafit.Entities.HistoryCIModel;
import com.example.vitafit.Entities.HistorySLModel;
import com.example.vitafit.Entities.StepCountModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "VitafitDb";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USER = "tbl_users";
    private static final String TABLE_BP = "tbl_blood_pressure";
    private static final String TABLE_SL = "tbl_sugar_levels";
    private static final String TABLE_CI = "tbl_calories";
    private static final String TABLE_PROFILES = "tbl_profiles";
    private static final String TABLE_STEP_COUNTS = "tbl_step_counts"; // New table for step counts

    // Common columns
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";
    private static final String COL_USER_ID = "user_id";

    // User table columns
    private static final String KEY_USER_ID = "user_id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_DOB = "date_of_birth";
    private static final String COL_HEIGHT = "height";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_PASSWORD = "password";

    // Blood pressure table columns
    private static final String KEY_BP_ID = "bp_id";
    private static final String COL_SYSTOLIC = "systolic";
    private static final String COL_DIASTOLIC = "diastolic";
    private static final String COL_PULSE = "pulse";

    // Sugar level table columns
    private static final String KEY_SL_ID = "sl_id";
    private static final String COL_GLY_INDEX = "gly_index";
    private static final String COL_STATUS = "status";

    // Calories intake table columns
    private static final String KEY_CI_ID = "ci_id";
    private static final String COL_CALORIES = "calories_intake";

    // Profiles table columns
    private static final String KEY_PROFILE_ID = "profile_id";
    private static final String COL_PROFILE_IMAGE = "image";

    // Step counts table columns
    private static final String KEY_STEP_ID = "step_id";
    private static final String COL_STEPS = "steps";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        db.execSQL(" CREATE TABLE " + TABLE_USER + " ( "
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_DOB + " DATE, "
                + COL_HEIGHT + " TEXT, "
                + COL_WEIGHT + " TEXT, "
                + COL_PASSWORD + " TEXT );"
        );

        // Create blood pressure table
        db.execSQL(" CREATE TABLE " + TABLE_BP + " ( "
                + KEY_BP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SYSTOLIC + " TEXT, "
                + COL_DIASTOLIC + " TEXT, "
                + COL_PULSE + " TEXT, "
                + COL_DATE + " DATE, "
                + COL_TIME + " TEXT, "
                + COL_USER_ID + " INTEGER REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + ")"
                + ");"
        );

        // Create sugar level table
        db.execSQL(" CREATE TABLE " + TABLE_SL + " ( "
                + KEY_SL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_GLY_INDEX + " TEXT, "
                + COL_STATUS + " TEXT, "
                + COL_DATE + " DATE, "
                + COL_TIME + " TEXT, "
                + COL_USER_ID + " INTEGER REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + ")"
                + ");"
        );

        // Create calories intake table
        db.execSQL(" CREATE TABLE " + TABLE_CI + " ( "
                + KEY_CI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CALORIES + " TEXT, "
                + COL_DATE + " DATE, "
                + COL_TIME + " TEXT, "
                + COL_USER_ID + " INTEGER REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + ")"
                + ");"
        );

        // Create profiles table
        db.execSQL(" CREATE TABLE " + TABLE_PROFILES + " ( "
                + KEY_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PROFILE_IMAGE + " BLOB, "
                + COL_DATE + " DATE, "
                + COL_TIME + " TEXT, "
                + COL_USER_ID + " INTEGER REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + ")"
                + ");"
        );

        // Create step counts table
        db.execSQL(" CREATE TABLE " + TABLE_STEP_COUNTS + " ( "
                + KEY_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_STEPS + " INTEGER, "
                + COL_DATE + " DATE, "
                + COL_USER_ID + " INTEGER REFERENCES " + TABLE_USER + "(" + KEY_USER_ID + ")"
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_BP);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_SL);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_CI);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_STEP_COUNTS); // Drop step counts table
        onCreate(db);
    }

    // Insert or update step count
    public boolean insertOrUpdateStepCount(int steps, String date, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if a record for today already exists
        Cursor cursor = db.query(TABLE_STEP_COUNTS,
                new String[]{KEY_STEP_ID, COL_STEPS},
                COL_DATE + " = ? AND " + COL_USER_ID + " = ?",
                new String[]{date, String.valueOf(userId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Update existing record
            int stepId = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STEP_ID));
            ContentValues values = new ContentValues();
            values.put(COL_STEPS, steps);
            db.update(TABLE_STEP_COUNTS, values, KEY_STEP_ID + " = ?", new String[]{String.valueOf(stepId)});
            cursor.close();
        } else {
            // Insert new record
            ContentValues values = new ContentValues();
            values.put(COL_STEPS, steps);
            values.put(COL_DATE, date);
            values.put(COL_USER_ID, userId);
            db.insert(TABLE_STEP_COUNTS, null, values);
        }
        db.close();
        return true;
    }

    // Get step count for a specific date
    public int getStepCountByDate(String date, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STEP_COUNTS,
                new String[]{COL_STEPS},
                COL_DATE + " = ? AND " + COL_USER_ID + " = ?",
                new String[]{date, String.valueOf(userId)},
                null, null, null);

        int steps = 0;
        if (cursor != null && cursor.moveToFirst()) {
            steps = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STEPS));
            cursor.close();
        }
        db.close();
        return steps;
    }

    // Get step count history for a user
    public ArrayList<StepCountModel> getStepCountHistory(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STEP_COUNTS,
                new String[]{COL_STEPS, COL_DATE},
                COL_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null, null, COL_DATE + " DESC");

        ArrayList<StepCountModel> stepCountHistory = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                stepCountHistory.add(new StepCountModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_STEPS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return stepCountHistory;
    }

    // registering new user
    public boolean userRegister(String name, String email, String DOB, String height, String weight, String password) {
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_DOB, DOB);
        values.put(COL_HEIGHT, height);
        values.put(COL_WEIGHT, weight);
        values.put(COL_PASSWORD, password);
//        values.put(KEY_BOOK_DESCRIPTION, description);

        // Putting 3 parameters in insert method
        long result = db.insert(TABLE_USER, null, values);
        // db.insert(TABLE_NAME, null, values);
//        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    // checking if the user email already exits;
    public boolean checkEmail(String email) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + " =?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }
    // checking email and password matching for login;
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + " =? and " + COL_PASSWORD + " =? ", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }
    // getting logged in user details
    public Cursor getUserData(String email) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + " =? ", new String[]{email});
    }

    //updating logged in user details
    public boolean updateUserDetails(String name , String email, String DOB, String height, String weight, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_DOB, DOB);
        values.put(COL_HEIGHT, height);
        values.put(COL_WEIGHT, weight);
        values.put(COL_PASSWORD, password);
        long result = db.update(TABLE_USER, values, COL_EMAIL + "=?", new String[]{email});
        db.close();

        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    // saving user blood pressure
    public boolean saveBP(String systolic, String diastolic, String pulse, String date, String time, int id) {
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(COL_SYSTOLIC, systolic);
        values.put(COL_DIASTOLIC, diastolic);
        values.put(COL_PULSE, pulse);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_USER_ID, id);
//        values.put(KEY_BOOK_DESCRIPTION, description);

        // Putting 3 parameters in insert method
        long result = db.insert(TABLE_BP, null, values);

//        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }



    }
    // saving user Sugar Level
    public boolean saveSL(String gly, String status, String date, String time, int id) {
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(COL_GLY_INDEX, gly);
        values.put(COL_STATUS, status);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_USER_ID, id);
//        values.put(KEY_BOOK_DESCRIPTION, description);

        // Putting 3 parameters in insert method
        long result = db.insert(TABLE_SL, null, values);

//        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    // saving user Sugar Level
    public boolean saveCI(String calories, String date, String time, int id) {
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(COL_CALORIES, calories);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_USER_ID, id);
//        values.put(KEY_BOOK_DESCRIPTION, description);

        // Putting 3 parameters in insert method
        long result = db.insert(TABLE_CI, null, values);

//        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    // saving user profile Image
    public boolean saveImage(byte[] image, String date, String time, int id) {
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(COL_PROFILE_IMAGE, image);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_USER_ID, id);
//        values.put(KEY_BOOK_DESCRIPTION, description);

        // Putting 3 parameters in insert method
        long result = db.insert(TABLE_PROFILES, null, values);

//        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    // getting user profile Image
    public Cursor getImage(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_PROFILES + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_PROFILE_ID + " DESC LIMIT  1", null);
    }
    // Fetching  Blood Pressure data from tableBP
    public ArrayList<HistoryBPModel> fetchBPHistory(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor =  db.rawQuery(" SELECT * FROM  " + TABLE_BP +" WHERE " + COL_USER_ID + " = " + id + " ORDER BY " + KEY_BP_ID  + " DESC ", null);
        ArrayList<HistoryBPModel> arrBPHistory = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrBPHistory.add(new HistoryBPModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrBPHistory;
    }
    // Fetching  Sugar level data from tableSL
    public ArrayList<HistorySLModel> fetchSLHistory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery(" SELECT * FROM  " + TABLE_SL +" WHERE " + COL_USER_ID + " = " + id + " ORDER BY " + KEY_SL_ID  + " DESC ", null);
        ArrayList<HistorySLModel> arrSLHistory = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrSLHistory.add(new HistorySLModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrSLHistory;
    }
    // Fetching Calories Intake data from tableCI
    public ArrayList<HistoryCIModel> fetchCIHistory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery(" SELECT * FROM  " + TABLE_CI +" WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_CI_ID + " DESC ", null);
        ArrayList<HistoryCIModel> arrCIHistory = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrCIHistory.add(new HistoryCIModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrCIHistory;
    }
    // getting logged in user details
    public Cursor getLastBP(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_BP_ID + " DESC LIMIT  1", null);
    }
    // getting logged in user details
    public Cursor getLastSL(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_SL + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_SL_ID + " DESC LIMIT  1 ", null);

    }

    // getting logged in user details
    public Cursor getCI(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_CI + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_CI_ID + " DESC LIMIT  1 ", null);

    }

    // getting data for graphs
    public Cursor getWeeklySL(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_SL + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_SL_ID + " DESC ", null);

    }

    public Cursor getWeeklyBP(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_BP_ID + " DESC  ", null);

    }

    public Cursor getWeeklyCI(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT * FROM " + TABLE_CI + " WHERE " + COL_USER_ID + " = " + id + " ORDER BY  " + KEY_CI_ID + " DESC ", null);

    }

    public Cursor getSysAvg(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT avg( "+ COL_SYSTOLIC +") FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getDiaAvg(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT avg( "+ COL_DIASTOLIC +") FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getMaxSys(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  max( "+ COL_SYSTOLIC +" ) FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id , null);

    }
    public Cursor getMaxDia(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  max( "+ COL_DIASTOLIC +" ) FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getMinDia(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  min( "+ COL_DIASTOLIC +" ) FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getMinSys(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  min( "+ COL_DIASTOLIC +" ) FROM " + TABLE_BP + " WHERE " + COL_USER_ID + " = " + id , null);

    }
    public Cursor getMinSL(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  min( "+ COL_GLY_INDEX +" ) FROM " + TABLE_SL + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getMaxSL(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  max( "+ COL_GLY_INDEX +" ) FROM " + TABLE_SL + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getAvgSL(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  avg( "+ COL_GLY_INDEX +" ) FROM " + TABLE_SL + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getMaxCI(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  max( "+ COL_CALORIES +" ) FROM " + TABLE_CI + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getMinCI(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  min( "+ COL_CALORIES +" ) FROM " + TABLE_CI + " WHERE " + COL_USER_ID + " = " + id , null);

    }

    public Cursor getAvgCI(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(" SELECT  avg( "+ COL_CALORIES +" ) FROM " + TABLE_CI + " WHERE " + COL_USER_ID + " = " + id , null);

    }


    public boolean deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER, COL_EMAIL + "=?", new String[]{email});
        db.close();

        return result > 0;
    }


}
