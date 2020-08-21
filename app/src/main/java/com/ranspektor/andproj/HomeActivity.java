package com.ranspektor.andproj;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ranspektor.andproj.models.Entry;

public class HomeActivity extends AppCompatActivity implements EntryListFragment.Delegate, EntryDetailsFragment.EditElementDelegate {

    NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navCtrl =  Navigation.findNavController(this,R.id.home_nav_host );
        NavigationUI.setupActionBarWithNavController(this, navCtrl);

    }

    @Override
    public void onItemSelected(Entry req) {
       NavController navCtrl=  Navigation.findNavController(this,R.id.home_nav_host );
        EntryListFragmentDirections.ActionEntryListFragmentToEntryDetailsFragment directions = EntryListFragmentDirections.actionEntryListFragmentToEntryDetailsFragment(req);
        navCtrl.navigate(directions);
    }

    @Override
    public void onEditClick(Entry req) {
        NavController navCtrl=  Navigation.findNavController(this,R.id.home_nav_host );
        EntryDetailsFragmentDirections.ActionEntryDetailsFragmentToEditEntryFragment directions = EntryDetailsFragmentDirections.actionEntryDetailsFragmentToEditEntryFragment(req);
        navCtrl.navigate(directions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:{
                navCtrl.navigateUp();
                return true;}
            case R.id.menu_add_entry:{
                navCtrl.navigate(EntryListFragmentDirections.actionEntryListFragmentToCreateEntryFragment());
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.entry_list_menu,menu);
        return true;
    }


}