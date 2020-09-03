package com.ranspektor.andproj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.ranspektor.andproj.models.StoreModel;
import com.ranspektor.andproj.models.UserModel;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class EditEntryFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESAULT_SUCCESS = 0;

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

        takePhotoBtn.setOnClickListener(v -> takePhoto());

        sendBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            takePhotoBtn.setClickable(false);

            Entry newEntry = new Entry(title.getText().toString(), content.getText().toString());
            newEntry.id = req.id;
            newEntry.userId = UserModel.instance.getCurrentUserId();

            if (imageBitmap != null) {
                StoreModel.uploadEntryImage(imageBitmap, newEntry.id, new StoreModel.Listener() {
                    @Override
                    public void onSuccess(String url) {
                        Log.d("TAG", "GOOD PHOTO");
                        newEntry.imgUrl = url;

                        EntryModel.instance.updateEntry(newEntry, data -> {
                            if(data){
                                EntryModel.instance.refreshEntryList(null);
                                Navigation.findNavController(v).navigateUp();
                                Navigation.findNavController(v).navigateUp();
                            }else{
                                //print error
                            }
                        });
                    }

                    @Override
                    public void onFail() {
                        Log.d("TAG", "BAD PHOTO");
                        // print error
                    }
                });
            }else{
                newEntry.imgUrl = req.imgUrl;

                EntryModel.instance.updateEntry(newEntry, data -> {
                    if(data){
                        EntryModel.instance.refreshEntryList(null);
                        Navigation.findNavController(v).navigateUp();
                        Navigation.findNavController(v).navigateUp();
                    }else{
                        //print error
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
        title.setText(req.title);
        content.setText(req.content);

        String imgUrl = req.imgUrl;
        if (imgUrl != null && !imgUrl.equals("")) {
            Picasso.get().load(imgUrl).into(image);
        }
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    imageBitmap = (Bitmap) extras.get("data");
                    image.setImageBitmap(imageBitmap);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}