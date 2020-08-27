package com.ranspektor.andproj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ranspektor.andproj.models.Entry;
import com.ranspektor.andproj.models.EntryModel;
import com.ranspektor.andproj.models.Listeners;
import com.ranspektor.andproj.models.UserModel;

public class EditEntryFragment extends Fragment {
    private Entry req;

    View view;

    ImageView image;
    EditText title;
    EditText content;
    ProgressBar progressBar;
    Button takePhotoBtn;
    Button sendBtn;
    Bitmap imageBitmap;


    public EditEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_entry, container, false);


        Log.d("TAG", "View view = inflater.inflate(R.layout.fragment_edit_entry, container,  ");
        req = EditEntryFragmentArgs.fromBundle(getArguments()).getReq();
        Log.d("TAG", "req = EditEntryFragmentArgs.fromBundle(getArguments()).getReq();");


        image = view.findViewById(R.id.edit_entry_image);
        title = view.findViewById(R.id.edit_entry_title);
        content = view.findViewById(R.id.edit_entry_content);
        progressBar = view.findViewById(R.id.edit_entry_progress);
        progressBar.setVisibility(View.INVISIBLE);
        takePhotoBtn = view.findViewById(R.id.edit_entry_take_photo_btn);
        sendBtn = view.findViewById(R.id.edit_entry_send_btn);


        if(req != null ){
            update_display();
        }

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                takePhotoBtn.setClickable(false);

                Entry entry = new Entry(title.getText().toString(), content.getText().toString());
                entry.id = req.id;
                entry.userId = UserModel.instance.getCurrentUserId();
                EntryModel.instance.updateEntry(entry, new Listeners.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        if(data){
                            EntryModel.instance.refreshEntryList(null);
                            Navigation.findNavController(v).navigateUp();
                            Navigation.findNavController(v).navigateUp();
                        }else{
                            //print error
                        }
                    }
                });
            }
        });

        this.view = view;
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @Nullable MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.entry_edit_menu,menu);
    }

    private void update_display(){
        Log.d("TAG", "update_display");

        title.setText(req.title);
        Log.d("TAG", title.getText().toString());
        content.setText(req.content);
        Log.d("TAG", content.getText().toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_delete_entry:{
                EntryModel.instance.deleteEntry(req, new Listeners.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        if(data){
                            EntryModel.instance.refreshEntryList(null);
                            Navigation.findNavController(view).navigateUp();
                            Navigation.findNavController(view).navigateUp();
                        }else{

                        }
                    }
                });
                return true;}
            default: return super.onOptionsItemSelected(item);
        }
    }


    private void takePhoto() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//        }
    }
}