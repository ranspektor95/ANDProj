package com.ranspektor.andproj.models;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ranspektor.andproj.ANDApplication;

import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EntryModel {

    static public final EntryModel instance = new EntryModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    private EntryModel() {
//        for (int i = 0; i < 10; i++) {
//            Entry entry = new Entry("" + i, "title " + i, "Lorem Ipsum " + i, null, null);
//            addEntry(entry, null);
//        }
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshEntryList(CompListener compListener) {
        long lastUpdated = ANDApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).getLong("EntryLastUpdateDate", 0);
        EntryFirebase.getAllEntriesSince(lastUpdated, entries -> new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                long lastUpdated = 0;
                for (Entry entry : entries) {
                    AppLocalDb.db.entryDao().insertAll(entry);
                    if (entry.lastUpdated > lastUpdated) lastUpdated = entry.lastUpdated;
                }
                SharedPreferences.Editor edit = ANDApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).edit();
                edit.putLong("EntryLastUpdateDate", lastUpdated);
                edit.commit();
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (compListener != null) {
                    compListener.onComplete();
                }
            }
        }.execute(""));
    }

    public LiveData<List<Entry>> getAllEntries() {
        LiveData<List<Entry>> liveData = AppLocalDb.db.entryDao().getAll();
        refreshEntryList(null);
        return liveData;
    }

    public void addEntry(Entry entry, final Listener<Boolean> listener) {
        EntryFirebase.addEntry(entry, listener);
    }

    //TODO: implements other methods like GetAllEntries (delete, insert, getEntry, getEntryByUserId, etc...)
}
