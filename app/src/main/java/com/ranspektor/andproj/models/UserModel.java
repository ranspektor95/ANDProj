package com.ranspektor.andproj.models;

public class UserModel {
    public static final UserModel instance = new UserModel();

    public void addUser(User user, Listeners.Listener<Boolean> listener) {
        UserFirebase.addUser(user, listener);
    }

    public String getCurrentUserId() {
        return UserFirebase.getCurrentUserId();
    }

    public void signIn(String email, String password, Listeners.Listener<Boolean> listener) {
        UserFirebase.signIn(email, password, listener);
    }

    public void signUp(String email, String password, Listeners.Listener<String> listener) {
        UserFirebase.singUp(email, password, listener);
    }

    public void signOut() {
        UserFirebase.signOut();
    }

    public boolean isLoggedIn() {
        return UserFirebase.isSignedIn();
    }
}
