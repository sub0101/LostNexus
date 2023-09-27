package com.example.lostnexus;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AllItemsFragment extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_items);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(null);

        RecyclerView recyclerView = findViewById(R.id.lostitemlsist);
        LostItemListAdapter listAdapter = new LostItemListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_all_items, container, false);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        ((AppCompatActivity)getActivity()).setActionBar(R.layout.base_toolbar);
//    }
}