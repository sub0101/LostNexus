package com.example.lostnexus;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostnexus.databinding.FragmentInitialItemBinding;
import com.example.lostnexus.models.LostItem;
import com.example.lostnexus.viewmodels.LostItemViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class InitialItemFragment extends Fragment {


    RecyclerView recyclerView1 , recyclerView2;
    List<LostItem> lostItemList;
    ItemListAdapter itemListAdapter;
    LostItemViewModel lostItemViewModel;
FragmentInitialItemBinding initialItemBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initialItemBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_initial_item, container,false);
       lostItemViewModel = new ViewModelProvider(this).get(LostItemViewModel.class);
        return initialItemBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_initial_item, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        lostItemViewModel.getAllItems().observe(this, new Observer<List<LostItem>>() {
            @Override
            public void onChanged(List<LostItem> lostItems) {
                for(LostItem lostItem :lostItems){
                    System.out.println(lostItem.getDetail());
                }
                showItems(lostItems);
            }
        });




    }



    void showItems(List<LostItem> items){
        recyclerView1=  initialItemBinding.itemviewWeek;
        itemListAdapter  = new ItemListAdapter(getContext() ,items);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setNestedScrollingEnabled(true);
        recyclerView1.setAdapter(itemListAdapter);
        recyclerView2 = initialItemBinding.recentLostview;
        itemListAdapter = new ItemListAdapter(getContext() , items);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext() , 2));
        recyclerView2.setNestedScrollingEnabled(true);
        recyclerView2.setAdapter( itemListAdapter);
    }
}