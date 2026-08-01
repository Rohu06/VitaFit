package com.example.vitafit.User;

public class Exercise {
    private String title;
    private String description;
    private String category;
    private int iconResId;
    private String mediaUrl;

    public Exercise(String title, String description, String category, int iconResId, String mediaUrl) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.iconResId = iconResId;
        this.mediaUrl = mediaUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }
}