package com.example.fitnessapp;

public class ActiviteSportive {

    private String name;
    private String desc;
    private String videoUrl;
    private String imageUrl;

    public ActiviteSportive(){}

    public ActiviteSportive(String name, String desc,String videoUrl, String imageUrl) {
        this.name = name;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
    }


    public String getDesc() {
        return desc;
    }
    public String getName() {
        return name;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public String getImageUrl(){ return  imageUrl; }

}
