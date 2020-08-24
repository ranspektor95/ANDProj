package com.ranspektor.andproj.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntryFirebase {
    final static String ENTRIES_COLLECTION = "entries";


    public static void getAllEntries(long since, final Listeners.Listener<List<Entry>> listener) {
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

    static void getAllUserEntries(String userId, long since, final Listeners.Listener<List<Entry>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp timestamp = new Timestamp(new Date(since));
        db.collection(ENTRIES_COLLECTION)
                .whereEqualTo("userId", userId)
                .whereEqualTo("isDeleted", false)
                .whereGreaterThanOrEqualTo("lastUpdated", timestamp).get()
                .addOnCompleteListener(task -> {
                    List<Entry> entries;
                    if (task.isSuccessful()) {
                        entries = new LinkedList<>();
                        if (task.getResult() != null)
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> json = doc.getData();
                                Entry entry = factory(json);
                                entry.setId(doc.getId());
                                entries.add(entry);
                            }
                        listener.onComplete(entries);
                    } else {
                        throw new RuntimeException(task.getException());
                    }
                });
    }

    public static void addEntry(Entry entry, final Listeners.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        entry.setUserId(UserFirebase.getCurrentUserId());
        db.collection(ENTRIES_COLLECTION)
                .document(entry.getId())
                .set(toJson(entry))
                .addOnCompleteListener(task -> {
                    if (listener != null) {
                        listener.onComplete(task.isSuccessful());
                    }
                });
    }

    public static void getEntry(final String entryId, final Listeners.Listener<Entry> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ENTRIES_COLLECTION)
                .document(entryId)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot entry = task.getResult();
                    Map<String, Object> json = entry != null
                            ? entry.getData()
                            : null;
                    if (json != null)
                        listener.onComplete(factory(json));
                });
    }

    public static void updateEntry(Entry entry, final Listeners.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ENTRIES_COLLECTION).document(entry.getId()).set(toJson(entry))
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void deleteEntry(final Entry entry, final Listeners.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(ENTRIES_COLLECTION)
                .document(entry.getId())
                .update("isDeleted", true)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
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
