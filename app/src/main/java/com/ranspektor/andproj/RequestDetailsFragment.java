package com.ranspektor.andproj;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ranspektor.andproj.models.Request;

public class RequestDetailsFragment extends Fragment {
    private Request req;
    EditElementDelegate parent;
    TextView title;
    TextView content;

    public RequestDetailsFragment() { }

    interface EditElementDelegate {
        void onEditClick(Request req);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_request_details, container, false);
        // todo set images from req;
        title = view.findViewById(R.id.request_detail_title);
        content = view.findViewById(R.id.request_detail_content);

        req = RequestDetailsFragmentArgs.fromBundle(getArguments()).getReq();
        if(req != null ){
            update_display();
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @Nullable MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.request_details_menu,menu);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //TODO check if user can edit and then
        setHasOptionsMenu(true);

        if(context instanceof RequestDetailsFragment.EditElementDelegate){
            parent = (RequestDetailsFragment.EditElementDelegate) getActivity();
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent= null;
    }

    private void onEditClick() {
        parent.onEditClick(req);
    }


    private void update_display(){
        title.setText(req.title);
        content.setText(req.content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_btn: {
                this.onEditClick();
                return true;
            }
            default: return true;
        }
    }

}