package com.ranspektor.andproj.models;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;

public class Model {

    public interface Listener<T> {
        void onComplete(T data);
    }

    static public final Model instance = new Model();

    private Model() {
        //TODO: check if dummy data population working

//        this.GetAllEntries(data -> {
//            if (data == null) {
//                for (int i = 0; i < 10; i++) {
//                    AppLocalDb.db.entryDao().insertAll(
//                            new Entry(
//                                    "" + i,
//                                    "name " + i,
//                                    "Lorem Ipsum " + i,
//                                    null,
//                                    null
//                            )
//                    );
//                }
//            }
//        });
    }

    public void GetAllEntries(final Listener<List<Entry>> listener) {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, String, List<Entry>> task = new AsyncTask<String, String, List<Entry>>() {
            @Override
            protected List<Entry> doInBackground(String... strings) {
                return AppLocalDb.db.entryDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Entry> entries) {
                super.onPostExecute(entries);
                listener.onComplete(entries);

            }
        };
        task.execute();
    }

    //TODO: implements other methods like GetAllEntries (delete, insert, getEntry, getEntryByUserId, etc...)


    Entry getEntry(String id) {
        return null;
    }


}
