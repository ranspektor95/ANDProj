package com.ranspektor.andproj;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranspektor.andproj.models.Entry;
import com.ranspektor.andproj.models.Listeners;
import com.ranspektor.andproj.models.UserModel;

public class LoginFragment extends Fragment {

    EditText username;
    EditText password;
    TextView loginErr;
    Button signInButton;
    Button signUpButton;
    ProgressBar progressBar;

    NavigateHomeDelegate parent;

    public LoginFragment() { }

    interface NavigateHomeDelegate {
        void navigateHome();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        progressBar = view.findViewById(R.id.login_progress);
        progressBar.setVisibility(View.INVISIBLE);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        loginErr = view.findViewById(R.id.login_error);
        signInButton = view.findViewById(R.id.login);
        signUpButton = view.findViewById(R.id.sign_up_btn);


        signInButton.setOnClickListener((v) -> {
            this.onSignInClick();
        });

        signUpButton.setOnClickListener((v) -> {
            this.onSignUpClick();
        });


        return view;
    }

    void onSignInClick(){
        progressBar.setVisibility(View.VISIBLE);
        UserModel.instance.signIn(username.getText().toString().trim(), password.getText().toString(), new Listeners.Listener<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("TAG", "done onComplete " + data);
                if(!data){
                    loginErr.setText("Bad Email or Password");
                }else{
                    parent.navigateHome();
                    loginErr.setText("");
                }
            }
        });
    }

    void onSignUpClick(){
        progressBar.setVisibility(View.VISIBLE);
        UserModel.instance.signUp(username.getText().toString().trim(), password.getText().toString(), new Listeners.Listener<String>() {
            @Override
            public void onComplete(String data) {
                progressBar.setVisibility(View.INVISIBLE);
                if(!data.isEmpty()){
                    loginErr.setText("");
                    parent.navigateHome();

                }else{
                    loginErr.setText("Bad Email or Password for Sign Up");
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //TODO check if user can edit and then
        setHasOptionsMenu(true);

        if(context instanceof LoginFragment.NavigateHomeDelegate){
            parent = (LoginFragment.NavigateHomeDelegate) getActivity();
        } else {
            throw new RuntimeException(context.toString());
        }
    }

}