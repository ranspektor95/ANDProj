package com.ranspektor.andproj.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Entry implements Serializable {
    @PrimaryKey
    @NonNull

    public String id;
    public String title;
    public String content;
    public String imgUrl;
    public String userId;

    public Entry(@NonNull String id, String title, String content, String imgUrl, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.userId = userId;
    }
}
