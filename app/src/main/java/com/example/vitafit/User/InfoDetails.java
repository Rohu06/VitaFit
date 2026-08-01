package com.example.vitafit.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitafit.R;

public class InfoDetails extends AppCompatActivity {
    ImageView sImage, lImage;
    LinearLayout color_ll, info_details_appbar;
    TextView txtTitle, txtSbTitle1, txtSbTitle2, txtDesc1, txtDesc2;
    String title, subtitle1, subtitle2, desc1, desc2, youtubeUrl; // Add youtubeUrl
    int image, color;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_details);

        // Hooks
        sImage = findViewById(R.id.info_detail_s_image);
        lImage = findViewById(R.id.info_detail_l_image);
        txtTitle = findViewById(R.id.info_detail_title);
        txtSbTitle1 = findViewById(R.id.info_detail_subtitle1);
        txtSbTitle2 = findViewById(R.id.info_detail_subtitle2);
        txtDesc1 = findViewById(R.id.info_detail_desc1);
        txtDesc2 = findViewById(R.id.info_detail_desc2);
        color_ll = findViewById(R.id.info_detail_ll);
        info_details_appbar = findViewById(R.id.info_details_appbar);
        WebView webView = findViewById(R.id.youtube_knowledge); // WebView hook


        ImageView backArrow = findViewById(R.id.info_details_back_btn);

        backArrow.setOnClickListener(v -> onBackPressed());


        // Get YouTube URL from intent
        youtubeUrl = getIntent().getStringExtra("youtubeUrl");

        // Configure WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webView.setWebChromeClient(new WebChromeClient()); // Required for video playback
        webView.loadUrl(youtubeUrl); // Load the YouTube URL

        // Call function to set values
        gettingAndSettingValues();


    }

    // Method for getting and setting values which we received by bundle passing(Intent)
    public void gettingAndSettingValues(){

        //  Getting values through Intent bundle Passing
        title = getIntent().getStringExtra("title");
        subtitle1 = getIntent().getStringExtra("subtitle1");
        subtitle2 = getIntent().getStringExtra("subtitle2");
        desc1 = getIntent().getStringExtra("description1");
        desc2 = getIntent().getStringExtra("description2");
        image = getIntent().getIntExtra("image", 1);
        color = getIntent().getIntExtra("color", 1);



        // Setting Values in those views

        sImage.setImageResource(image);
        lImage.setImageResource(image);
        color_ll.setBackgroundColor(color);
        info_details_appbar.setBackgroundColor(color);
        txtTitle.setText(title);
        txtSbTitle1.setText(subtitle1);
        txtSbTitle2.setText(subtitle2);
        txtDesc1.setText(desc1);
        txtDesc2.setText(desc2);


    }
}