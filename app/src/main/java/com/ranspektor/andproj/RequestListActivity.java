package com.ranspektor.andproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranspektor.andproj.models.Model;
import com.ranspektor.andproj.models.Request;

import java.util.List;

public class RequestListActivity extends AppCompatActivity implements RequestListFragment.Delegate, RequestDetailsFragment.EditElementDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_list_activity_fragment);

//        RequestListFragment requestListFragment = new RequestListFragment();
//        RequestDetailsFragment requestDetailsFragment = new RequestDetailsFragment();
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.request_list_fragment_container, requestListFragment, "TAG");
//        transaction.commit();
    }


    void openRequestDetail(Request req){
//        RequestDetailsFragment requestDetailsFragment = new RequestDetailsFragment();
//        requestDetailsFragment.setRequest(req);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in,android.R.animator.fade_out);
//        transaction.add(R.id.request_list_fragment_container, requestDetailsFragment, "TAG");
//        transaction.addToBackStack("TAG");
//        transaction.commit();
    }

    @Override
    public void onItemSelected(Request req) {
        openRequestDetail(req);
    }

    @Override
    public void onEditClick(Request req) {
//        EditRequestFragment editRequestFragment = new EditRequestFragment();
//        editRequestFragment.setRequest(req);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in,android.R.animator.fade_out);
//        transaction.add(R.id.request_list_fragment_container, editRequestFragment, "TAG");
//        transaction.addToBackStack("TAG");
//        transaction.commit();
    }
}