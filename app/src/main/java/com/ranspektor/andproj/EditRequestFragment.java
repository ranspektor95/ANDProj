package com.ranspektor.andproj;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranspektor.andproj.models.Request;

public class EditRequestFragment extends Fragment {
    private Request req;


    public EditRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_request, container, false);

        req = EditRequestFragmentArgs.fromBundle(getArguments()).getReq();
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