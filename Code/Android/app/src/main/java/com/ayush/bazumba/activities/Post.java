package com.ayush.bazumba.activities;

import android.graphics.Bitmap;

public class Post {
    private String cid;
    private String title;
    private String teacher;
    private Bitmap thumbnail;

    public Post(String cid, String title, String teacher, Bitmap thumbnail){
        this.cid = cid;
        this.title = title;
        this.teacher = teacher;
        this.thumbnail = thumbnail;
    }

    public String getCid() {
        return cid;
    }

    public void setCId(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
