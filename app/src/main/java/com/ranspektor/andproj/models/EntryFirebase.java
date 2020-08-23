package com.ranspektor.andproj.models;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntryFirebase {
    final static String ENTRIES_COLLECTION = "entries";


    public static void getAllEntriesSince(long since, final EntryModel.Listener<List<Entry>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since, 0);
        db.collection(ENTRIES_COLLECTION)
                .whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get()
                .addOnCompleteListener(task -> {
                    List<Entry> entriesData = null;
                    if (task.isSuccessful()) {
                        entriesData = new LinkedList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Map<String, Object> json = doc.getData();
                            Entry entry = factory(json);
                            entriesData.add(entry);
                        }
                    }
                    listener.onComplete(entriesData);
                    Log.d("TAG", "refresh " + entriesData.size());
                });

    }

    public static void getAllEntries(final EntryModel.Listener<List<Entry>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ENTRIES_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    List<Entry> entriesData = null;
                    if (task.isSuccessful()) {
                        entriesData = new LinkedList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Entry entry = doc.toObject(Entry.class);
                            entriesData.add(entry);
                        }
                    }
                    listener.onComplete(entriesData);
                });
    }

    public static void addEntry(Entry entry, final EntryModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ENTRIES_COLLECTION)
                .document(entry.getId())
                .set(toJson(entry))
                .addOnCompleteListener(task -> {
                    if (listener != null) {
                        listener.onComplete(task.isSuccessful());
                    }
                });
    }

    private static Entry factory(Map<String, Object> json) {
        Entry entry = new Entry();
        entry.id = (String) json.get("id");
        entry.title = (String) json.get("title");
        entry.content = (String) json.get("content");
        entry.imgUrl = (String) json.get("imgUrl");
        entry.userId = (String) json.get("userId");
        Timestamp ts = (Timestamp) json.get("lastUpdated");
        if (ts != null) entry.lastUpdated = ts.getSeconds();
        return entry;
    }

    private static Map<String, Object> toJson(Entry entry) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", entry.id);
        result.put("title", entry.title);
        result.put("content", entry.content);
        result.put("imgUrl", entry.imgUrl);
        result.put("userId", entry.userId);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }
}
