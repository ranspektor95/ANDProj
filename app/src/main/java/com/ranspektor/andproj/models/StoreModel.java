package com.ranspektor.andproj.models;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class StoreModel {


    public interface Listener {
        void onSuccess(String url);

        void onFail();
    }

    public static void uploadEntryImage(Bitmap imageBmp, String entryId, final Listener listener) {
        uploadImage(imageBmp, "entry_" + entryId, listener, "images");
    }

    private static void uploadImage(Bitmap imageBmp, String name, final Listener listener, String folder) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference imageReference = firebaseStorage.getReference().child(folder).child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onSuccess(uri.toString());
                    }
                });
            }
        });
    }
}
