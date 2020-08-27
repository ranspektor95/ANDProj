package com.ranspektor.andproj;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ranspektor.andproj.models.Entry;
import com.ranspektor.andproj.models.EntryModel;
import com.ranspektor.andproj.models.Listeners;

public class CreateEntryFragment extends Fragment {

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
        });
        return view;
    }


    private void takePhoto() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//        }
    }
}