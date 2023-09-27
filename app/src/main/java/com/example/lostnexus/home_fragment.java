package com.example.lostnexus;

import android.content.Intent;
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
    BottomNavigationView bottomNavigationView;

InitialItemFragment initialItemFragment;
AllItemsFragment allItemsFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeScreenBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_home_screen, container , false);
        return homeScreenBinding.getRoot();

    }


    @Override
    public void onStart() {
        super.onStart();

setInitialView();
    }




    public void setInitialView()
    {

        allItemsFragment = new AllItemsFragment();
        initialItemFragment  = new InitialItemFragment();
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
setCurrentFragment(initialItemFragment);
            }
            else if(id == R.id.search){
//setCurrentFragment(allItemsFragment);
                Intent intent = new Intent(getActivity().getApplication() , AllItemsFragment.class);
                startActivity(intent);
            }
            return true;
        }

    }
    private void setCurrentFragment(Fragment fragment){
//            .beginTransaction().apply {
//        replace(R.id.flFragment,fragment)
//        commit()
        getFragmentManager().beginTransaction().replace(homeScreenBinding.initialContainer.getId() , fragment).commit();
    }

}