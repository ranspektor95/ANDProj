package com.ranspektor.andproj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ranspektor.andproj.models.Entry;
import com.ranspektor.andproj.models.EntryModel;

import java.util.List;

public class EntryListViewModel extends ViewModel {
    LiveData<List<Entry>> liveData;

    public LiveData<List<Entry>> getLiveData() {
        if (liveData == null) {
            liveData = EntryModel.instance.getAllEntries();
        }
        return liveData;
    }

    public void refresh(EntryModel.CompListener compListener) {
        EntryModel.instance.refreshEntryList(compListener);
    }
}
