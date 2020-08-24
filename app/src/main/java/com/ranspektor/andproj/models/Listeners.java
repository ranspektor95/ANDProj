package com.ranspektor.andproj.models;

public interface Listeners {
    interface Listener<T> {
        void onComplete(T data);
    }

    interface CompListener {
        void onComplete();
    }
}
