package com.example.vitafit.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class DynamicsFragment extends Fragment {

    FloatingActionButton addSLFab, addBPFab, addCIFab, addSTcount;
    BarChart sugarLevelHBC;
    Button historyBPBtn, historySLBtn, historyCIBtn;

    DonutProgress caloriesProgress;
    TextView dynamicsUserName, systolic, diastolic, pulse, glyIndex, status;
    ImageView profileImage;

    @SuppressLint("MissingInflatedId")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dynamics, container, false);

        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();


        //Hooks
        addSLFab = view.findViewById(R.id.dynamics_add_sl_fab);
        addBPFab = view.findViewById(R.id.dynamics_add_bp_fab);
        addCIFab = view.findViewById(R.id.dynamics_add_calories_fab);
        dynamicsUserName = view.findViewById(R.id.dynamics_userName);
        profileImage = view.findViewById(R.id.dynamics_profile_image);
        historyBPBtn = view.findViewById(R.id.history_bp);
        historySLBtn = view.findViewById(R.id.history_sl);
        historyCIBtn = view.findViewById(R.id.history_ci);
        systolic = view.findViewById(R.id.systolic);
        diastolic = view.findViewById(R.id.diastolic);
        pulse = view.findViewById(R.id.pulse);
        status = view.findViewById(R.id.sl_status);
        glyIndex = view.findViewById(R.id.sl_value);
        sugarLevelHBC = view.findViewById(R.id.hbc_sugar_level_dynamics);
        caloriesProgress = view.findViewById(R.id.calories_progress);
        addSTcount = view.findViewById(R.id.addSTCount);
        // on click listener functions
        btnOnClick();

        int id = 0;


        // db helper class object
        DbHelper helper = new DbHelper(getContext());


        // fetching user name
        Cursor cursorUser = helper.getUserData(email);
        while (cursorUser.moveToNext()) {
            id = cursorUser.getInt(0);
            dynamicsUserName.setText(cursorUser.getString(1));
        }
        int finalId = id;

        //fetching profile picture
        Cursor cursorImage = helper.getImage(finalId);
        while (cursorImage.moveToNext()) {
            Bitmap bitmap = convertByteArrayIntoBitmap(cursorImage.getBlob(1));
            profileImage.setImageBitmap(bitmap);
        }


        // fetching BP table last entry
        Cursor cursorBP = helper.getLastBP(finalId);
        while (cursorBP.moveToNext()) {
            systolic.setText(cursorBP.getString(1));
            diastolic.setText(cursorBP.getString(2));
            pulse.setText(cursorBP.getString(3));

        }

        // fetching BP table last entry
        Cursor cursorSL = helper.getLastSL(finalId);
        while (cursorSL.moveToNext()) {
            glyIndex.setText(cursorSL.getString(1));
            status.setText(cursorSL.getString(2));
        }


        // fetching BP table last entry
        Cursor cursorCI = helper.getCI(finalId);
        String cal1 = "";
        while (cursorSL.moveToNext()) {
            cal1 = cursorCI.getString(1);

        }


        caloriesProgress.setDonut_progress("40");


        // for sugar Level graph
        BarDataSet barDataSet1 = new BarDataSet(dataValues1(), "Sugar Level");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
        sugarLevelHBC.setData(barData);

        XAxis xAxis = sugarLevelHBC.getXAxis();
        xAxis.setTextColor(getContext().getColor(R.color.color_BW));
        sugarLevelHBC.getAxisRight().setTextColor(getContext().getColor(R.color.color_BW));
        sugarLevelHBC.getAxisLeft().setTextColor(getContext().getColor(R.color.color_BW));
        sugarLevelHBC.getDescription().setTextColor(getContext().getColor(R.color.color_BW));
        barDataSet1.setValueTextColor(getContext().getColor(R.color.color_BW));


        sugarLevelHBC.invalidate();


        return view;

    }

    // Every button setOnClickListener here
    private void btnOnClick() {
        addSLFab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddSugarLevel.class);
            startActivity(intent);
        });

        addCIFab.setOnClickListener(v -> {
//                addCIBottomSheetDialog();
            Intent intent = new Intent(getContext(), AddCaloriesIntake.class);
            startActivity(intent);
        });
        addBPFab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddBloodPressure.class);
            startActivity(intent);
        });
        historyBPBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryBloodPressure.class);
            startActivity(intent);
        });
        historySLBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistorySugarLevel.class);
            startActivity(intent);
        });

        addSTcount.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), steps_counting.class);
            startActivity(intent);
        });

        historyCIBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryCaloriesIntake.class);
            startActivity(intent);
        });
    }



    // for adding data into arraylist for sugar level graph
    private ArrayList<BarEntry> dataValues1() {

        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();
        DbHelper helper = new DbHelper(getContext());
        int id = 0;
        Cursor cursor = helper.getUserData(email);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            dynamicsUserName.setText(cursor.getString(1));
        }

        ArrayList<BarEntry> dataVals = new ArrayList<>();

        // fetching BP table last entry
        Cursor cursorSLTotal = helper.getWeeklySL(id);


        for (int i = 0; i < cursorSLTotal.getCount(); i++) {
            cursorSLTotal.moveToNext();
            dataVals.add(new BarEntry(i, Float.parseFloat(cursorSLTotal.getString(1))));

        }


        return dataVals;
    }

    // converting byteArray into Bitmap
    private Bitmap convertByteArrayIntoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }


}