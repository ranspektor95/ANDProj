package com.ranspektor.andproj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ranspektor.andproj.models.Entry;

public class EntryListActivity extends AppCompatActivity implements EntryListFragment.Delegate, EntryDetailsFragment.EditElementDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_list_activity_fragment);

//        EntryListFragment entryListFragment = new EntryListFragment();
//        EntryDetailsFragment entryDetailsFragment = new EntryDetailsFragment();
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.entry_list_fragment_container, entryListFragment, "TAG");
//        transaction.commit();
    }


    void openEntryDetail(Entry req){
//        EntryDetailsFragment entryDetailsFragment = new EntryDetailsFragment();
//        entryDetailsFragment.setEntry(req);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in,android.R.animator.fade_out);
//        transaction.add(R.id.entry_list_fragment_container, entryDetailsFragment, "TAG");
//        transaction.addToBackStack("TAG");
//        transaction.commit();
    }

    @Override
    public void onItemSelected(Entry req) {
        openEntryDetail(req);
    }

    @Override
    public void onEditClick(Entry req) {
//        EditEntryFragment editEntryFragment = new EditEntryFragment();
//        editEntryFragment.setEntry(req);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in,android.R.animator.fade_out);
//        transaction.add(R.id.entry_list_fragment_container, editEntryFragment, "TAG");
//        transaction.addToBackStack("TAG");
//        transaction.commit();
    }
}