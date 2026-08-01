package com.example.vitafit.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitafit.Entities.InfoModel;
import com.example.vitafit.R;
import com.example.vitafit.User.InfoDetails;

import java.util.ArrayList;

public class InfoRVAdapter extends RecyclerView.Adapter<InfoRVAdapter.ViewHolder> {
    Context context;
    ArrayList<InfoModel> arrInfo;

    public InfoRVAdapter(Context context, ArrayList<InfoModel> arrInfo) {
        this.context = context;
        this.arrInfo = arrInfo;
    }

    @NonNull
    @Override
    public InfoRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_info_row_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoRVAdapter.ViewHolder holder, int position) {
        InfoModel model = arrInfo.get(position);
        holder.title.setText(arrInfo.get(position).title);
        holder.sImage.setImageResource(arrInfo.get(position).image);
        holder.lImage.setImageResource(arrInfo.get(position).image);
        holder.bgColor.setBackgroundColor(arrInfo.get(position).color);

        holder.bgColor.setOnClickListener(v -> {
            Intent i = new Intent(context, InfoDetails.class);
            i.putExtra("title", model.getTitle());
            i.putExtra("subtitle1", model.getSubTitle1());
            i.putExtra("subtitle2", model.getSubTitle2());
            i.putExtra("description1", model.getDescription1());
            i.putExtra("description2", model.getDescription2());
            i.putExtra("color", model.getColor());
            i.putExtra("image", model.getImage());
            i.putExtra("youtubeUrl", model.getYoutubeUrl()); // Pass the YouTube URL

            context.startActivity(i);
        });



    }

    @Override
    public int getItemCount() {
        return arrInfo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView sImage, lImage;
        RelativeLayout bgColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.info_title);
            sImage = itemView.findViewById(R.id.info_s_image);
            lImage = itemView.findViewById(R.id.info_l_image);
            bgColor = itemView.findViewById(R.id.info_card);


        }
    }
}
