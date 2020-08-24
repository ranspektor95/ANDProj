package com.ranspektor.andproj.models;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    @NonNull
    public String email;
    public long lastUpdated;

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public User() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
