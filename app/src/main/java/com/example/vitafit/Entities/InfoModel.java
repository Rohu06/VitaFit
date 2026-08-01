package com.example.vitafit.Entities;

public class InfoModel {
    public int image, color;
    public String title,  subTitle1, subTitle2, description1, description2;
    public String youtubeUrl; // Add this field



    public InfoModel(int image, String title, int color, String subTitle1, String subTitle2, String description1, String description2, String youtubeUrl) {
        this.image = image;
        this.title = title;
        this.color = color;
        this.subTitle1 = subTitle1;
        this.subTitle2 = subTitle2;
        this.description1 = description1;
        this.description2 = description2;
        this.youtubeUrl = youtubeUrl;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle1(String subTitle1) {
        this.subTitle1 = subTitle1;
    }

    public void setSubTitle2(String subTitle2) {
        this.subTitle2 = subTitle2;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }
    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
    // Add getter and setter for youtubeUrl


    public int getImage() {
        return image;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle1() {
        return subTitle1;
    }

    public String getSubTitle2() {
        return subTitle2;
    }

    public String getDescription1() {
        return description1;
    }

    public String getDescription2() {
        return description2;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }


}
