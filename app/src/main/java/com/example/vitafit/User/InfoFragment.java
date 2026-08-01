package com.example.vitafit.User;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitafit.AdapterClasses.InfoRVAdapter;
import com.example.vitafit.Entities.InfoModel;
import com.example.vitafit.R;

import java.util.ArrayList;


public class InfoFragment extends Fragment {
    RecyclerView infoRV;
    InfoRVAdapter adapter;
    ArrayList<InfoModel> arrInfo = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        infoRV = view.findViewById(R.id.info_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);

        infoRV.setLayoutManager(gridLayoutManager);

        AddingData();

        adapter = new InfoRVAdapter(getContext(), arrInfo);
        infoRV.setAdapter(adapter);

        return view;
    }
    public void AddingData(){
        String title1 = requireContext().getString(R.string.title1);
        String t1Subtitle1 = requireContext().getString(R.string.t1_subtitle1);
        String t1Subtitle2 = requireContext().getString(R.string.t1_subtitle2);
        String t1Desc1 = requireContext().getString(R.string.t1_desc1);
        String t1Desc2 = requireContext().getString(R.string.t1_desc2);
        int image1 = R.drawable.info_pic1;
        int color1 = getContext().getResources().getColor(R.color.blue_dark);
        String youtubeUrl1 = requireContext().getString(R.string.youtube_url1);


        String title2 = requireContext().getString(R.string.title2);
        String t2Subtitle1 = requireContext().getString(R.string.t2_subtitle1);
        String t2Subtitle2 = requireContext().getString(R.string.t2_subtitle2);
        String t2Desc1 = requireContext().getString(R.string.t2_desc1);
        String t2Desc2 = requireContext().getString(R.string.t2_desc2);
        int image2 = R.drawable.info_pic2;
        int color2 =getContext().getResources().getColor(R.color.greenish);
        String youtubeUrl2 =requireContext().getString(R.string.youtube_url2);

        String title3 = requireContext().getString(R.string.title3);
        String t3Subtitle1 = requireContext().getString(R.string.t3_subtitle1);
        String t3Subtitle2 = requireContext().getString(R.string.t3_subtitle2);
        String t3Desc1 = requireContext().getString(R.string.t3_desc1);
        String t3Desc2 = requireContext().getString(R.string.t3_desc2);
        int image3 = R.drawable.info_pic3;
        int color3 =getContext().getResources().getColor(R.color.light_green);
        String youtubeUrl3 = requireContext().getString(R.string.youtube_url3);

        String title4 = requireContext().getString(R.string.title4);
        String t4Subtitle1 = requireContext().getString(R.string.t4_subtitle1);
        String t4Subtitle2 = requireContext().getString(R.string.t4_subtitle2);
        String t4Desc1 = requireContext().getString(R.string.t4_desc1);
        String t4Desc2 = requireContext().getString(R.string.t4_desc2);
        int image4 = R.drawable.info_pic4;
        int color4 =getContext().getResources().getColor(R.color.red);
        String youtubeUrl4 = requireContext().getString(R.string.youtube_url4);

        String title5 = requireContext().getString(R.string.title5);
        String t5Subtitle1 = requireContext().getString(R.string.t5_subtitle1);
        String t5Subtitle2 = requireContext().getString(R.string.t5_subtitle2);
        String t5Desc1 = requireContext().getString(R.string.t5_desc1);
        String t5Desc2 = requireContext().getString(R.string.t5_desc2);
        int image5 = R.drawable.info_pic5;
        int color5 =getContext().getResources().getColor(R.color.teal_700);
        String youtubeUrl5 =requireContext().getString(R.string.youtube_url5);

        String title6 = requireContext().getString(R.string.title6);
        String t6Subtitle1 = requireContext().getString(R.string.t6_subtitle1);
        String t6Subtitle2 = requireContext().getString(R.string.t6_subtitle2);
        String t6Desc1 = requireContext().getString(R.string.t6_desc1);
        String t6Desc2 = requireContext().getString(R.string.t6_desc2);
        int image6 = R.drawable.info_pic6;
        int color6 =getContext().getResources().getColor(R.color.purple_500);
        String youtubeUrl6 = requireContext().getString(R.string.youtube_url6);

        String title7 = requireContext().getString(R.string.title7);
        String t7Subtitle1 = requireContext().getString(R.string.t7_subtitle1);
        String t7Subtitle2 = requireContext().getString(R.string.t7_subtitle2);
        String t7Desc1 = requireContext().getString(R.string.t7_desc1);
        String t7Desc2 = requireContext().getString(R.string.t7_desc2);
        int image7 = R.drawable.info_pic7;
        int color7 = getContext().getResources().getColor(R.color.light_green);
        String youtubeUrl7 = requireContext().getString(R.string.youtube_url7);

        String title8 = requireContext().getString(R.string.title8);
        String t8Subtitle1 = requireContext().getString(R.string.t8_subtitle1);
        String t8Subtitle2 = requireContext().getString(R.string.t8_subtitle2);
        String t8Desc1 = requireContext().getString(R.string.t8_desc1);
        String t8Desc2 = requireContext().getString(R.string.t8_desc2);
        int image8 = R.drawable.info_pic8;
        int color8 =getContext().getResources().getColor(R.color.Yellowish);
        String youtubeUrl8 = requireContext().getString(R.string.youtube_url8);

        // Add new topics
        String title9 = requireContext().getString(R.string.title9);
        String t9Subtitle1 = requireContext().getString(R.string.t9_subtitle1);
        String t9Subtitle2 = requireContext().getString(R.string.t9_subtitle2);
        String t9Desc1 = requireContext().getString(R.string.t9_desc1);
        String t9Desc2 = requireContext().getString(R.string.t9_desc2);
        int image9 = R.drawable.info_pic9; // Add a new drawable resource
        int color9 = getContext().getResources().getColor(R.color.light_blue);
        String youtubeUrl9 = requireContext().getString(R.string.youtube_url9);

        String title10 = requireContext().getString(R.string.title10);
        String t10Subtitle1 = requireContext().getString(R.string.t10_subtitle1);
        String t10Subtitle2 = requireContext().getString(R.string.t10_subtitle2);
        String t10Desc1 = requireContext().getString(R.string.t10_desc1);
        String t10Desc2 = requireContext().getString(R.string.t10_desc2);
        int image10 = R.drawable.info_pic10; // Add a new drawable resource
        int color10 = getContext().getResources().getColor(R.color.vibrant_purple);
        String youtubeUrl10 = requireContext().getString(R.string.youtube_url10);

        String title11 = requireContext().getString(R.string.title11);
        String t11Subtitle1 = requireContext().getString(R.string.t11_subtitle1);
        String t11Subtitle2 = requireContext().getString(R.string.t11_subtitle2);
        String t11Desc1 = requireContext().getString(R.string.t11_desc1);
        String t11Desc2 = requireContext().getString(R.string.t11_desc2);
        int image11 = R.drawable.info_pic11; // Add a new drawable resource
        int color11 = getContext().getResources().getColor(R.color.bright_green);
        String youtubeUrl11 = requireContext().getString(R.string.youtube_url11);

        String title12 = requireContext().getString(R.string.title12);
        String t12Subtitle1 = requireContext().getString(R.string.t12_subtitle1);
        String t12Subtitle2 = requireContext().getString(R.string.t12_subtitle2);
        String t12Desc1 = requireContext().getString(R.string.t12_desc1);
        String t12Desc2 = requireContext().getString(R.string.t12_desc2);
        int image12 = R.drawable.info_pic12; // Add a new drawable resource
        int color12 = getContext().getResources().getColor(R.color.deep_blue);
        String youtubeUrl12 = requireContext().getString(R.string.youtube_url12);

        String title13 = requireContext().getString(R.string.title13);
        String t13Subtitle1 = requireContext().getString(R.string.t13_subtitle1);
        String t13Subtitle2 = requireContext().getString(R.string.t13_subtitle2);
        String t13Desc1 = requireContext().getString(R.string.t13_desc1);
        String t13Desc2 = requireContext().getString(R.string.t13_desc2);
        int image13 = R.drawable.info_pic13; // Add a new drawable resource
        int color13 = getContext().getResources().getColor(R.color.bold_purple);
        String youtubeUrl13 = requireContext().getString(R.string.youtube_url13);

        String title14 = requireContext().getString(R.string.title14);
        String t14Subtitle1 = requireContext().getString(R.string.t14_subtitle1);
        String t14Subtitle2 = requireContext().getString(R.string.t14_subtitle2);
        String t14Desc1 = requireContext().getString(R.string.t14_desc1);
        String t14Desc2 = requireContext().getString(R.string.t14_desc2);
        int image14 = R.drawable.info_pic14; // Add a new drawable resource
        int color14 = getContext().getResources().getColor(R.color.rich_gold);
        String youtubeUrl14 = requireContext().getString(R.string.youtube_url14);

        String title15 = requireContext().getString(R.string.title15);
        String t15Subtitle1 = requireContext().getString(R.string.t15_subtitle1);
        String t15Subtitle2 = requireContext().getString(R.string.t15_subtitle2);
        String t15Desc1 = requireContext().getString(R.string.t15_desc1);
        String t15Desc2 = requireContext().getString(R.string.t15_desc2);
        int image15 = R.drawable.info_pic15; // Add a new drawable resource
        int color15 = getContext().getResources().getColor(R.color.electric_teal);
        String youtubeUrl15 = requireContext().getString(R.string.youtube_url15);

        String title16 = requireContext().getString(R.string.title16);
        String t16Subtitle1 = requireContext().getString(R.string.t16_subtitle1);
        String t16Subtitle2 = requireContext().getString(R.string.t16_subtitle2);
        String t16Desc1 = requireContext().getString(R.string.t16_desc1);
        String t16Desc2 = requireContext().getString(R.string.t16_desc2);
        int image16 = R.drawable.info_pic16; // Add a new drawable resource
        int color16 = getContext().getResources().getColor(R.color.fade_pink);
        String youtubeUrl16 = requireContext().getString(R.string.youtube_url16);


        arrInfo.add(new InfoModel(image1, title1, color1, t1Subtitle1, t1Subtitle2, t1Desc1, t1Desc2,youtubeUrl1));
        arrInfo.add(new InfoModel(image2, title2, color2, t2Subtitle1, t2Subtitle2, t2Desc1, t2Desc2,youtubeUrl2));
        arrInfo.add(new InfoModel(image3, title3, color3, t3Subtitle1, t3Subtitle2, t3Desc1, t3Desc2,youtubeUrl3));
        arrInfo.add(new InfoModel(image4, title4, color4, t4Subtitle1, t4Subtitle2, t4Desc1, t4Desc2,youtubeUrl4));
        arrInfo.add(new InfoModel(image5, title5, color5, t5Subtitle1, t5Subtitle2, t5Desc1, t5Desc2,youtubeUrl5));
        arrInfo.add(new InfoModel(image6, title6, color6, t6Subtitle1, t6Subtitle2, t6Desc1, t6Desc2,youtubeUrl6));
        arrInfo.add(new InfoModel(image7, title7, color7, t7Subtitle1, t7Subtitle2, t7Desc1, t7Desc2,youtubeUrl7));
        arrInfo.add(new InfoModel(image8, title8, color8, t8Subtitle1, t8Subtitle2, t8Desc1, t8Desc2,youtubeUrl8));
        arrInfo.add(new InfoModel(image9, title9, color9, t9Subtitle1, t9Subtitle2, t9Desc1, t9Desc2, youtubeUrl9));
        arrInfo.add(new InfoModel(image10, title10, color10, t10Subtitle1, t10Subtitle2, t10Desc1, t10Desc2, youtubeUrl10));
        arrInfo.add(new InfoModel(image11, title11, color11, t11Subtitle1, t11Subtitle2, t11Desc1, t11Desc2, youtubeUrl11));
        arrInfo.add(new InfoModel(image12, title12, color12, t12Subtitle1, t12Subtitle2, t12Desc1, t12Desc2, youtubeUrl12));
        arrInfo.add(new InfoModel(image13, title13, color13, t13Subtitle1, t13Subtitle2, t13Desc1, t13Desc2, youtubeUrl13));
        arrInfo.add(new InfoModel(image14, title14, color14, t14Subtitle1, t14Subtitle2, t14Desc1, t14Desc2, youtubeUrl14));
        arrInfo.add(new InfoModel(image15, title15, color15, t15Subtitle1, t15Subtitle2, t15Desc1, t15Desc2, youtubeUrl15));
        arrInfo.add(new InfoModel(image16, title16, color16, t16Subtitle1, t16Subtitle2, t16Desc1, t16Desc2, youtubeUrl16));
    }
}