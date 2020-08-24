package com.ranspektor.andproj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ranspektor.andproj.models.Entry;

import java.util.LinkedList;
import java.util.List;


public class EntryListFragment extends Fragment {

    Delegate parent;
    RecyclerView list;
    List<Entry> entriesData = new LinkedList<>();
    EntryListAdapter adapter;
    EntriesListViewModel entriesViewModel;
    UserEntriesListViewModel userEntriesViewModel;

    LiveData<List<Entry>> entriesLiveData;
    LiveData<List<Entry>> userEntriesLiveData;
//  UserModel.instance.addUser();
//  EntryModel.instance.addEntry();

    interface Delegate {
        void onItemSelected(Entry entry);
    }

    public EntryListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);

        if (context instanceof Delegate) {
            parent = (Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString());
        }
        entriesViewModel = new ViewModelProvider(this).get(EntriesListViewModel.class);
        userEntriesViewModel = new ViewModelProvider(this).get(UserEntriesListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list_view, container, false);

        list = view.findViewById(R.id.entry_list);
        list.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);

        adapter = new EntryListAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((position -> {
            Entry entry = entriesData.get(position);
            parent.onItemSelected(entry);
        }));

        entriesLiveData = entriesViewModel.getLiveData();
        entriesLiveData.observe(getViewLifecycleOwner(), entries -> {
            entriesData = entries;
            adapter.notifyDataSetChanged();
        });

        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.entry_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(() -> {
            entriesViewModel.refresh(() -> swipeRefresh.setRefreshing(false));
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView entryTitle;

        public EntryViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            entryTitle = itemView.findViewById(R.id.row_entry_title);
            entryTitle.setOnClickListener((v) -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onClick(position);
                    }
                }
            });
        }

        public void bind(Entry entry) {
            entryTitle.setText(entry.title);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, @Nullable MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.entry_list_menu, menu);
    }


    interface OnItemClickListener {
        void onClick(int position);
    }

    class EntryListAdapter extends RecyclerView.Adapter<EntryViewHolder> {
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_entry_list_row_item, parent, false);
            return new EntryViewHolder(v, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
            Entry entry = entriesData.get(position);
            holder.bind(entry);
//            holder.entryTitle.setText(entriesData.get(position).title);
        }

        @Override
        public int getItemCount() {
            return entriesData.size();
        }

    }


}