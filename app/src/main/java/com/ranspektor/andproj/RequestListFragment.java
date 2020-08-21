package com.ranspektor.andproj;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranspektor.andproj.models.Model;
import com.ranspektor.andproj.models.Request;

import java.util.List;


public class RequestListFragment extends Fragment {

    Delegate parent;
    RecyclerView list;
    List<Request> requestsData;

    public RequestListFragment() {
        requestsData = Model.instance.getAllRequests();
    }

    interface Delegate{
        void onItemSelected(Request req);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list_view, container, false);

        list = view.findViewById(R.id.request_list);
        list.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);

        RequestListAdapter adapter = new RequestListAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((position -> {
            Request req = requestsData.get(position);
            parent.onItemSelected(req);
        }));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @Nullable MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.request_list_menu,menu);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);

        if(context instanceof Delegate){
            parent = (Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent= null;
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView requestTitle;

        public RequestViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            requestTitle = itemView.findViewById(R.id.row_request_title);
            requestTitle.setOnClickListener((v)->{
                if (listener != null ){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onClick(position);
                    }
                }
            });
        }

        public void bind(Request req){
            requestTitle.setText(req.title);
        }
    }

    interface OnItemClickListener{
        void onClick(int position);
    }

    class RequestListAdapter extends RecyclerView.Adapter<RequestViewHolder> {
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener) {this.listener = listener;}

        @NonNull
        @Override
        public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_request_list_row_item, parent, false);
            RequestViewHolder vh = new RequestViewHolder(v,listener);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
            Request req = requestsData.get(position);
            holder.bind(req);
//            holder.requestTitle.setText(requestsData.get(position).title);
        }

        @Override
        public int getItemCount() {
            return requestsData.size();
        }

    }




}