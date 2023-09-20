package com.example.lostnexus;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostnexus.databinding.FragmentHomeScreenBinding;
import com.example.lostnexus.models.LostItem;
import com.example.lostnexus.viewmodels.LostItemViewModel;
import com.example.lostnexus.viewmodels.MainViewModel;

import java.util.List;


public class home_fragment extends Fragment {

FragmentHomeScreenBinding homeScreenBinding;
MainViewModel mainViewModel;
RecyclerView recyclerView1 , recyclerView2;
List<LostItem> lostItemList;
ItemListAdapter itemListAdapter;
LostItemViewModel lostItemViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeScreenBinding = DataBindingUtil.inflate(inflater , R.layout.lost_add_fragment, container , false);
      lostItemViewModel  = new ViewModelProvider(this).get(LostItemViewModel.class);
        System.out.println("inside the fragment cretead");

        return homeScreenBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lostItemViewModel.getAllItems().observe(this, new Observer<List<LostItem>>() {
            @Override
            public void onChanged(List<LostItem> lostItemList) {
           for(LostItem lostItem :lostItemList){
               System.out.println(lostItem.getDetail());
           }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView1=  homeScreenBinding.itemviewWeek;
        lostItemList = lostItemViewModel.getAllItems().getValue();

        itemListAdapter  = new ItemListAdapter(getContext()  ,lostItemList);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(itemListAdapter);
//        Re customAdapter = new CustomAdapter(getContext() ,movies );
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.setAdapter(customAdapter);

    }



}