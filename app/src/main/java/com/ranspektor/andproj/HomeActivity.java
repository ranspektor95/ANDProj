package com.ranspektor.andproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ranspektor.andproj.models.Request;

public class HomeActivity extends AppCompatActivity implements RequestListFragment.Delegate, RequestDetailsFragment.EditElementDelegate {

    NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navCtrl =  Navigation.findNavController(this,R.id.home_nav_host );
        NavigationUI.setupActionBarWithNavController(this, navCtrl);

    }

    @Override
    public void onItemSelected(Request req) {
       NavController navCtrl=  Navigation.findNavController(this,R.id.home_nav_host );
        RequestListFragmentDirections.ActionRequestListFragmentToRequestDetailsFragment directions = RequestListFragmentDirections.actionRequestListFragmentToRequestDetailsFragment(req);
        navCtrl.navigate(directions);
    }

    @Override
    public void onEditClick(Request req) {
        NavController navCtrl=  Navigation.findNavController(this,R.id.home_nav_host );
        RequestDetailsFragmentDirections.ActionRequestDetailsFragmentToEditRequestFragment directions = RequestDetailsFragmentDirections.actionRequestDetailsFragmentToEditRequestFragment(req);
        navCtrl.navigate(directions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:{
                navCtrl.navigateUp();
                return true;}
            case R.id.menu_add_request:{
                navCtrl.navigate(RequestListFragmentDirections.actionRequestListFragmentToCreateRequestFragment());
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.request_list_menu,menu);
        return true;
    }


}