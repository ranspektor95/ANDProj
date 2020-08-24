package com.ranspektor.andproj.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.FieldValue;
import com.google.firestore.v1.DocumentTransform;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Entry implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public String content;
    public String imgUrl;
    public String userId;
    public long lastUpdated;
    public boolean isDeleted;


    public Entry() {
    }

    public Entry(@NonNull String id, String title, String content, String imgUrl, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.userId = userId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
