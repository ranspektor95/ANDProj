package com.ranspektor.andproj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ranspektor.andproj.models.Entry;
import com.ranspektor.andproj.models.EntryModel;
import com.ranspektor.andproj.models.Listeners;
import com.ranspektor.andproj.models.UserModel;

import java.util.List;

public class UserEntriesListViewModel extends ViewModel {
    private LiveData<List<Entry>> liveData;

    public LiveData<List<Entry>> getLiveData(String userId) {
        if (liveData == null)
            liveData = EntryModel.instance.getUserEntries(userId);
        return liveData;
    }

    public LiveData<List<Entry>> getLiveData() {
        if (liveData == null)
            liveData = EntryModel.instance.getCurrentUserEntries();
        return liveData;
    }

    public void refresh(String userId, Listeners.CompListener listener) {
        EntryModel.instance.refreshUserEntryList(userId, listener);
    }

    public void refresh(Listeners.CompListener listener) {
        EntryModel.instance.refreshUserEntryList(UserModel.instance.getCurrentUserId(), listener);
    }
}
