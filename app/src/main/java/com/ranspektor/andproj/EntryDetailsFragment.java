package com.ranspektor.andproj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ranspektor.andproj.models.Entry;

public class EntryDetailsFragment extends Fragment {
    private Entry req;
    EditElementDelegate parent;
    TextView title;
    TextView content;

    public EntryDetailsFragment() { }

    interface EditElementDelegate {
        void onEditClick(Entry req);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_entry_details, container, false);
        // todo set images from req;
        title = view.findViewById(R.id.entry_detail_title);
        content = view.findViewById(R.id.entry_detail_content);

        req = EntryDetailsFragmentArgs.fromBundle(getArguments()).getReq();
        if(req != null ){
            update_display();
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @Nullable MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.entry_details_menu,menu);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //TODO check if user can edit and then
        setHasOptionsMenu(true);

        if(context instanceof EntryDetailsFragment.EditElementDelegate){
            parent = (EntryDetailsFragment.EditElementDelegate) getActivity();
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