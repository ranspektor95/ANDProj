package com.ranspektor.andproj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ranspektor.andproj.models.Entry;

public class EditEntryFragment extends Fragment {
    private Entry req;


    public EditEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_entry, container, false);

        req = EditEntryFragmentArgs.fromBundle(getArguments()).getReq();
        if(req != null ){
            update_display();
        }

        return view;
    }

    private void update_display(){
//        title.setText(req.title);
//        content.setText(req.content);
    }
}