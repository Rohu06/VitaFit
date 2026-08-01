package com.example.vitafit.User;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.example.vitafit.Sessions.LoginSessionManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {
    Button profileEditBtn;
    LinearLayout logoutLL, delete_accLL, feedbackLL;
    TextView fullName, userWeight, userHeight, userAge, userBMI;
    ImageView profileImage;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Hooks
        profileEditBtn = view.findViewById(R.id.profile_edit_btn);
        logoutLL = view.findViewById(R.id.logout_ll);
        delete_accLL = view.findViewById(R.id.delete_account);
        fullName = view.findViewById(R.id.profile_full_name);
        profileImage = view.findViewById(R.id.profile_img);
        userWeight = view.findViewById(R.id.profile_user_weight);
        userHeight = view.findViewById(R.id.profile_user_height);
        userBMI = view.findViewById(R.id.profile_user_bmi);
        userAge = view.findViewById(R.id.users_age);
        feedbackLL = view.findViewById(R.id.feedback_LL);


        // Fetching email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "");

        // Database helper
        DbHelper helper = new DbHelper(getContext());

        // Getting user data
        Cursor cursorUser = helper.getUserData(email);
        if (cursorUser != null && cursorUser.moveToFirst()) {
            fullName.setText(cursorUser.getString(1));  // Set full name
            userHeight.setText(cursorUser.getString(4) + "cm");  // Set height
            userWeight.setText(cursorUser.getString(5) + "kg");  // Set weight

            // Calculate and display BMI
            double weight = cursorUser.getDouble(5); // Assuming weight is stored in column index 5
            double height = cursorUser.getDouble(4); // Assuming height is stored in column index 4
            double bmi = calculateBMI(weight, height);
            userBMI.setText(String.format("BMI: %.2f", bmi));

            // Fetch and calculate age from DOB
            String userDOB = cursorUser.getString(3); // Assuming DOB is stored in the 7th column
            if (userDOB != null && !userDOB.isEmpty()) {
                int age = calculateAge(userDOB);
                userAge.setText(age + " Years"); // Display age
            } else {
                userAge.setText("Unknown"); // Handle case where DOB is not available
            }

            // Fetching profile image
            Cursor cursorImage = helper.getImage(cursorUser.getInt(0));  // Use user ID directly
            if (cursorImage != null && cursorImage.moveToFirst()) {
                Bitmap bitmap = convertByteArrayIntoBitmap(cursorImage.getBlob(1));
                profileImage.setImageBitmap(bitmap);
                cursorImage.close();
            }
            cursorUser.close();
        } else {
            Toast.makeText(getContext(), "User data not found!", Toast.LENGTH_SHORT).show();
        }

        // Edit profile button click
        profileEditBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UpdateProfile.class);
            startActivity(intent);
        });

        // Feedback button click
        feedbackLL.setOnClickListener(v -> sendFeedbackEmail());

        // Logout button click
        logoutLL.setOnClickListener(v -> {

            AlertDialog.Builder alertExitBox = new AlertDialog.Builder(getContext());
            alertExitBox.setTitle("Logout?");
            alertExitBox.setIcon(R.drawable.ic_logout);
            alertExitBox.setMessage("Are you sure you want to logout?");


            alertExitBox.setPositiveButton("Yes", (dialogInterface, i) -> {
                LoginSessionManagement session1 = new LoginSessionManagement(getContext());
//                session1.setLogin(false,email);
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                requireActivity().finish();
            });
            alertExitBox.setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(getContext(), "Welcome Back!", Toast.LENGTH_SHORT).show());


            alertExitBox.show();




        });

        // Delete account button click
        delete_accLL.setOnClickListener(v -> showDeleteAccountDialog(helper, email));

        return view;
    }

    // Function to calculate age from DOB
    private int calculateAge(String dob) {
        if (dob == null || dob.isEmpty()) {
            return 0; // Return 0 if DOB is empty or null
        }

        try {
            // Parse the date string
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date dateOfBirth = sdf.parse(dob);

            if (dateOfBirth == null) {
                return 0; // Return 0 if parsing fails
            }

            // Convert to Calendar instances
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dateOfBirth);

            Calendar today = Calendar.getInstance();

            // Calculate age
            int age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

            // Adjust age if the user's birthday hasn't occurred yet this year
            if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return Math.max(age, 0); // Ensure age is never negative
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0; // Default return value if parsing fails
    }

    // Function to Calculate users Body mass Index (BMI)
    private double calculateBMI(double weight, double heightCm) {
        // Convert height from cm to meters
        double heightM = heightCm / 100.0;

        // Calculate BMI: weight (kg) / (height (m) * height (m))
        return weight / (heightM * heightM);
    }

    // Function to show the delete account dialog with a 5-second timer
    private void showDeleteAccountDialog(DbHelper helper, String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Account");
        builder.setIcon(R.drawable.delete_forever_24px);
        builder.setMessage("Are you sure you want to delete your account? This action cannot be undone.You have ( 5 ) seconds to decide.");

        // Disable the positive button initially
        builder.setPositiveButton("Yes", null);

        builder.setNegativeButton("No", (dialogInterface, i) -> {
            Toast.makeText(getContext(), "Welcome Back!", Toast.LENGTH_SHORT).show();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        // Disable the "Yes" button initially
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);

        // Start a 5-second countdown timer
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String message = "Are you sure you want to delete your account? This action cannot be undone. <br><br>" +
                        "You have ( <font color='#FF0000'>  " + (millisUntilFinished / 1000) + "  </font> ) seconds to decide.";

                dialog.setMessage(HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY));
            }

            @Override
            public void onFinish() {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
                dialog.setMessage("Are you sure you want to delete your account? This action cannot be undone.");
            }
        }.start();

        // Handle the "Yes" button click
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
            // Delete the account from the database
            boolean isDeleted = helper.deleteUser(email);
            if (isDeleted) {
                Toast.makeText(requireContext(), "Account deleted successfully!", Toast.LENGTH_SHORT).show();

                // Logout the user after deleting the account
                LoginSessionManagement session = new LoginSessionManagement(getContext());
                session.logout();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Redirect to welcome screen or splash screen
                Intent intent = new Intent(getContext(), welcome_screen.class);
                startActivity(intent);
                requireActivity().finish();  // Close the current activity
            } else {
                Toast.makeText(requireContext(), "Failed to delete account!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        // Handle the "No" button click
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(v -> dialog.dismiss());
    }

    // Converting byteArray into Bitmap
    private Bitmap convertByteArrayIntoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    // Function to send email intent
    private void sendFeedbackEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822"); // Ensures only email apps handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lalalata08@gmail.com"}); // Replace with your email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Your App");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello Developer,\n\nI have some feedback..."); // Prefill message (optional)

        try {
            startActivity(Intent.createChooser(intent, "Send Feedback"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No email app found!", Toast.LENGTH_SHORT).show();
        }
    }
}