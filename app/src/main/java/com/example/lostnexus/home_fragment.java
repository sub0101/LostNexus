package com.example.lostnexus;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.example.lostnexus.databinding.FragmentHomeScreenBinding;
import com.example.lostnexus.models.LostItem;
import com.example.lostnexus.viewmodels.LostItemViewModel;
import com.example.lostnexus.viewmodels.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class home_fragment extends Fragment {

FragmentHomeScreenBinding homeScreenBinding;
RecyclerView recyclerView1 , recyclerView2;
List<LostItem> lostItemList;
ItemListAdapter itemListAdapter;
LostItemViewModel lostItemViewModel;
BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeScreenBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_home_screen, container , false);
      lostItemViewModel  = new ViewModelProvider(this).get(LostItemViewModel.class);
        System.out.println("inside the fragment cretead");

        return homeScreenBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_home_screen, container, false);
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
        recyclerView1=  homeScreenBinding.itemviewWeek;
        itemListAdapter  = new ItemListAdapter(getContext() ,items);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setNestedScrollingEnabled(true);
        recyclerView1.setAdapter(itemListAdapter);
        recyclerView2 = homeScreenBinding.recentLostview;
        itemListAdapter = new ItemListAdapter(getContext() , items);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext() , 2));
        recyclerView2.setNestedScrollingEnabled(true);

        recyclerView2.setAdapter( itemListAdapter);
    }


    public void setInitialView(MainActivity mainActivity)
    {

        bottomNavigationView = homeScreenBinding.bottomNavigationView;
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new home_fragment.BottomNavigationItemListner());
        bottomNavigationView.setSelectedItemId(R.id.home_bottomMenu);


    }
    private class BottomNavigationItemListner implements NavigationBarView.OnItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.home_bottomMenu) {

            }
            else if(id == R.id.search){

            }
            return true;
        }

    }

}