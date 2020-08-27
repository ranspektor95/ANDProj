package com.ranspektor.andproj.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserFirebase {

    private final static String USER_COLLECTION = "users";

    public static void addUser(User user, final Listeners.Listener<Boolean> listener) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String id = firebaseUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(USER_COLLECTION)
                    .document(id)
                    .set(jsonFromUser(user))
                    .addOnCompleteListener(task -> {
                        if (listener != null) {
                            listener.onComplete(task.isSuccessful());
                        }
                    });
        }
    }

    public static boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static void signIn(String email, String password, final Listeners.Listener<Boolean> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TAG", "signInWithEmail:success");
                    } else {
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                    }
                    listener.onComplete(task.isSuccessful());
                });
    }

    public static void singUp(String email, String password, final Listeners.Listener<String> listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.w("TAG", "singUp", task.getException());
                    String id = getCurrentUserId();
                    listener.onComplete(id);
                });
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static String getCurrentUserId() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser != null ? firebaseUser.getUid() : null;
    }

    private static Map<String, Object> jsonFromUser(User user) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("email", user.getEmail());
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }

    private static User userFromJson(String id, Map<String, Object> json) {
        String email = (String) json.get("email");
        User user = new User(id, email);
        Timestamp timestamp = (Timestamp) json.get("lastUpdated");
        if (timestamp != null) user.setLastUpdated(timestamp.toDate().getTime());
        return user;
    }
}
