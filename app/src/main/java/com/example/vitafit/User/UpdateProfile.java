package com.example.vitafit.User;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Objects;

public class UpdateProfile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    TextInputEditText updateName, updateEmail, updateDOB, updateHeight, updateWeight, updatePassword;
    ImageView camera, gallery;
    Button chooseImage, saveBtn, saveImage;
    ImageView profileImage;
    DbHelper helper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);


        // Hooks
        profileImage = findViewById(R.id.update_profile_img);
        chooseImage = findViewById(R.id.choose_image);
        updateName = findViewById(R.id.update_name);
        updateEmail = findViewById(R.id.update_email);
        updateDOB = findViewById(R.id.update_DOB);
        updateHeight = findViewById(R.id.update_height);
        updateWeight = findViewById(R.id.update_weight);
        updatePassword = findViewById(R.id.update_password);
        saveBtn = findViewById(R.id.save_update_btn);
        saveImage = findViewById(R.id.save_image);

        ImageView backArrow = findViewById(R.id.update_profile_back_btn);

        backArrow.setOnClickListener(v -> onBackPressed());


        //choose Image button
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfileImage();
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

        // getting email of the user logged in
        SharedPreferences sharedPreferences = getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();

        // getting the id of the user logged in
        helper = new DbHelper(this);
        Cursor cursorUser = helper.getUserData(email);
        int id = 0;
        while (cursorUser.moveToNext()) {
            id = cursorUser.getInt(0);
            updateName.setText(cursorUser.getString(1));
            updateEmail.setText(cursorUser.getString(2));
            updateDOB.setText(cursorUser.getString(3));
            updateHeight.setText(cursorUser.getString(4));
            updateWeight.setText(cursorUser.getString(5));
            updatePassword.setText(cursorUser.getString(6));
        }
        updateEmail.setFocusable(false);


        int finalId = id;


        //fetching profile picture
        Cursor cursorImage = helper.getImage(finalId);
        while (cursorImage.moveToNext()) {
            Bitmap bitmap = convertByteArrayIntoBitmap(cursorImage.getBlob(1));
            profileImage.setImageBitmap(bitmap);
        }

        // saving image
        saveImage.setOnClickListener(v -> {
            // saving image into the database
            byte[] byteImage = convertImageToByteArray(profileImage);
            boolean saveImage = helper.saveImage(byteImage, date, time, finalId);
            if (saveImage) {
                Toast.makeText(UpdateProfile.this, "Profile Image saved", Toast.LENGTH_SHORT).show();
                //fetching profile picture
                Cursor cursorImage1 = helper.getImage(finalId);

                Bitmap bitmap = convertByteArrayIntoBitmap(cursorImage1.getBlob(1));
                profileImage.setImageBitmap(bitmap);
            }
             else {
                Toast.makeText(UpdateProfile.this, "Image not saved", Toast.LENGTH_SHORT).show();
            }
        });

        UserDashboard userDashboard = new UserDashboard();
        userDashboard.addFragment(new ProfileFragment(), false );

        // updating user details
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Objects.requireNonNull(updateName.getText()).toString();
                String email = Objects.requireNonNull(updateEmail.getText()).toString();
                String DOB = Objects.requireNonNull(updateDOB.getText()).toString();
                String height = Objects.requireNonNull(updateHeight.getText()).toString();
                String weight = Objects.requireNonNull(updateWeight.getText()).toString();
                String password = Objects.requireNonNull(updatePassword.getText()).toString();


                helper = new DbHelper(UpdateProfile.this);
                if (!validateName() || !validateEmail() || !validateDOB() || !validateHeight() || !validateWeight() || !validatePassword()) {
                    return;
                } else {
                    boolean update = helper.updateUserDetails(name, email, DOB, height, weight, password);

                    if (update) {
                        Toast.makeText(UpdateProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        while (cursorUser.moveToNext()) {
                            updateName.setText(cursorUser.getString(1));
                            updateEmail.setText(cursorUser.getString(2));
                            updateDOB.setText(cursorUser.getString(3));
                            updateHeight.setText(cursorUser.getString(4));
                            updateWeight.setText(cursorUser.getString(5));
                            updatePassword.setText(cursorUser.getString(6));
                        }

                        UserDashboard userDashboard = new UserDashboard();
                        userDashboard.addFragment(new ProfileFragment(), false );


                    } else {
                        Toast.makeText(UpdateProfile.this, "Updating failed", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }

    // For Choosing Image from camera and gallery
    private void chooseProfileImage() {
        // for showing bottom sheet dialog
        final Dialog dialog = new Dialog(UpdateProfile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bs_choose_image_layout);

        ImageView cancelButton = dialog.findViewById(R.id.cancel_button);
        LinearLayout camera = dialog.findViewById(R.id.camera_ll);
        LinearLayout gallery = dialog.findViewById(R.id.gallery_ll);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissions()) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }

//                    takePicFromCamera();
                    dialog.dismiss();
                }

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picPhoto, 1);
                dialog.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    Uri selectedImageUri = data.getData();
                    profileImage.setImageURI(selectedImageUri);

                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profileImage.setImageBitmap(photo);

                }
                break;
        }
    }

    private boolean checkAndRequestPermissions() {
        int cameraPermission = ActivityCompat.checkSelfPermission(UpdateProfile.this, Manifest.permission.CAMERA);
        if (cameraPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(UpdateProfile.this, new String[]{Manifest.permission.CAMERA}, 20);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

    // For saving Image into the database
    private byte[] convertImageToByteArray(ImageView image) {
        Bitmap bitmapImage = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // converting byteArray into Bitmap
    private Bitmap convertByteArrayIntoBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    // validations
    private Boolean validateName() {
        String name = Objects.requireNonNull(updateName.getText()).toString();
        if (name.isEmpty()) {
            updateName.setError("Name is required");
            return false;
        } else {
            updateName.setError(null);
            return true;
        }

    }
    private Boolean validateEmail() {
        String email = Objects.requireNonNull(updateEmail.getText()).toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            updateEmail.setError("Email is required");
            return false;
        } else if (!email.matches(emailPattern)) {
            updateEmail.setError("Invalid Email");
            return false;
        } else {
            updateEmail.setError(null);
            return true;
        }


    }
    private Boolean validateDOB() {
        String dob = Objects.requireNonNull(updateDOB.getText()).toString();
        if (dob.isEmpty()) {
            updateDOB.setError("DOB is required");
            return false;
        } else {
            updateDOB.setError(null);
            return true;
        }

    }
    private Boolean validateHeight() {
        String height = Objects.requireNonNull(updateHeight.getText()).toString();
        if (height.isEmpty()) {
            updateHeight.setError("Height is required");
            return false;
        } else {
            updateHeight.setError(null);
            return true;
        }

    }
    private Boolean validateWeight() {
        String weight = Objects.requireNonNull(updateWeight.getText()).toString();
        if (weight.isEmpty()) {
            updateWeight.setError("Weight is required");
            return false;
        } else {
            updateWeight.setError(null);
            return true;
        }

    }
    private Boolean validatePassword() {
        String pass = Objects.requireNonNull(updatePassword.getText()).toString();
        if (pass.isEmpty()) {
            updatePassword.setError("Password is required");
            return false;
        } else {
            updatePassword.setError(null);
            return true;
        }

    }
}