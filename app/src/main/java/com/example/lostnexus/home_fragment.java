package com.example.lostnexus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lostnexus.databinding.FragmentHomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class home_fragment extends Fragment {

FragmentHomeScreenBinding homeScreenBinding;
    BottomNavigationView bottomNavigationView;

InitialItemFragment initialItemFragment;
NotificationFragment notificationFragment;
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
        notificationFragment = new NotificationFragment();
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
                Intent intent = new Intent(getActivity().getApplication() , AllItemsFragment.class);
                startActivity(intent);
            }
            else if(id == R.id.notification){
                setCurrentFragment(notificationFragment);
            }
            return true;
        }

    }
    private void setCurrentFragment(Fragment fragment){

        getFragmentManager().beginTransaction().replace(homeScreenBinding.initialContainer.getId() , fragment).commit();
    }

}