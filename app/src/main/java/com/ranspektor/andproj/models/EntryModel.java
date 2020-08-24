package com.ranspektor.andproj.models;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ranspektor.andproj.ANDApplication;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EntryModel {

    static public final EntryModel instance = new EntryModel();

    private EntryModel() {
//        for (int i = 0; i < 10; i++) {
//            Entry entry = new Entry("" + i, "title " + i, "Lorem Ipsum " + i, null, null);
//            addEntry(entry, null);
//        }
    }

    public LiveData<List<Entry>> getAllEntries() {
        LiveData<List<Entry>> liveData = AppLocalDb.db.entryDao().getAllEntries();
        refreshEntryList(null);
        return liveData;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshEntryList(Listeners.CompListener compListener) {
        long lastUpdated = ANDApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).getLong("EntryLastUpdateDate", 0);
        EntryFirebase.getAllEntries(lastUpdated, entries -> new AsyncTask<String, String, String>() {
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

    public LiveData<List<Entry>> getUserEntries(String userId) {
        LiveData<List<Entry>> liveData = AppLocalDb.db.entryDao().getUserEntries(userId);
        refreshUserEntryList(userId, null);
        return liveData;
    }

    public LiveData<List<Entry>> getCurrentUserEntries() {
        return getUserEntries(UserModel.instance.getCurrentUserId());
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshUserEntryList(String userId, Listeners.CompListener compListener) {
        long lastUpdated = ANDApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).getLong("EntryLastUpdateDate", 0);
        EntryFirebase.getAllUserEntries(userId, lastUpdated, entries -> new AsyncTask<String, String, String>() {
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

    public LiveData<Entry> getEntry(String entryId) {
        LiveData<Entry> liveData = AppLocalDb.db.entryDao().getEntry(entryId);
        refreshSpecificEntry(entryId);
        return liveData;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshSpecificEntry(String entryId) {
        EntryFirebase.getEntry(entryId, entry -> {
            if (entry != null) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        AppLocalDb.db.entryDao().insertAll(entry);
                        return null;
                    }
                }.execute("");
            }
        });
    }

    public void addEntry(Entry entry, final Listeners.Listener<Boolean> listener) {
        EntryFirebase.addEntry(entry, listener);
    }

    @SuppressLint("StaticFieldLeak")
    public void updateEntry(Entry entry, final Listeners.Listener<Boolean> listener) {
        EntryFirebase.updateEntry(entry, data -> {
            if (data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        AppLocalDb.db.entryDao().insertAll(entry);
                        return null;
                    }
                }.execute("");
            }
            listener.onComplete(data);
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteEntry(Entry entry, Listeners.Listener<Boolean> listener) {
        EntryFirebase.deleteEntry(entry, listener);
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.entryDao().delete(entry);
                return null;
            }
        }.execute();
    }

}
