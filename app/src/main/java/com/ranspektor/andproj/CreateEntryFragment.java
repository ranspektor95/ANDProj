package com.ranspektor.andproj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ranspektor.andproj.models.Entry;
import com.ranspektor.andproj.models.EntryModel;
import com.ranspektor.andproj.models.Listeners;
import com.ranspektor.andproj.models.StoreModel;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CreateEntryFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESAULT_SUCCESS = 0;

    ImageView image;
    EditText title;
    EditText content;
    ProgressBar progressBar;
    Button takePhotoBtn;
    Button sendBtn;
    Bitmap imageBitmap;

    public CreateEntryFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_entry, container, false);
        
        image = view.findViewById(R.id.add_entry_image);
        title = view.findViewById(R.id.add_entry_title);
        content = view.findViewById(R.id.add_entry_content);
        progressBar = view.findViewById(R.id.add_entry_progress);
        progressBar.setVisibility(View.INVISIBLE);
        takePhotoBtn = view.findViewById(R.id.add_entry_take_photo_btn);

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        sendBtn = view.findViewById(R.id.add_entry_send_btn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                takePhotoBtn.setClickable(false);

                Entry entry = new Entry(title.getText().toString(), content.getText().toString());
                entry.id = UUID.randomUUID().toString();

                if (imageBitmap != null) {
                    StoreModel.uploadEntryImage(imageBitmap, entry.id, new StoreModel.Listener() {
                        @Override
                        public void onSuccess(String url) {
                            Log.d("TAG", "GOOD PHOTO");
                            entry.imgUrl = url;

                            EntryModel.instance.addEntry(entry, new Listeners.Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {
                                    if(data){
                                        EntryModel.instance.refreshEntryList(null);
                                        Navigation.findNavController(v).navigateUp();
                                    }else{
                                        //print error
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFail() {
                            Log.d("TAG", "BAD PHOTO");
                            // print error
                        }
                    });
                } else {

                    EntryModel.instance.addEntry(entry, new Listeners.Listener<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            if (data) {
                                EntryModel.instance.refreshEntryList(null);
                                Navigation.findNavController(v).navigateUp();
                            } else {
                                //print error
                            }
                        }
                    });
                }
            }
        });
        return view;
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