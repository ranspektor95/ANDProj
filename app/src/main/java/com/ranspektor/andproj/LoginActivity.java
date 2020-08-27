package com.ranspektor.andproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ranspektor.andproj.models.UserModel;

public class LoginActivity extends AppCompatActivity implements LoginFragment.NavigateHomeDelegate {

    NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        navCtrl =  Navigation.findNavController(this,R.id.login_nav_host );
        NavigationUI.setupActionBarWithNavController(this, navCtrl);

        if (UserModel.instance.isLoggedIn()){
            this.navigateHome();
        }


    }


    @Override
    public void navigateHome() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }
}